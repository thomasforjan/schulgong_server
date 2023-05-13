package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Model of minutes
 * @since April 2023
 */
@Getter
@Setter
@Entity
@Table(name = "Minute")
public class Minute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "minute")
  private int minute;

  public Minute() {
  }

  public Minute(int minute) {
    this.minute = minute;
  }
}
