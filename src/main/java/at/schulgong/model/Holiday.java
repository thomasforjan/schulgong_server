package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Model of holiday
 * @since April 2023
 */
@Getter
@Setter
@Table(name = "Holiday")
@NoArgsConstructor
@Entity
public class Holiday {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "startdate")
  private LocalDate startDate;

  @Column(name = "enddate")
  private LocalDate endDate;

  @Column(name = "name")
  private String name;
}
