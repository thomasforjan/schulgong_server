package at.schulgong.controller;

import at.schulgong.assembler.SongModelAssembler;
import at.schulgong.dto.PlaylistDTO;
import at.schulgong.dto.PlaylistSongDTO;
import at.schulgong.dto.SavePlaylistDTO;
import at.schulgong.dto.SongDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.model.PlaylistSong;
import at.schulgong.model.Song;
import at.schulgong.repository.PlaylistSongRepository;
import at.schulgong.repository.SongRepository;
import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.speaker.util.PlaylistInfo;
import at.schulgong.speaker.util.SpeakerState;
import at.schulgong.util.AudioConverter;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Controller to provide CRUD-functionality
 * @since May 2023
 */
@RestController
@RequestMapping("live")
@CrossOrigin
public class LiveController {
  private final PlayRingtones playRingtones;
  private final SongRepository songRepository;
  private final SongModelAssembler songModelAssembler;
  private final PlaylistSongRepository playlistRepository;


  /**
   * Controller of Live
   *
   * @param playRingtones      Dto of playRingtones
   * @param songRepository     Repo of songs
   * @param songModelAssembler Assembler of songs
   * @param playlistRepository Repo of playlist
   */
  public LiveController(PlayRingtones playRingtones, SongRepository songRepository, SongModelAssembler songModelAssembler, PlaylistSongRepository playlistRepository) {
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
   * Play announcement.
   *
   * @param announcement takes announcement as byte array
   * @return no Content
   */
  @PostMapping(consumes = "audio/webm")
  ResponseEntity<?> liveAnnouncement(@RequestBody byte[] announcement) {
    try {
      Path filePath = Paths.get(Config.ANNOUNCEMENT_PATH_WEBA_FORMAT.getPath());
      File outputFile = new File(filePath.toString());
      try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
        outputStream.write(announcement);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    AudioConverter.convertWebAToMP3(Config.ANNOUNCEMENT_PATH_WEBA_FORMAT.getPath(), Config.ANNOUNCEMENT_PATH.getPath());
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
    Song song = songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Config.SONG.getException()));
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
    return songModelAssembler.toCollectionModel(songs).add(linkTo(methodOn(LiveController.class).allSongs()).withRel(Config.SONG.getUrl()));
  }

  /**
   * Get all available songs.
   *
   * @return all songs
   */
  @GetMapping("music/songs/available")
  public CollectionModel<SongDTO> allSongsWithoutPlaylist() {
    List<Song> songs = songRepository.findAvailableSongs();
    return songModelAssembler.toCollectionModel(songs).add(linkTo(methodOn(LiveController.class).allSongs()).withRel(Config.SONG.getUrl()));
  }

  /**
   * Get state if songs are playing
   *
   * @return playlistDTO
   */
  @GetMapping("music/state")
  public ResponseEntity<PlaylistDTO> getPlaylistState() {
    PlaylistDTO playlistDTO = null;
    SpeakerState speakerState = null;
    PlaylistSongDTO actualPlaylistSongDTO = null;
    List<PlaylistSongDTO> playlistSongDTOList = new ArrayList<>();
    List<PlaylistSong> playlistList = playlistRepository.findAll();
    if (!playRingtones.isPlayingAlarm() && !playlistList.isEmpty()) {
      for (PlaylistSong p : playlistList) {
        playlistSongDTOList.add(DtoConverter.convertPlaylistSongToDTO(p));
      }
      playlistSongDTOList.sort(Comparator.comparingLong(PlaylistSongDTO::getIndex));
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        PlaylistInfo playlistInfo = new PlaylistInfo(60, true, true, 1, SpeakerState.PLAYING.getState());
        actualPlaylistSongDTO = playlistSongDTOList.get(playlistInfo.getPosition() == 0 ? 0 : playlistInfo.getPosition() - 1);
        if (!playlistInfo.isPlayingFromQueue()) {
          speakerState = SpeakerState.STOPPED;
        } else {
          speakerState = SpeakerState.fromState(playlistInfo.getSpeakerState());
        }
        playlistDTO = PlaylistDTO.builder()
          .speakerState(speakerState)
          .volume(playlistInfo.getVolume())
          .mute(playlistInfo.isMute())
          .actualSong(actualPlaylistSongDTO)
          .songDTOList(playlistSongDTOList).build();

      } catch (Exception e) {
        e.printStackTrace();
      }
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
  ResponseEntity<?> savePlaylist(
    @RequestBody SavePlaylistDTO savePlaylistDTO) {
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
    } catch (
      Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  /**
   * Save audiofile from the anouncement and encode it
   *
   * @param base64Audio String of audioname
   * @param filePath    filepath
   * @return boolean
   */
  private boolean saveAudioFile(String base64Audio, String filePath) {
    boolean isFileGenerated = false;
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
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
   * @param songList        List of songs
   * @param savePlaylistDTO List of saved playlist
   */
  private void deleteElementsFromSongEntityAndFileSystem(List<Song> songList, SavePlaylistDTO savePlaylistDTO) {
    for (Song song : songList) {
      boolean songExist = false;
      for (SongDTO songDTO : savePlaylistDTO.getActualSongList()) {
        if (song.getId() == songDTO.getId()) {
          songExist = true;
          break;
        }
      }
      if (!songExist && (deleteAudioFile(song.getFilePath()))) {
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
        String filePath = Config.PLAYLIST_PATH.getPath() + songDTO.getName();
        if (saveAudioFile(songDTO.getSong(), filePath)) {
          songDTO.setFilePath(filePath);
          Song song = songRepository.save(DtoConverter.convertSongDTOToSong(songDTO));
          for (PlaylistSongDTO playlistSongDTO : savePlaylistDTO.getPlaylistSongDTOList()) {
            if (songDTO.getId() == playlistSongDTO.getId()) {
              playlistSongDTO.setId(song.getId());
              break;
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
      playlistRepository.save(DtoConverter.convertDtoToPlaylistSong(playlistSongDTO, song.get()));
    }
  }

}
