package at.schulgong.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalTime;
import java.util.Date;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote DTO of Ringtime Object
 * @since April 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RingtimeDTO extends RepresentationModel<RingtimeDTO> {

  private long id;
  private String name;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private RingtoneDTO ringtoneDTO;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime playTime;
  private Date startDate;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Date endDate;
  private boolean monday;
  private boolean tuesday;
  private boolean wednesday;
  private boolean thursday;
  private boolean friday;
  private boolean saturday;
  private boolean sunday;
}