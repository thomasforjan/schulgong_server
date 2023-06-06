package at.schulgong.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote DTO of configuration Object
 * @since May 2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigurationDTO extends RepresentationModel<ConfigurationDTO> {
  private String password;
  private int ringtimeVolume;
  private int alarmVolume;
  private int announcementVolume;
  private String ringtimeDirectory;
  private String playlistDirectory;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    ConfigurationDTO that = (ConfigurationDTO) o;
    return ringtimeVolume == that.ringtimeVolume && alarmVolume == that.alarmVolume && announcementVolume == that.announcementVolume && Objects.equals(password, that.password) && Objects.equals(ringtimeDirectory, that.ringtimeDirectory) && Objects.equals(playlistDirectory, that.playlistDirectory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), password, ringtimeVolume, alarmVolume, announcementVolume, ringtimeDirectory, playlistDirectory);
  }

  @Override
  public String toString() {
    return "ConfigurationDTO{" +
      "password='" + password + '\'' +
      ", ringtimeVolume=" + ringtimeVolume +
      ", alarmVolume=" + alarmVolume +
      ", announcementVolume=" + announcementVolume +
      ", ringtimeDirectory='" + ringtimeDirectory + '\'' +
      ", playlistDirectory='" + playlistDirectory + '\'' +
      '}';
  }
}
