package at.schulgong.dto;

import java.time.LocalDate;
import java.util.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO of holiday Object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HolidayDTO extends RepresentationModel<HolidayDTO> {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HolidayDTO that)) return false;
        if (!super.equals(o)) return false;
        return id == that.id
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, startDate, endDate, name);
    }
}
