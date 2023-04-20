package at.schulgong.repository;

import at.schulgong.Hour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourRepository extends JpaRepository<Hour, Long> {
  public Hour findByHour(int hour);
}
