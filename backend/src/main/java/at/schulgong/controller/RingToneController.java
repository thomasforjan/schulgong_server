package at.schulgong.controller;

import at.schulgong.RingTone;
import at.schulgong.assembler.RingToneModelAssembler;
import at.schulgong.dto.RingToneDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.repository.RingToneRepository;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("ringTones")
@CrossOrigin
public class RingToneController {
  private final RingToneRepository ringToneRepository;
  private final RingToneModelAssembler assembler;


  public RingToneController(RingToneRepository ringToneRepository, RingToneModelAssembler assembler) {
    this.ringToneRepository = ringToneRepository;
    this.assembler = assembler;
  }

  /**
   * Get all ringTones.
   *
   * @return all ringTones
   */
  @GetMapping
  public CollectionModel<RingToneDTO> all() {
    List<RingTone> ringTones = ringToneRepository.findAll();
    return assembler.toCollectionModel(ringTones).add(linkTo(methodOn(RingToneController.class).all()).withRel("ringTones"));
  }

  /**
   * Get particular ringTone by specific id.
   *
   * @param id takes ringTones id
   * @return specific ringTones based on its id
   */
  @GetMapping(value = "/{id}")
  public RingToneDTO one(@PathVariable long id) {
    RingTone ringTone = ringToneRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(id, "ringTone"));
    return assembler.toModel(ringTone);
  }

  /**
   * Add new ringTone.
   *
   * @param ringToneDTO takes given ringTone body values
   * @return new ringTone
   */
  @PostMapping
  ResponseEntity<?> newRingTone(@RequestBody RingToneDTO ringToneDTO) {
    /*if (!RequestValidator.checkRequestBodySensor(newRingTime)) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Your sending ringTime data are not correct!");
    }*/
    RingTone ringTone = DtoConverter.convertDtoToRingTone(ringToneDTO);
    RingToneDTO entityModel = assembler.toModel(ringToneRepository.save(ringTone));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Update a particular ringTone based on its id.
   *
   * @param newRingTone takes given ringTone body values
   * @param id          takes ringTone id
   * @return updated ringTone
   */
  @PutMapping("/{id}")
  ResponseEntity<?> replaceRingTone(@RequestBody RingToneDTO newRingTone, @PathVariable long id) {

    /*if (!RequestValidator.checkRequestBodySensor(newRingTime) && id <= 0) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Your sending sensor data are not correct!");
    }*/

    RingTone updateRingTone = ringToneRepository
      .findById(id)
      .map(ringTone -> {
        ringTone.setName(newRingTone.getName());
        ringTone.setFilename(newRingTone.getFilename());
        ringTone.setPath(newRingTone.getPath());
        ringTone.setDate(newRingTone.getDate());
        ringTone.setSize(newRingTone.getSize());
        return ringToneRepository.save(ringTone);
      })
      .orElseGet(() -> {
        newRingTone.setId(id);
        RingTone ringTone = DtoConverter.convertDtoToRingTone(newRingTone);
        return ringToneRepository.save(ringTone);
      });

    RingToneDTO entityModel = assembler.toModel(updateRingTone);

    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Delete a particular ringTone based on its id.
   *
   * @param id takes ringTone id
   * @return deleted ringTone
   */
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteRingTone(@PathVariable long id) {
    if (ringToneRepository.existsById(id)) {
      ringToneRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException(id, "ringTone");
    }
  }
}
