package at.schulgong.assembler;

import at.schulgong.Ringtone;
import at.schulgong.controller.RingtoneController;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RingtoneModelAssembler extends RepresentationModelAssemblerSupport<Ringtone, RingtoneDTO> {

  public RingtoneModelAssembler(){
    super(RingtoneController.class, RingtoneDTO.class);
  }


  @Override
  public RingtoneDTO toModel(Ringtone ringTone){
    RingtoneDTO ringToneDTO = DtoConverter.convertRingtoneToDTO(ringTone);
    ringToneDTO.add(linkTo(methodOn(RingtoneController.class).one(ringToneDTO.getId())).withSelfRel());
    ringToneDTO.add(linkTo(methodOn(RingtoneController.class).all()).withRel("ringTones"));
    return ringToneDTO;
  }

  @Override
  public CollectionModel<RingtoneDTO> toCollectionModel(Iterable<? extends Ringtone> ringToneList) {
    List<RingtoneDTO> ringToneDTOS = new ArrayList<>();

    for (Ringtone ringTone : ringToneList) {
      RingtoneDTO ringToneDTO = DtoConverter.convertRingtoneToDTO(ringTone);
      ringToneDTO.add(linkTo(methodOn(RingtoneController.class).one(ringToneDTO.getId())).withSelfRel());
      ringToneDTO.add(linkTo(methodOn(RingtoneController.class).all()).withRel("ringTones"));
      ringToneDTOS.add(ringToneDTO);
    }
    return CollectionModel.of(ringToneDTOS);
  }
}
