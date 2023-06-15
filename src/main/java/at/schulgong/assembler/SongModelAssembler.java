package at.schulgong.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import at.schulgong.controller.HolidayController;
import at.schulgong.controller.LiveController;
import at.schulgong.dto.SongDTO;
import at.schulgong.model.Song;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assembler to add self and global url to entry to make it Restful
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Component
public class SongModelAssembler extends RepresentationModelAssemblerSupport<Song, SongDTO> {

    /** Constructor of assembler */
    public SongModelAssembler() {
        super(LiveController.class, SongDTO.class);
    }

    /**
     * Method to change object song to the DTO and to add selfrel and global rel.
     *
     * @param song song object
     * @return songDTO
     */
    @Override
    public SongDTO toModel(Song song) {
        SongDTO songDTO = DtoConverter.convertSongToDTO(song);
        songDTO.add(linkTo(methodOn(LiveController.class).oneSong(songDTO.getId())).withSelfRel());
        songDTO.add(
                linkTo(methodOn(LiveController.class).allSongs()).withRel(Config.SONG.getUrl()));
        return songDTO;
    }

    /**
     * Method to create List of song objects adding selfrel and global rel.
     *
     * @param songList must not be {@literal null}.
     * @return CollectionModel of song's
     */
    @Override
    public CollectionModel<SongDTO> toCollectionModel(Iterable<? extends Song> songList) {
        List<SongDTO> songDTOS = new ArrayList<>();
        for (Song song : songList) {
            SongDTO songDTO = DtoConverter.convertSongToDTO(song);
            songDTO.add(
                    linkTo(methodOn(HolidayController.class).one(songDTO.getId())).withSelfRel());
            songDTO.add(
                    linkTo(methodOn(HolidayController.class).all()).withRel(Config.SONG.getUrl()));
            songDTOS.add(songDTO);
        }
        return CollectionModel.of(songDTOS);
    }
}
