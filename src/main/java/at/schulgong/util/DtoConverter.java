package at.schulgong.util;

import at.schulgong.dto.*;
import at.schulgong.model.*;
import java.time.LocalTime;

/**
 * DtoConverter to convert ringtoneDTO into ringtone and reverse and ringtimeDTO into ringtime and
 * reverse.
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
public class DtoConverter {

    /**
     * Method to convert ringtime object into a ringtimeDTO object
     *
     * @param ringtime object of ringtone
     * @param inclusiveRingtone boolean
     * @return object of ringtoneDTO
     */
    public static RingtimeDTO convertRingtimeToDTO(Ringtime ringtime, boolean inclusiveRingtone) {
        RingtimeDTO ringTimeDTO =
                RingtimeDTO.builder()
                        .id(ringtime.getId())
                        .name(ringtime.getName())
                        .startDate(ringtime.getStartDate())
                        .endDate(ringtime.getEndDate())
                        .monday(ringtime.isMonday())
                        .tuesday(ringtime.isTuesday())
                        .wednesday(ringtime.isWednesday())
                        .thursday(ringtime.isThursday())
                        .friday(ringtime.isFriday())
                        .saturday(ringtime.isSaturday())
                        .sunday(ringtime.isSunday())
                        .playTime(
                                LocalTime.of(
                                        ringtime.getHour().getHour(),
                                        ringtime.getMinute().getMinute()))
                        .build();
        if (inclusiveRingtone && (ringtime.getRingtone() != null)) {
            RingtoneDTO ringToneDTO = convertRingtoneToDTO(ringtime.getRingtone());
            ringTimeDTO.setRingtoneDTO(ringToneDTO);
        }
        return ringTimeDTO;
    }

    /**
     * Method to convert ringtimeDTO object into a ringtime object
     *
     * @param ringtimeDTO object of ringtimeDTO
     * @param inclusiveRingtone boolean
     * @return object of ringtime
     */
    public static Ringtime convertDtoToRingtime(
            RingtimeDTO ringtimeDTO, boolean inclusiveRingtone) {
        Ringtime ringtime = new Ringtime();

        ringtime.setId(ringtimeDTO.getId());
        ringtime.setName(ringtimeDTO.getName());
        ringtime.setStartDate(ringtimeDTO.getStartDate());
        ringtime.setEndDate(ringtimeDTO.getEndDate());
        ringtime.setMonday(ringtimeDTO.isMonday());
        ringtime.setTuesday(ringtimeDTO.isTuesday());
        ringtime.setWednesday(ringtimeDTO.isWednesday());
        ringtime.setThursday(ringtimeDTO.isThursday());
        ringtime.setFriday(ringtimeDTO.isFriday());
        ringtime.setSaturday(ringtimeDTO.isSaturday());
        ringtime.setSunday(ringtimeDTO.isSunday());
        ringtime.setHour(new Hour(ringtimeDTO.getPlayTime().getHour()));
        ringtime.setMinute(new Minute(ringtimeDTO.getPlayTime().getMinute()));
        if (inclusiveRingtone) {
            Ringtone ringTone = convertDtoToRingtone(ringtimeDTO.getRingtoneDTO());
            ringtime.setRingtone(ringTone);
        }
        return ringtime;
    }

    /**
     * Method to convert ringtone object into a ringtoneDTO object
     *
     * @param ringtone object of ringtone
     * @return object of ringtoneDTO
     */
    public static RingtoneDTO convertRingtoneToDTO(Ringtone ringtone) {
        return RingtoneDTO.builder()
                .id(ringtone.getId())
                .name(ringtone.getName())
                .filename(ringtone.getFilename())
                .path(ringtone.getPath())
                .date(ringtone.getDate())
                .size(ringtone.getSize())
                .build();
    }

    /**
     * Method to convert ringtoneDTO object into a ringtone object
     *
     * @param ringtoneDTO object of ringtoneDTO
     * @return object of ringtone
     */
    public static Ringtone convertDtoToRingtone(RingtoneDTO ringtoneDTO) {
        Ringtone ringtone = new Ringtone();
        ringtone.setId(ringtoneDTO.getId());
        ringtone.setName(ringtoneDTO.getName());
        ringtone.setFilename(ringtoneDTO.getFilename());
        ringtone.setPath(ringtoneDTO.getPath());
        ringtone.setDate(ringtoneDTO.getDate());
        ringtone.setSize(ringtoneDTO.getSize());
        return ringtone;
    }

    /**
     * Method to convert holiday object into a holidayDTO object
     *
     * @param holiday object of holiday
     * @return object of holidayDTO
     */
    public static HolidayDTO convertHolidayToDTO(Holiday holiday) {
        return HolidayDTO.builder()
                .id(holiday.getId())
                .startDate(holiday.getStartDate())
                .endDate(holiday.getEndDate())
                .name(holiday.getName())
                .build();
    }

    /**
     * Method to convert holidayDTO object into a holiday object
     *
     * @param holidayDTO object of holiday
     * @return object of holiday
     */
    public static Holiday convertDtoToHoliday(HolidayDTO holidayDTO) {
        Holiday holiday = new Holiday();
        holiday.setId(holidayDTO.getId());
        holiday.setStartDate(holidayDTO.getStartDate());
        holiday.setEndDate(holidayDTO.getEndDate());
        holiday.setName(holidayDTO.getName());
        return holiday;
    }

    /**
     * Method to convert playlistSong object into a playlistSongDTO object
     *
     * @param playlistSong object of playlistSong
     * @return object of playlistSongDTO
     */
    public static PlaylistSongDTO convertPlaylistSongToDTO(PlaylistSong playlistSong) {
        PlaylistSongDTO playlistSongDTO = new PlaylistSongDTO();
        playlistSongDTO.setIndex(playlistSong.getId());
        playlistSongDTO.setId(playlistSong.getSong().getId());
        playlistSongDTO.setName(playlistSong.getSong().getName());
        playlistSongDTO.setFilePath(playlistSong.getSong().getFilePath());
        return playlistSongDTO;
    }

    /**
     * Method to convert playlistSongDTO object into a playlistSong object
     *
     * @param playlistSongDTO object of playlistSongDTO
     * @return object of playlistSong
     */
    public static PlaylistSong convertDtoToPlaylistSong(
            PlaylistSongDTO playlistSongDTO, Song song) {
        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setId(playlistSongDTO.getIndex());
        playlistSong.setSong(song);

        return playlistSong;
    }

    /**
     * Method to convert song object into a songDTO object
     *
     * @param song song object
     * @return songDTO object
     */
    public static SongDTO convertSongToDTO(Song song) {
        return SongDTO.builder()
                .id(song.getId())
                .name(song.getName())
                .filePath(song.getFilePath())
                .build();
    }

    /**
     * Method to convert songDTO object into a song object
     *
     * @param songDTO songDTO object
     * @return song object
     */
    public static Song convertSongDTOToSong(SongDTO songDTO) {
        Song song = new Song();
        song.setId(songDTO.getId());
        song.setName(songDTO.getName());
        song.setFilePath(songDTO.getFilePath());
        return song;
    }
}
