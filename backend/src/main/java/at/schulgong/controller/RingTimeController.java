package at.schulgong.controller;

import at.schulgong.Hour;
import at.schulgong.Minute;
import at.schulgong.RingTime;
import at.schulgong.RingTone;
import at.schulgong.assembler.RingTimeModelAssembler;
import at.schulgong.dto.RingTimeDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.repository.HourRepository;
import at.schulgong.repository.MinuteRepository;
import at.schulgong.repository.RingTimeRepository;
import at.schulgong.repository.RingToneRepository;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("periods")
@CrossOrigin
public class RingTimeController {
  private final RingTimeRepository ringTimeRepository;
  private final RingToneRepository ringToneRepository;

  private final HourRepository hourRepository;

  private final MinuteRepository minuteRepository;

  private final RingTimeModelAssembler assembler;


  public RingTimeController(RingTimeRepository ringTimeRepository, RingTimeModelAssembler assembler,
                            RingToneRepository ringToneRepository, HourRepository hourRepository, MinuteRepository minuteRepository) {
    this.ringTimeRepository = ringTimeRepository;
    this.assembler = assembler;
    this.ringToneRepository = ringToneRepository;
    this.hourRepository = hourRepository;
    this.minuteRepository = minuteRepository;
  }

  /**
   * Get all ringTime.
   *
   * @return all ringTime
   */
  @GetMapping
  public CollectionModel<RingTimeDTO> all() {
    LocalTime lt = LocalTime.of(8,0);
    List<RingTime> ringTimes = ringTimeRepository.findAll();
    return assembler.toCollectionModel(ringTimes).add(linkTo(methodOn(RingTimeController.class).all()).withRel("ringTimes"));
  }

  /**
   * Get particular ringTime by specific id.
   *
   * @param id takes ringTime id
   * @return specific ringTime based on its id
   */
  @GetMapping(value = "{id}")
  public RingTimeDTO one(@PathVariable long id) {
    RingTime ringTime = ringTimeRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(id, "ring time"));
    return assembler.toModel(ringTime);
  }

  /**
   * Add new sensor.
   *
   * @param newRingTime takes given ringTime body values
   * @return new sensor
   */
  @PostMapping
  ResponseEntity<?> newRingTime(@RequestBody RingTimeDTO newRingTime) {
    /*if (!RequestValidator.checkRequestBodySensor(newRingTime)) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Your sending ringTime data are not correct!");
    }*/
    RingTone ringTone = ringToneRepository.findById(newRingTime.getRingToneDTO().getId())
      .orElseThrow(() -> new EntityNotFoundException(newRingTime.getRingToneDTO().getId(), "ring tone"));
    Hour hour = hourRepository.findByHour(newRingTime.getPlayTime().getHour());
    Minute minute = minuteRepository.findByMinute(newRingTime.getPlayTime().getMinute());
    RingTime ringTime = DtoConverter.convertDtoToRingTime(newRingTime, false);
    if(hour != null) {
      ringTime.setHour(hour);
    }
    if(minute != null) {
      ringTime.setMinute(minute);
    }
    ringTime.setRingTone(ringTone);
    RingTimeDTO entityModel = assembler.toModel(ringTimeRepository.save(ringTime));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Update a particular ringTime based on its id.
   *
   * @param newRingTime takes given ringTime body values
   * @param id          takes ringTime id
   * @return updated ringTime
   */
  @PutMapping("{id}")
  ResponseEntity<?> replaceRingTime(@RequestBody RingTimeDTO newRingTime, @PathVariable long id) {

    /*if (!RequestValidator.checkRequestBodySensor(newRingTime) && id <= 0) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Your sending sensor data are not correct!");
    }*/

    RingTone ringTone = DtoConverter.convertDtoToRingTone(newRingTime.getRingToneDTO());
    Hour hour = hourRepository.findByHour(newRingTime.getPlayTime().getHour());
    Minute minute = minuteRepository.findByMinute(newRingTime.getPlayTime().getMinute());
    RingTime updateRingTime = ringTimeRepository
      .findById(id)
      .map(ringTime -> {
        ringTime.setName(newRingTime.getName());
        ringTime.setRingTone(ringTone);
        ringTime.setStartDate(newRingTime.getStartDate());
        ringTime.setEndDate(newRingTime.getEndDate());
        ringTime.setMonday(newRingTime.isMonday());
        ringTime.setTuesday(newRingTime.isTuesday());
        ringTime.setWednesday(newRingTime.isWednesday());
        ringTime.setThursday(newRingTime.isThursday());
        ringTime.setFriday(newRingTime.isFriday());
        ringTime.setSaturday(newRingTime.isSaturday());
        ringTime.setSunday(newRingTime.isSunday());
        ringTime.setHour(hour);
        ringTime.setMinute(minute);
//        ringTime.setAddInfo(newRingTime.getAddInfo());
        return ringTimeRepository.save(ringTime);
      })
      .orElseGet(() -> {
        newRingTime.setId(id);
        RingTime ringTime = DtoConverter.convertDtoToRingTime(newRingTime, true);
        return ringTimeRepository.save(ringTime);
      });

    RingTimeDTO entityModel = assembler.toModel(updateRingTime);

    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Delete a particular ringTime based on its id.
   *
   * @param id takes ringTime id
   * @return deleted ringTime
   */
  @DeleteMapping("{id}")
  ResponseEntity<?> deleteRingTime(@PathVariable long id) {
    if (ringTimeRepository.existsById(id)) {
      ringTimeRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException(id, "ring time");
    }
  }

}
