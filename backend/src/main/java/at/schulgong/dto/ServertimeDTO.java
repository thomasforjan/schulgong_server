package at.schulgong.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote DTO of server time Object
 * @since April 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServertimeDTO extends RepresentationModel<ServertimeDTO> {
    private String time;
}
