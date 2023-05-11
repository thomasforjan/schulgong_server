package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Model of hours
 * @since April 2023
 */
@Getter
@Setter
@Entity
@Table(name = "Hour")
public class Hour {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "hour")
  private int hour;

  public Hour() {
  }

  public Hour(int hour) {
    this.hour = hour;
  }
}
