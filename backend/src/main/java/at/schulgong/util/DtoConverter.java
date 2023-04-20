package at.schulgong.util;

import at.schulgong.Ringtone;
import at.schulgong.dto.RingtoneDTO;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote DtoConverter to convert ringtoneDTO into ringtone and reverse.
 * @since April 2023
 */
public class DtoConverter {

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

    ringtone.setName(ringtoneDTO.getName());
    ringtone.setFilename(ringtoneDTO.getFilename());
    ringtone.setPath(ringtoneDTO.getPath());
    ringtone.setDate(ringtoneDTO.getDate());
    ringtone.setSize(ringtoneDTO.getSize());
    return ringtone;
  }
}
