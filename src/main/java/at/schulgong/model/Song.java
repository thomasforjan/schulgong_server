package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Model of song
 * @since May 2023
 */
@Getter
@Setter
@Table(name = "Song")
@NoArgsConstructor
@Entity
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "filePath")
  private String filePath;
}
