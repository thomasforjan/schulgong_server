package at.schulgong;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name ="RingTone")
@NoArgsConstructor
@Entity
public class RingTone {


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
  private String date;
  @Column(name = "size")
  private int size;

  public RingTone(String name, String filename, String path, String date, int size) {
    this.name = name;
    this.filename = filename;
    this.path = path;
    this.date = date;
    this.size = size;
  }
}
