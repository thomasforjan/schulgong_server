package at.schulgong.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import at.schulgong.assembler.SongModelAssembler;
import at.schulgong.dto.*;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.model.PlaylistSong;
import at.schulgong.model.Song;
import at.schulgong.repository.PlaylistSongRepository;
import at.schulgong.repository.SongRepository;
import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.speaker.util.SpeakerActionStatus;
import at.schulgong.speaker.util.SpeakerCommand;
import at.schulgong.util.AudioConverter;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import at.schulgong.util.ReadWriteConfigurationFile;
import jakarta.websocket.server.PathParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to provide CRUD-functionality
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@RestController
@RequestMapping("api/live")
@CrossOrigin
public class LiveController {
    private final PlayRingtones playRingtones;
    private final SongRepository songRepository;
    private final SongModelAssembler songModelAssembler;
    private final PlaylistSongRepository playlistRepository;

    /**
     * Controller of Live
     *
     * @param playRingtones Dto of playRingtones
     * @param songRepository Repo of songs
     * @param songModelAssembler Assembler of songs
     * @param playlistRepository Repo of playlist
     */
    public LiveController(
            PlayRingtones playRingtones,
            SongRepository songRepository,
            SongModelAssembler songModelAssembler,
            PlaylistSongRepository playlistRepository) {
        this.playRingtones = playRingtones;
        this.songRepository = songRepository;
        this.songModelAssembler = songModelAssembler;
        this.playlistRepository = playlistRepository;
    }

    /**
     * Get isPlayingAlarm flag.
     *
     * @return isPlayingAlarm flag
     */
    @GetMapping("alarm/isplaying")
    public ResponseEntity<Boolean> alarmIsPlaying() {
        return ResponseEntity.ok(playRingtones.isPlayingAlarm());
    }

    /**
     * Start or stop playing the alarm.
     *
     * @param isPlayingAlarm flag if alarm has to start or stop
     * @return no Content
     */
    @PostMapping("alarm")
    ResponseEntity<?> alarmStartStopPlaying(@RequestBody boolean isPlayingAlarm) {
        if (isPlayingAlarm) {
            playRingtones.playAlarm();
        } else {
            playRingtones.stopAlarm();
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Get isPlayingPlaylist flag.
     *
     * @return isPlayingPlaylist flag
     */
    @GetMapping("playlist/isplaying")
    public ResponseEntity<Boolean> playlistIsPlaying() {
        return ResponseEntity.ok(playRingtones.isPlayingPlaylist());
    }

    /**
     * Play announcement.
     *
     * @param announcement takes announcement as byte array
     * @return no Content
     */
    @PostMapping(consumes = {"audio/webm", "audio/ogg", "audio/wav"})
    ResponseEntity<?> liveAnnouncement(@RequestBody byte[] announcement) {
        try {
            Path filePath = Paths.get(Config.ANNOUNCEMENT_PATH.getPath());
            File outputFileDirectory = new File(filePath.toString());
            if (!outputFileDirectory.exists()) {
                outputFileDirectory.mkdirs();
            }
            File outputFile = new File(filePath.toString() + "/Durchsage.weba");
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(announcement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AudioConverter.convertWebAToMP3(
                Config.ANNOUNCEMENT_PATH.getPath() + "/Durchsage.weba",
                Config.ANNOUNCEMENT_PATH.getPath() + "/Durchsage.mp3");
        playRingtones.playAnnouncement();
        return ResponseEntity.noContent().build();
    }

    /**
     * Get particular song by specific id.
     *
     * @param id takes song id
     * @return specific song based on its id
     */
    @GetMapping("music/songs/{id}")
    public SongDTO oneSong(@PathVariable long id) {
        Song song =
                songRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(id, Config.SONG.getException()));
        return songModelAssembler.toModel(song);
    }

    /**
     * Get all songs.
     *
     * @return all songs
     */
    @GetMapping("music/songs")
    public CollectionModel<SongDTO> allSongs() {
        List<Song> songs = songRepository.findAll();
        return songModelAssembler
                .toCollectionModel(songs)
                .add(
                        linkTo(methodOn(LiveController.class).allSongs())
                                .withRel(Config.SONG.getUrl()));
    }

    /**
     * Get all available songs.
     *
     * @return all songs
     */
    @GetMapping("music/songs/available")
    public CollectionModel<SongDTO> allSongsWithoutPlaylist() {
        List<Song> songs = songRepository.findAvailableSongs();
        return songModelAssembler
                .toCollectionModel(songs)
                .add(
                        linkTo(methodOn(LiveController.class).allSongs())
                                .withRel(Config.SONG.getUrl()));
    }

    /**
     * Get state if songs are playing
     *
     * @return playlistDTO
     */
    @GetMapping("music/state")
    public ResponseEntity<PlaylistDTO> getPlaylistState() {
        PlaylistDTO playlistDTO = null;
        if (!playRingtones.isPlayingAlarm()) {
            playlistDTO = playRingtones.getPlaylistDTO();
        }
        return ResponseEntity.ok(playlistDTO);
    }

    /**
     * Update songs and playlist.
     *
     * @param savePlaylistDTO list of songs to remove
     * @return response status
     */
    @PostMapping("music/songs/save")
    ResponseEntity<?> savePlaylist(@RequestBody SavePlaylistDTO savePlaylistDTO) {
        try {
            playlistRepository.deleteAll();
            if (savePlaylistDTO.isSongListChanged()) {
                List<Song> songList = songRepository.findAll();
                if (!songList.isEmpty()) {
                    deleteElementsFromSongEntityAndFileSystem(songList, savePlaylistDTO);
                }
                saveSongIntoSongEntityAndFileSystem(savePlaylistDTO);
            }
            setPlaylist(savePlaylistDTO);
            playRingtones.setPlaylist(savePlaylistDTO.getPlaylistSongDTOList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Control playlist.
     *
     * @param speakerCommandDTO takes SpeakerCommandDTO
     * @return status ok or badRequest
     */
    @PostMapping("music/control")
    ResponseEntity controlPlaylist(@RequestBody SpeakerCommandDTO speakerCommandDTO) {
        SpeakerActionStatus speakerActionStatus =
                playRingtones.controlPlaylist(
                        speakerCommandDTO,
                        playRingtones.isPlayingFromQueue() ? null : playlistRepository.findAll());
        if (speakerActionStatus.getExitCode() == 0
                && (speakerActionStatus.getException() == null
                        || speakerActionStatus.getException().isEmpty())) {
            if (speakerActionStatus.getSpeakerCommand() != SpeakerCommand.PLAY) {
                playRingtones.getPlaylistInfo();
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Repeat playlist.
     *
     * @param repeat takes SpeakerCommandDTO
     * @return status ok
     */
    @PostMapping("music/repeat/{repeat}")
    ResponseEntity setRepeatFunction(@PathVariable("repeat") boolean repeat) {
        playRingtones.setRepeatPlaylist(repeat);
        playRingtones.getPlaylistInfo();
        return ResponseEntity.ok().build();
    }

    /**
     * Set playlist on network speaker
     *
     * @param force Flag if set playlist is running
     * @return response status
     */
    @PostMapping("music/songs/set/playlist/{force}")
    ResponseEntity<?> setPlaylistOnNetworkSpeaker(@PathParam("force") boolean force) {
        try {
            if (force || !playRingtones.isPlayingFromQueue()) {
                List<PlaylistSong> playlistSongList = playlistRepository.findAll();
                if (playlistSongList != null) {
                    List<PlaylistSongDTO> playlistSongDTOList = new ArrayList<>();
                    for (PlaylistSong playlistSong : playlistSongList) {
                        playlistSongDTOList.add(
                                DtoConverter.convertPlaylistSongToDTO(playlistSong));
                    }
                    playRingtones.setPlaylist(playlistSongDTOList);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Save audiofile from the anouncement and encode it
     *
     * @param base64Audio String of audioname
     * @param filePath filepath
     * @return boolean
     */
    private boolean saveAudioFile(String base64Audio, String filePath, String fileName) {
        ;
        boolean isFileGenerated = false;
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName)) {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedByte = decoder.decode(base64Audio.split(",")[1]);
            fos.write(decodedByte);
            isFileGenerated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isFileGenerated;
    }

    /**
     * Delete the audiofile in the directory
     *
     * @param path path of audio file
     * @return flag if file is deleted
     */
    private boolean deleteAudioFile(String path) {
        boolean isFileDeleted = false;
        File file = new File(path);
        try {
            if (file.exists() && file.canWrite()) {
                Files.delete(Paths.get(path));
                isFileDeleted = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isFileDeleted;
    }

    /**
     * Method to delete songs from song entity and from the fileSystem
     *
     * @param songList List of songs
     * @param savePlaylistDTO List of saved playlist
     */
    private void deleteElementsFromSongEntityAndFileSystem(
            List<Song> songList, SavePlaylistDTO savePlaylistDTO) {
        for (Song song : songList) {
            boolean songExist = false;
            for (SongDTO songDTO : savePlaylistDTO.getActualSongList()) {
                if (song.getId() == songDTO.getId()) {
                    songExist = true;
                    break;
                }
            }
            if (!songExist && (Files.notExists(Paths.get(song.getFilePath())) || deleteAudioFile(song.getFilePath()))) {
                songRepository.delete(song);
            }
        }
    }

    /**
     * Save songs into song entity and into the file system
     *
     * @param savePlaylistDTO get saved playlist DTO
     */
    private void saveSongIntoSongEntityAndFileSystem(SavePlaylistDTO savePlaylistDTO) {
        for (SongDTO songDTO : savePlaylistDTO.getActualSongList()) {
            if (songDTO.getId() < 0) {
                ConfigurationDTO configurationDTO =
                        ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(
                                Config.CONFIGURATION_PATH.getPath());
                if (configurationDTO != null) {
                    String filePath = configurationDTO.getPlaylistDirectory();
                    if (saveAudioFile(songDTO.getSong(), filePath, songDTO.getName())) {

                        songDTO.setFilePath(filePath + File.separator + songDTO.getName());

                        Song song = songRepository.save(DtoConverter.convertSongDTOToSong(songDTO));
                        for (PlaylistSongDTO playlistSongDTO :
                                savePlaylistDTO.getPlaylistSongDTOList()) {
                            if (songDTO.getId() == playlistSongDTO.getId()) {
                                playlistSongDTO.setId(song.getId());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to set playlist
     *
     * @param savePlaylistDTO saved playlist
     */
    private void setPlaylist(SavePlaylistDTO savePlaylistDTO) {
        for (PlaylistSongDTO playlistSongDTO : savePlaylistDTO.getPlaylistSongDTOList()) {
            Optional<Song> song = songRepository.findById(playlistSongDTO.getId());
            playlistRepository.save(
                    DtoConverter.convertDtoToPlaylistSong(playlistSongDTO, song.get()));
        }
    }
}
