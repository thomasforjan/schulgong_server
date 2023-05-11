package at.schulgong.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote DTO of holiday Object
 * @since April 2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HolidayDTO extends RepresentationModel<HolidayDTO> {
  private long id;
  private LocalDate startDate, endDate;
  private String name;
}
