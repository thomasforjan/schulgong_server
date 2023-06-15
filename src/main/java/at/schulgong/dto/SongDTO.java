package at.schulgong.dto;

import java.util.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO of song Object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDTO extends RepresentationModel<SongDTO> {
    private long id;
    private String name;
    private String filePath;
    private String song;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongDTO songDTO)) return false;
        if (!super.equals(o)) return false;
        return id == songDTO.id
                && Objects.equals(name, songDTO.name)
                && Objects.equals(filePath, songDTO.filePath)
                && Objects.equals(song, songDTO.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, filePath, song);
    }
}
