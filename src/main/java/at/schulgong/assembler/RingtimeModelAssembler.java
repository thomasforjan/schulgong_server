package at.schulgong.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import at.schulgong.controller.RingtimeController;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.model.Ringtime;
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
 * @since April 2023
 */
@Component
public class RingtimeModelAssembler
        extends RepresentationModelAssemblerSupport<Ringtime, RingtimeDTO> {

    /** Constructor of assembler */
    public RingtimeModelAssembler() {
        super(RingtimeController.class, RingtimeDTO.class);
    }

    /**
     * Converts ringtime entity list to data transfer object (DTO) list.
     *
     * @param ringtime takes a ringtime object
     * @return ringtimeDTO
     */
    @Override
    public RingtimeDTO toModel(Ringtime ringtime) {
        RingtimeDTO ringtimeDTO = DtoConverter.convertRingtimeToDTO(ringtime, true);
        ringtimeDTO.add(
                linkTo(methodOn(RingtimeController.class).one(ringtimeDTO.getId())).withSelfRel());
        ringtimeDTO.add(linkTo(methodOn(RingtimeController.class).all()).withRel("periods"));
        return ringtimeDTO;
    }

    /**
     * Converts ringtime entity list to data transfer object (DTO) list.
     *
     * @param ringtimesList takes ringtime object list
     * @return ringtimeDTO list
     */
    @Override
    public CollectionModel<RingtimeDTO> toCollectionModel(
            Iterable<? extends Ringtime> ringtimesList) {
        List<RingtimeDTO> ringtimeDTOS = new ArrayList<>();
        for (Ringtime ringtime : ringtimesList) {
            RingtimeDTO ringtimeDTO = DtoConverter.convertRingtimeToDTO(ringtime, true);
            ringtimeDTO.add(
                    linkTo(methodOn(RingtimeController.class).one(ringtimeDTO.getId()))
                            .withSelfRel());
            ringtimeDTO.add(
                    linkTo(methodOn(RingtimeController.class).all())
                            .withRel(Config.RINGTIME.getUrl()));
            ringtimeDTOS.add(ringtimeDTO);
        }
        return CollectionModel.of(ringtimeDTOS);
    }
}
