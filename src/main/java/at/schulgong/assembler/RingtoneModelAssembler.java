package at.schulgong.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import at.schulgong.controller.RingtoneController;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.model.Ringtone;
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
public class RingtoneModelAssembler
        extends RepresentationModelAssemblerSupport<Ringtone, RingtoneDTO> {

    /** Constructor of assembler */
    public RingtoneModelAssembler() {
        super(RingtoneController.class, RingtoneDTO.class);
    }

    /**
     * Method to change object ringtone to the DTO and to add selfrel and global rel.
     *
     * @param ringtone Ringtone object
     * @return ringtoneDTO
     */
    @Override
    public RingtoneDTO toModel(Ringtone ringtone) {
        RingtoneDTO ringtoneDTO = DtoConverter.convertRingtoneToDTO(ringtone);
        ringtoneDTO.add(
                linkTo(methodOn(RingtoneController.class).one(ringtoneDTO.getId())).withSelfRel());
        ringtoneDTO.add(
                linkTo(methodOn(RingtoneController.class).all()).withRel(Config.RINGTONE.getUrl()));
        return ringtoneDTO;
    }

    /**
     * Method to create List of Ringtone objects adding selfrel and global rel.
     *
     * @param ringtoneList must not be {@literal null}.
     * @return CollectionModel of ringtoneDTO's
     */
    @Override
    public CollectionModel<RingtoneDTO> toCollectionModel(
            Iterable<? extends Ringtone> ringtoneList) {
        List<RingtoneDTO> ringtoneDTOS = new ArrayList<>();
        for (Ringtone ringtone : ringtoneList) {
            RingtoneDTO ringtoneDTO = DtoConverter.convertRingtoneToDTO(ringtone);
            ringtoneDTO.add(
                    linkTo(methodOn(RingtoneController.class).one(ringtoneDTO.getId()))
                            .withSelfRel());
            ringtoneDTO.add(
                    linkTo(methodOn(RingtoneController.class).all())
                            .withRel(Config.RINGTONE.getUrl()));
            ringtoneDTOS.add(ringtoneDTO);
        }
        return CollectionModel.of(ringtoneDTOS);
    }
}
