package at.schulgong.controller;

import at.schulgong.assembler.RingtimeModelAssembler;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.dto.ServertimeDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.model.Hour;
import at.schulgong.model.Minute;
import at.schulgong.model.Ringtime;
import at.schulgong.model.Ringtone;
import at.schulgong.repository.HourRepository;
import at.schulgong.repository.MinuteRepository;
import at.schulgong.repository.RingtimeRepository;
import at.schulgong.repository.RingtoneRepository;
import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Controller to provide CRUD-functionality
 * @since April 2023
 */
@RestController
@RequestMapping("api/ringtimes")
@CrossOrigin
public class RingtimeController {
  private final PlayRingtones playRingtones;
  private final RingtimeRepository ringtimeRepository;
  private final RingtoneRepository ringtoneRepository;
  private final HourRepository hourRepository;
  private final MinuteRepository minuteRepository;
  private final RingtimeModelAssembler assembler;

  /**
   * Controller of Ringtime
   *
   * @param ringtimeRepository Repository of ringtime
   * @param assembler          assembler of ringtime
   * @param ringtoneRepository Repository of ringtone
   * @param hourRepository     Repository of hour
   * @param minuteRepository   Repository of minute
   */
  public RingtimeController(PlayRingtones playRingtones, RingtimeRepository ringtimeRepository, RingtimeModelAssembler assembler, RingtoneRepository ringtoneRepository, HourRepository hourRepository, MinuteRepository minuteRepository) {
    this.playRingtones = playRingtones;
    this.ringtimeRepository = ringtimeRepository;
    this.assembler = assembler;
    this.ringtoneRepository = ringtoneRepository;
    this.hourRepository = hourRepository;
    this.minuteRepository = minuteRepository;
  }

  /**
   * Get all ringtimes.
   *
   * @return all ringtime
   */
  @GetMapping
  public CollectionModel<RingtimeDTO> all() {
    List<Ringtime> ringtimes = ringtimeRepository.findAll();
    return assembler.toCollectionModel(ringtimes).add(linkTo(methodOn(RingtimeController.class).all()).withRel(Config.RINGTIME.getUrl()));
  }

  /**
   * Get particular ringtime by specific id.
   *
   * @param id takes ringtime id
   * @return specific ringtime based on its id
   */
  @GetMapping(value = "{id}")
  public RingtimeDTO one(@PathVariable long id) {
    Ringtime ringtime = ringtimeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Config.RINGTIME.getException()));
    return assembler.toModel(ringtime);
  }

  /**
   * Retrieves the current server time. This method is responsible for handling GET requests to
   * the "/server-time" endpoint. It fetches the current server time, formats it using the
   * ISO_DATE_TIME pattern, and then returns it as a ServertimeDTO wrapped in a ResponseEntity.
   *
   * @return a ResponseEntity containing the ServertimeDTO with the current server time
   */
  @GetMapping("/server-time")
  public ResponseEntity<ServertimeDTO> getServerTime() {
    LocalDateTime servertime = LocalDateTime.now();
    String formattedServertime = DateTimeFormatter.ISO_DATE_TIME.format(servertime);
    ServertimeDTO servertimeDTO = new ServertimeDTO(formattedServertime);
    return ResponseEntity.ok(servertimeDTO);
  }

  /**
   * Add new sensor.
   *
   * @param newRingtime takes given ringtime body values
   * @return new sensor
   */
  @PostMapping
  ResponseEntity<RingtimeDTO> newRingtime(@RequestBody RingtimeDTO newRingtime) {
    Ringtone ringtone = ringtoneRepository.findById(newRingtime.getRingtoneDTO().getId()).orElseThrow(() -> new EntityNotFoundException(newRingtime.getRingtoneDTO().getId(), Config.RINGTIME.getException()));
    Hour hour = hourRepository.findByHour(newRingtime.getPlayTime().getHour());
    Minute minute = minuteRepository.findByMinute(newRingtime.getPlayTime().getMinute());
    Ringtime ringtime = DtoConverter.convertDtoToRingtime(newRingtime, false);
    if (hour != null) {
      ringtime.setHour(hour);
    }
    if (minute != null) {
      ringtime.setMinute(minute);
    }
    ringtime.setRingtone(ringtone);
    RingtimeDTO entityModel = assembler.toModel(ringtimeRepository.save(ringtime));
    if (playRingtones.checkLoadRingtimes(newRingtime)) {
      playRingtones.restart();
    }
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Update a particular ringtime based on its id.
   *
   * @param newRingtime takes given ringtime body values
   * @param id          takes ringtime id
   * @return updated ringtime
   */
  @PutMapping("{id}")
  ResponseEntity<RingtimeDTO> replaceRingtime(@RequestBody RingtimeDTO newRingtime, @PathVariable long id) {
    final AtomicBoolean isLoadRingtimes = new AtomicBoolean(false);
    Ringtone ringtone = DtoConverter.convertDtoToRingtone(newRingtime.getRingtoneDTO());
    Hour hour = hourRepository.findByHour(newRingtime.getPlayTime().getHour());
    Minute minute = minuteRepository.findByMinute(newRingtime.getPlayTime().getMinute());
    Ringtime updateRingtime = ringtimeRepository.findById(id).map(ringtime -> {
      if (playRingtones.checkLoadRingtimes(DtoConverter.convertRingtimeToDTO(ringtime, false))) {
        isLoadRingtimes.set(true);
      }
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
      return ringtimeRepository.save(ringtime);
    }).orElseGet(() -> {
      newRingtime.setId(id);
      Ringtime ringtime = DtoConverter.convertDtoToRingtime(newRingtime, true);
      return ringtimeRepository.save(ringtime);
    });
    RingtimeDTO entityModel = assembler.toModel(updateRingtime);
    if (isLoadRingtimes.get() || playRingtones.checkLoadRingtimes(newRingtime)) {
      playRingtones.restart();
    }
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Delete a particular ringtime based on its id.
   *
   * @param id takes ringtime id
   * @return deleted ringtime
   */
  @DeleteMapping("{id}")
  ResponseEntity<RingtimeDTO> deleteRingtime(@PathVariable long id) {
    if (ringtimeRepository.existsById(id)) {
      boolean updatePlayRingtones = playRingtones.checkLoadRingtimes(this.one(id));
      ringtimeRepository.deleteById(id);
      if (updatePlayRingtones) {
        playRingtones.restart();
      }
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException(id, Config.RINGTIME.getException());
    }
  }

  /**
   * Delete all ringtimes.
   *
   * @return response
   */
  @DeleteMapping
  ResponseEntity<RingtimeDTO> deleteAllRingtimes() {
    ringtimeRepository.deleteAll();
    return ResponseEntity.ok().build();
  }
}
