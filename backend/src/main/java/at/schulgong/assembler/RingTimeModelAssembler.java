package at.schulgong.assembler;

import at.schulgong.RingTime;
import at.schulgong.controller.RingTimeController;
import at.schulgong.dto.RingTimeDTO;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RingTimeModelAssembler extends RepresentationModelAssemblerSupport<RingTime, RingTimeDTO> {

  public RingTimeModelAssembler() {
    super(RingTimeController.class, RingTimeDTO.class);
  }

  /**
   * Converts ringTime entity list to data transfer object (DTO) list.
   *
   * @param ringTime takes a ringTime object
   * @return ringTimeDTO
   */
  @Override
  public RingTimeDTO toModel(RingTime ringTime){
    RingTimeDTO ringTimeDTO = DtoConverter.convertRingTimeToDTO(ringTime, true);
    ringTimeDTO.add(linkTo(methodOn(RingTimeController.class).one(ringTimeDTO.getId())).withSelfRel());
    ringTimeDTO.add(linkTo(methodOn(RingTimeController.class).all()).withRel("periods"));
    return ringTimeDTO;
  }

  /**
   * Converts ringTime entity list to data transfer object (DTO) list.
   *
   * @param ringTimesList takes ringTime object list
   * @return ringTimeDTO list
   */
  @Override
  public CollectionModel<RingTimeDTO> toCollectionModel(Iterable<? extends RingTime> ringTimesList) {
    List<RingTimeDTO> ringTimeDTOS = new ArrayList<>();

    for (RingTime ringTime : ringTimesList) {
      RingTimeDTO ringTimeDTO = DtoConverter.convertRingTimeToDTO(ringTime, true);
      ringTimeDTO.add(linkTo(methodOn(RingTimeController.class).one(ringTimeDTO.getId())).withSelfRel());
      ringTimeDTO.add(linkTo(methodOn(RingTimeController.class).all()).withRel("ringTimes"));
      ringTimeDTOS.add(ringTimeDTO);
    }
    return CollectionModel.of(ringTimeDTOS);
  }



}
