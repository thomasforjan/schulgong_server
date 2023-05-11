package at.schulgong.assembler;

import at.schulgong.Ringtime;
import at.schulgong.controller.RingtimeController;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Assembler to add self and global url to entry to make it Restful
 * @since April 2023
 */
@Component
public class RingtimeModelAssembler extends RepresentationModelAssemblerSupport<Ringtime, RingtimeDTO> {

  public RingtimeModelAssembler() {
    super(RingtimeController.class, RingtimeDTO.class);
  }

  /**
   * Converts ringTime entity list to data transfer object (DTO) list.
   *
   * @param ringtime takes a ringtime object
   * @return ringtimeDTO
   */
  @Override
  public RingtimeDTO toModel(Ringtime ringtime){
    RingtimeDTO ringtimeDTO = DtoConverter.convertRingtimeToDTO(ringtime, true);
    ringtimeDTO.add(linkTo(methodOn(RingtimeController.class).one(ringtimeDTO.getId())).withSelfRel());
    ringtimeDTO.add(linkTo(methodOn(RingtimeController.class).all()).withRel("periods"));
    return ringtimeDTO;
  }

  /**
   * Converts ringTime entity list to data transfer object (DTO) list.
   *
   * @param ringtimesList takes ringtime object list
   * @return ringtimeDTO list
   */
  @Override
  public CollectionModel<RingtimeDTO> toCollectionModel(Iterable<? extends Ringtime> ringtimesList) {
    List<RingtimeDTO> ringtimeDTOS = new ArrayList<>();

    for (Ringtime ringtime : ringtimesList) {
      RingtimeDTO ringtimeDTO = DtoConverter.convertRingtimeToDTO(ringtime, true);
      ringtimeDTO.add(linkTo(methodOn(RingtimeController.class).one(ringtimeDTO.getId())).withSelfRel());
      ringtimeDTO.add(linkTo(methodOn(RingtimeController.class).all()).withRel("ringTimes"));
      ringtimeDTOS.add(ringtimeDTO);
    }
    return CollectionModel.of(ringtimeDTOS);
  }



}
