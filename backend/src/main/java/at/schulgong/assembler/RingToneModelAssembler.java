package at.schulgong.assembler;

import at.schulgong.RingTone;
import at.schulgong.controller.RingToneController;
import at.schulgong.dto.RingToneDTO;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RingToneModelAssembler extends RepresentationModelAssemblerSupport<RingTone, RingToneDTO> {

  public RingToneModelAssembler(){
    super(RingToneController.class, RingToneDTO.class);
  }


  @Override
  public RingToneDTO toModel(RingTone ringTone){
    RingToneDTO ringToneDTO = DtoConverter.convertRingToneToDTO(ringTone);
    ringToneDTO.add(linkTo(methodOn(RingToneController.class).one(ringToneDTO.getId())).withSelfRel());
    ringToneDTO.add(linkTo(methodOn(RingToneController.class).all()).withRel("ringTones"));
    return ringToneDTO;
  }

  @Override
  public CollectionModel<RingToneDTO> toCollectionModel(Iterable<? extends RingTone> ringToneList) {
    List<RingToneDTO> ringToneDTOS = new ArrayList<>();

    for (RingTone ringTone : ringToneList) {
      RingToneDTO ringToneDTO = DtoConverter.convertRingToneToDTO(ringTone);
      ringToneDTO.add(linkTo(methodOn(RingToneController.class).one(ringToneDTO.getId())).withSelfRel());
      ringToneDTO.add(linkTo(methodOn(RingToneController.class).all()).withRel("ringTones"));
      ringToneDTOS.add(ringToneDTO);
    }
    return CollectionModel.of(ringToneDTOS);
  }
}
