package at.schulgong.util;

import at.schulgong.Hour;
import at.schulgong.Minute;
import at.schulgong.dto.RingTimeDTO;
import at.schulgong.dto.RingToneDTO;
import at.schulgong.RingTime;
import at.schulgong.RingTone;

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
      if (ringTime.getRingTone() != null) {
        RingToneDTO ringToneDTO = convertRingToneToDTO(ringTime.getRingTone());
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
      RingTone ringTone = convertDtoToRingTone(ringTimeDTO.getRingToneDTO());
      ringTime.setRingTone(ringTone);
    }
//    ringTime.setAddInfo(ringTimeDTO.getAddInfo());

    return ringTime;

  }

  public static RingToneDTO convertRingToneToDTO(RingTone ringTone) {
    RingToneDTO ringToneDTO = RingToneDTO.builder()
      .id(ringTone.getId())
      .name(ringTone.getName())
      .filename(ringTone.getFilename())
      .path(ringTone.getPath())
      .date(ringTone.getDate())
      .size(ringTone.getSize())
      .build();
    return ringToneDTO;
  }

  public static RingTone convertDtoToRingTone(RingToneDTO ringToneDTO){
    RingTone ringTone = new RingTone();

    ringTone.setId(ringToneDTO.getId());
    ringTone.setName(ringToneDTO.getName());
    ringTone.setFilename(ringToneDTO.getFilename());
    ringTone.setPath(ringToneDTO.getPath());
    ringTone.setDate(ringToneDTO.getDate());
    ringTone.setSize(ringToneDTO.getSize());
    return ringTone;
  }

}
