package at.schulgong.repository;

import at.schulgong.Hour;
import at.schulgong.Minute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinuteRepository extends JpaRepository<Minute, Long> {
  public Minute findByMinute(int minute);
}
