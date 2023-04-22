package at.schulgong.util;

import at.schulgong.Hour;
import at.schulgong.Minute;
import at.schulgong.dto.RingTimeDTO;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.RingTime;
import at.schulgong.Ringtone;

import java.time.LocalTime;

public class DtoConverter {

  public static RingTimeDTO convertRingTimeToDTO(RingTime ringTime, boolean inclusiveRingTone) {
    RingTimeDTO ringTimeDTO = RingTimeDTO.builder()
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
    if (inclusiveRingTone) {
      if (ringTime.getRingtone() != null) {
        RingtoneDTO ringToneDTO = convertRingtoneToDTO(ringTime.getRingtone());
        ringTimeDTO.setRingToneDTO(ringToneDTO);
      }
    }
    return ringTimeDTO;
  }

  public static RingTime convertDtoToRingTime(RingTimeDTO ringTimeDTO, boolean inclusiveRingTone){
    RingTime ringTime = new RingTime();

    ringTime.setId(ringTimeDTO.getId());
    ringTime.setName(ringTimeDTO.getName());
    ringTime.setStartDate(ringTimeDTO.getStartDate());
    ringTime.setEndDate(ringTimeDTO.getEndDate());
    ringTime.setMonday(ringTimeDTO.isMonday());
    ringTime.setTuesday(ringTimeDTO.isTuesday());
    ringTime.setWednesday(ringTimeDTO.isWednesday());
    ringTime.setThursday(ringTimeDTO.isThursday());
    ringTime.setFriday(ringTimeDTO.isFriday());
    ringTime.setSaturday(ringTimeDTO.isSaturday());
    ringTime.setSunday(ringTimeDTO.isSunday());
    ringTime.setHour(new Hour(ringTimeDTO.getPlayTime().getHour()));
    ringTime.setMinute(new Minute(ringTimeDTO.getPlayTime().getMinute()));
    if (inclusiveRingTone) {
      Ringtone ringTone = convertDtoToRingtone(ringTimeDTO.getRingToneDTO());
      ringTime.setRingtone(ringTone);
    }
//    ringTime.setAddInfo(ringTimeDTO.getAddInfo());

    return ringTime;

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
