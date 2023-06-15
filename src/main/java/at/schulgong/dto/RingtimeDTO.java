package at.schulgong.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO of Ringtime Object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RingtimeDTO that)) return false;
        if (!super.equals(o)) return false;
        return id == that.id
                && monday == that.monday
                && tuesday == that.tuesday
                && wednesday == that.wednesday
                && thursday == that.thursday
                && friday == that.friday
                && saturday == that.saturday
                && sunday == that.sunday
                && Objects.equals(name, that.name)
                && Objects.equals(ringtoneDTO, that.ringtoneDTO)
                && Objects.equals(playTime, that.playTime)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                id,
                name,
                ringtoneDTO,
                playTime,
                startDate,
                endDate,
                monday,
                tuesday,
                wednesday,
                thursday,
                friday,
                saturday,
                sunday);
    }
}
