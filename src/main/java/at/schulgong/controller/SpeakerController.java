package at.schulgong.controller;

import at.schulgong.model.Ringtone;
import at.schulgong.model.Song;
import at.schulgong.repository.RingtoneRepository;
import at.schulgong.repository.SongRepository;
import at.schulgong.util.AudioFormat;
import at.schulgong.util.Config;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Controller to provide CRUD-functionality
 * @since April 2023
 */
@RestController
@RequestMapping("api/speaker/play")
@CrossOrigin
public class SpeakerController {
    private final RingtoneRepository ringtoneRepository;
    private final SongRepository songRepository;

    /**
     * Constructor for holidays
     *
     * @param ringtoneRepository Repository of ringtones
     * @param songRepository Repository of songs
     * @param ringtoneRepository
     * @param songRepository
     */
    public SpeakerController(RingtoneRepository ringtoneRepository, SongRepository songRepository) {

        this.ringtoneRepository = ringtoneRepository;
        this.songRepository = songRepository;
    }

    /**
     * Get ringtone as byte array.
     *
     * @return ringtone
     */
    @GetMapping("ringtone/{name}")
    public ResponseEntity<byte[]> getRingtone(@PathVariable String name) {
        Ringtone ringtone = ringtoneRepository.findRingtoneByFilename(name);
        byte[] audioBytes = null;
        if (ringtone != null) {
            File audioFile = new File(ringtone.getPath());
            try {
                audioBytes = Files.readAllBytes(audioFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(getMimeType(name)));

        if (audioBytes != null) {
            headers.setContentLength(audioBytes.length);
        }

        headers.setContentDispositionFormData(
                "attachment", ringtone != null ? ringtone.getFilename() : "");
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }

    /**
     * Get playlist song as byte array.
     *
     * @return playlist song
     */
    @GetMapping("playlist/{name}")
    public ResponseEntity<byte[]> getPlaylistSong(@PathVariable String name) {
        Song song = songRepository.findSongByName(name);
        byte[] audioBytes = null;
        if (song != null) {
            Path filePath = Paths.get(song.getFilePath());
            File audioFile = new File(filePath.toString());
            try {
                audioBytes = Files.readAllBytes(audioFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(getMimeType(name)));

        if (audioBytes != null) {
            headers.setContentLength(audioBytes.length);
        }

        headers.setContentDispositionFormData("attachment", song != null ? song.getName() : "");
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }

    /**
     * Get announcement as byte array.
     *
     * @return announcement
     */
    @GetMapping("announcement/Durchsage.mp3")
    public ResponseEntity<byte[]> getAnnouncement() {
        byte[] audioBytes = null;
        File audioFile = new File(Config.ANNOUNCEMENT_PATH.getPath() + "/Durchsage.mp3");
        try {
            audioBytes = Files.readAllBytes(audioFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/mpeg"));

        if (audioBytes != null) {
            headers.setContentLength(audioBytes.length);
        }

        headers.setContentDispositionFormData("attachment", "Durchsage.mp3");
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }

    private String getMimeType(String fileName) {
        String mimeType = AudioFormat.MP3.getType();
        String[] type = fileName.split("\\.");
        if (type.length > 1) {
            switch (type[1]) {
                case "m4a":
                    mimeType = AudioFormat.M4A.getType();
                    break;
                case "ogg":
                    mimeType = AudioFormat.OGG.getType();
                    break;
                case "wma":
                    mimeType = AudioFormat.WMA.getType();
                    break;
                case "flac":
                    mimeType = AudioFormat.FLAC.getType();
                    break;
                default:
                    mimeType = AudioFormat.MP3.getType();
                    break;
            }
        }
        return mimeType;
    }
}
