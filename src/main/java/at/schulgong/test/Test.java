package at.schulgong.test;

import java.util.Objects;
/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="test")
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="ID")
  private long Id;

  @Column
  private String info;


  public Test(String info) {
    this.info = info;
  }


  /**
   * Method for equals
   *
   * @param o injected parameter
   * @return right entry
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Test test)) return false;
    return Id == test.Id && info.equals(test.info);
  }

  /**
   * method to hash
   *
   * @return right hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(Id, info);
  }
}
