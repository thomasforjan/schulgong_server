package at.schulgong.util;

import at.schulgong.Hour;
import at.schulgong.Minute;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.Ringtime;
import at.schulgong.Ringtone;

import java.time.LocalTime;

public class DtoConverter {

  public static RingtimeDTO convertRingtimeToDTO(Ringtime ringTime, boolean inclusiveRingtone) {
    RingtimeDTO ringTimeDTO = RingtimeDTO.builder()
      .id(ringTime.getId())
      .name(ringTime.getName())
      .startDate(ringTime.getStartDate())
      .endDate(ringTime.getEndDate())
      .monday(ringTime.isMonday())
      .tuesday(ringTime.isTuesday())
      .wednesday(ringTime.isWednesday())
      .thursday(ringTime.isThursday())
      .friday(ringTime.isFriday())
      .saturday(ringTime.isSaturday())
      .sunday(ringTime.isSunday())
      .playTime(LocalTime.of(ringTime.getHour().getHour(), ringTime.getMinute().getMinute())).build();
//      .addInfo(ringTime.getAddInfo()).build();
    if (inclusiveRingtone) {
      if (ringTime.getRingtone() != null) {
        RingtoneDTO ringToneDTO = convertRingtoneToDTO(ringTime.getRingtone());
        ringTimeDTO.setRingtoneDTO(ringToneDTO);
      }
    }
    return ringTimeDTO;
  }

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
