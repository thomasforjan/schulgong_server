package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Model of ringtone
 * @since April 2023
 */
@Getter
@Setter
@Table(name = "Ringtone")
@NoArgsConstructor
@Entity
public class Ringtone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;
  @Column(name = "filename")
  private String filename;
  @Column(name = "path")
  private String path;
  @Column(name = "date")
  private LocalDate date;
  @Column(name = "size")
  private double size;
}
