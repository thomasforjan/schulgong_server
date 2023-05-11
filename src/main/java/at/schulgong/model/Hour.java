package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Hour")
@NamedQuery(name = "Hour.findByHour", query = "SELECT h FROM Hour h WHERE h.hour=:hour")
public class Hour {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "hour")
  private int hour;

  public Hour() {}

  public Hour(int hour) {
    this.hour = hour;
  }
}
