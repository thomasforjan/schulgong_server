package at.schulgong;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name ="RingTone")
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

  public Ringtone(String name, String filename, String path, LocalDate date, int size) {
    this.name = name;
    this.filename = filename;
    this.path = path;
    this.date = date;
    this.size = size;
  }
}
