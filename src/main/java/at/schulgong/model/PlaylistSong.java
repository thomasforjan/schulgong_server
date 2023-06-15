package at.schulgong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model of playlistSong
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Getter
@Setter
@Table(name = "PlaylistSong")
@NoArgsConstructor
@Entity
public class PlaylistSong {
    @Id private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "song_ID")
    private Song song;
}
