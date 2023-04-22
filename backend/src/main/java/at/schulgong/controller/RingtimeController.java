package at.schulgong.controller;

import at.schulgong.Hour;
import at.schulgong.Minute;
import at.schulgong.Ringtime;
import at.schulgong.Ringtone;
import at.schulgong.assembler.RingtimeModelAssembler;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.repository.HourRepository;
import at.schulgong.repository.MinuteRepository;
import at.schulgong.repository.RingtimeRepository;
import at.schulgong.repository.RingtoneRepository;
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
public class RingtimeController {
  private final RingtimeRepository ringTimeRepository;
  private final RingtoneRepository ringToneRepository;

  private final HourRepository hourRepository;

  private final MinuteRepository minuteRepository;

  private final RingtimeModelAssembler assembler;


  public RingtimeController(RingtimeRepository ringTimeRepository, RingtimeModelAssembler assembler,
                            RingtoneRepository ringToneRepository, HourRepository hourRepository, MinuteRepository minuteRepository) {
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
  public CollectionModel<RingtimeDTO> all() {
    LocalTime lt = LocalTime.of(8,0);
    List<Ringtime> ringtimes = ringTimeRepository.findAll();
    return assembler.toCollectionModel(ringtimes).add(linkTo(methodOn(RingtimeController.class).all()).withRel("ringTimes"));
  }

  /**
   * Get particular ringTime by specific id.
   *
   * @param id takes ringTime id
   * @return specific ringTime based on its id
   */
  @GetMapping(value = "{id}")
  public RingtimeDTO one(@PathVariable long id) {
    Ringtime ringTime = ringTimeRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(id, "ring time"));
    return assembler.toModel(ringTime);
  }

  /**
   * Add new sensor.
   *
   * @param newRingtime takes given ringTime body values
   * @return new sensor
   */
  @PostMapping
  ResponseEntity<?> newRingtime(@RequestBody RingtimeDTO newRingtime) {
    /*if (!RequestValidator.checkRequestBodySensor(newRingtime)) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Your sending ringtime data are not correct!");
    }*/
    Ringtone ringtone = ringToneRepository.findById(newRingtime.getRingtoneDTO().getId())
      .orElseThrow(() -> new EntityNotFoundException(newRingtime.getRingtoneDTO().getId(), "ring tone"));
    Hour hour = hourRepository.findByHour(newRingtime.getPlayTime().getHour());
    Minute minute = minuteRepository.findByMinute(newRingtime.getPlayTime().getMinute());
    Ringtime ringtime = DtoConverter.convertDtoToRingtime(newRingtime, false);
    if(hour != null) {
      ringtime.setHour(hour);
    }
    if(minute != null) {
      ringtime.setMinute(minute);
    }
    ringtime.setRingtone(ringtone);
    RingtimeDTO entityModel = assembler.toModel(ringTimeRepository.save(ringtime));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Update a particular ringTime based on its id.
   *
   * @param newRingtime takes given ringTime body values
   * @param id          takes ringTime id
   * @return updated ringTime
   */
  @PutMapping("{id}")
  ResponseEntity<?> replaceRingtime(@RequestBody RingtimeDTO newRingtime, @PathVariable long id) {

    /*if (!RequestValidator.checkRequestBodySensor(newRingtime) && id <= 0) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Your sending sensor data are not correct!");
    }*/

    Ringtone ringtone = DtoConverter.convertDtoToRingtone(newRingtime.getRingtoneDTO());
    Hour hour = hourRepository.findByHour(newRingtime.getPlayTime().getHour());
    Minute minute = minuteRepository.findByMinute(newRingtime.getPlayTime().getMinute());
    Ringtime updateRingtime = ringTimeRepository
      .findById(id)
      .map(ringtime -> {
        ringtime.setName(newRingtime.getName());
        ringtime.setRingtone(ringtone);
        ringtime.setStartDate(newRingtime.getStartDate());
        ringtime.setEndDate(newRingtime.getEndDate());
        ringtime.setMonday(newRingtime.isMonday());
        ringtime.setTuesday(newRingtime.isTuesday());
        ringtime.setWednesday(newRingtime.isWednesday());
        ringtime.setThursday(newRingtime.isThursday());
        ringtime.setFriday(newRingtime.isFriday());
        ringtime.setSaturday(newRingtime.isSaturday());
        ringtime.setSunday(newRingtime.isSunday());
        ringtime.setHour(hour);
        ringtime.setMinute(minute);
//        ringTime.setAddInfo(newRingtime.getAddInfo());
        return ringTimeRepository.save(ringtime);
      })
      .orElseGet(() -> {
        newRingtime.setId(id);
        Ringtime ringTime = DtoConverter.convertDtoToRingtime(newRingtime, true);
        return ringTimeRepository.save(ringTime);
      });

    RingtimeDTO entityModel = assembler.toModel(updateRingtime);

    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Delete a particular ringTime based on its id.
   *
   * @param id takes ringTime id
   * @return deleted ringTime
   */
  @DeleteMapping("{id}")
  ResponseEntity<?> deleteRingtime(@PathVariable long id) {
    if (ringTimeRepository.existsById(id)) {
      ringTimeRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException(id, "ring time");
    }
  }

}
