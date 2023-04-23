package at.schulgong.util;

import at.schulgong.Hour;
import at.schulgong.Minute;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.Ringtime;
import at.schulgong.Ringtone;
import java.time.LocalTime;


/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote DtoConverter to convert ringtoneDTO into ringtone and reverse and ringtimeDTO into ringtime and reverse.
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
    RingtimeDTO ringTimeDTO = RingtimeDTO.builder()
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
      .playTime(LocalTime.of(ringtime.getHour().getHour(), ringtime.getMinute().getMinute())).build();
//      .addInfo(ringTime.getAddInfo()).build();
    if (inclusiveRingtone) {
      if (ringtime.getRingtone() != null) {
        RingtoneDTO ringToneDTO = convertRingtoneToDTO(ringtime.getRingtone());
        ringTimeDTO.setRingtoneDTO(ringToneDTO);
      }
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
  public static Ringtime convertDtoToRingtime(RingtimeDTO ringtimeDTO, boolean inclusiveRingtone){
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
//    ringTime.setAddInfo(ringTimeDTO.getAddInfo());

    return ringtime;

  }

  /**
   * Method to convert ringtone object into a ringtoneDTO object
   *
   * @param ringtone object of ringtone
   * @return object of ringtoneDTO
   */
  public static RingtoneDTO convertRingtoneToDTO(Ringtone ringtone) {
    RingtoneDTO ringtoneDTO = RingtoneDTO.builder().id(ringtone.getId()).name(ringtone.getName()).filename(ringtone.getFilename()).path(ringtone.getPath()).date(ringtone.getDate()).size(ringtone.getSize()).build();
    return ringtoneDTO;
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
}
