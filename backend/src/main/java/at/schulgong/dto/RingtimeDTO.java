package at.schulgong.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalTime;
import java.util.Date;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
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
    //  private String addInfo;

}
