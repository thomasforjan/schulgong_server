package at.schulgong.controller;

import at.schulgong.Holiday;
import at.schulgong.assembler.HolidayModelAssembler;
import at.schulgong.dto.HolidayDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.repository.HolidayRepository;
import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Controller to provide CRUD-functionality
 * @since April 2023
 */
@RestController
@RequestMapping("holidays")
@CrossOrigin
public class HolidayController {
  private final PlayRingtones playRingtones;
  private final HolidayRepository holidayRepository;
  private final HolidayModelAssembler assembler;

  public HolidayController(PlayRingtones playRingtones, HolidayRepository holidayRepository, HolidayModelAssembler assembler) {
    this.playRingtones = playRingtones;
    this.holidayRepository = holidayRepository;
    this.assembler = assembler;
  }

  /**
   * Get all holidays.
   *
   * @return all holidays
   */
  @GetMapping
  public CollectionModel<HolidayDTO> all() {
    List<Holiday> holidays = holidayRepository.findAll();
    return assembler.toCollectionModel(holidays).add(linkTo(methodOn(HolidayController.class).all()).withRel(Config.HOLIDAY.getUrl()));
  }

  /**
   * Get particular holiday by specific id.
   *
   * @param id takes holiday id
   * @return specific holiday based on its id
   */
  @GetMapping(value = "/{id}")
  public HolidayDTO one(@PathVariable long id) {
    Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Config.HOLIDAY.getException()));
    return assembler.toModel(holiday);
  }

  /**
   * Add new holiday.
   *
   * @param newHoliday takes given holiday body values
   * @return new holiday
   */
  @PostMapping
  ResponseEntity<?> newHoliday(@RequestBody HolidayDTO newHoliday) {
    Holiday holiday = DtoConverter.convertDtoToHoliday(newHoliday);
    HolidayDTO entityModel = assembler.toModel(holidayRepository.save(holiday));
    if (playRingtones.checkLoadHoliday(newHoliday)) {
      playRingtones.restart();
    }
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Update a particular holiday based on its id.
   *
   * @param newHoliday takes given holiday body values
   * @param id         takes holiday id
   * @return updated holiday
   */
  @PutMapping("/{id}")
  ResponseEntity<?> replaceHoliday(@RequestBody HolidayDTO newHoliday, @PathVariable Long id) {
    final AtomicBoolean isLoadRingtimes = new AtomicBoolean(false);
    Holiday updateHoliday = holidayRepository.findById(id).map(holiday -> {
      if (playRingtones.checkLoadHoliday(DtoConverter.convertHolidayToDTO(holiday))) {
        isLoadRingtimes.set(true);
      }
      holiday.setStartDate(newHoliday.getStartDate());
      holiday.setEndDate(newHoliday.getEndDate());
      holiday.setName(newHoliday.getName());
      holiday.setName(newHoliday.getName());
      return holidayRepository.save(holiday);
    }).orElseGet(() -> {
      newHoliday.setId(id);
      Holiday holiday = DtoConverter.convertDtoToHoliday(newHoliday);
      return holidayRepository.save(holiday);
    });

    HolidayDTO entityModel = assembler.toModel(updateHoliday);
    if (isLoadRingtimes.get() || playRingtones.checkLoadHoliday(newHoliday)) {
      playRingtones.restart();
    }
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Delete a particular holiday based on its id.
   *
   * @param id takes holiday id
   * @return deleted holiday
   */
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteHoliday(@PathVariable long id) {
    if (holidayRepository.existsById(id)) {
      boolean updatePlayRingtones = false;
      if (playRingtones.checkLoadHoliday(this.one(id))) {
        updatePlayRingtones = true;
      }
      holidayRepository.deleteById(id);
      if (updatePlayRingtones) {
        playRingtones.restart();
      }
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException(id, Config.HOLIDAY.getException());
    }
  }
}