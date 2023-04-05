package at.schulgong.test;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
public interface TestRepository extends JpaRepository<Test, Long> {
}
