package at.schulgong;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Minute")
@NamedQuery(name = "Minute.findByMinute", query = "SELECT m FROM Minute m WHERE m.minute=:minute")
public class Minute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "minute")
  private int minute;

  public Minute() {}

  public Minute(int minute) {
    this.minute = minute;
  }
}
