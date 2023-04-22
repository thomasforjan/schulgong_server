package at.schulgong.controller;

import at.schulgong.Ringtone;
import at.schulgong.assembler.RingtoneModelAssembler;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.exception.EntityNotFoundException;
import at.schulgong.repository.RingtoneRepository;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Controller to provide CRUD-functionality
 * @since April 2023
 */
@RestController
@RequestMapping("ringtones")
@CrossOrigin
public class RingtoneController {
  private final RingtoneRepository ringtoneRepository;
  private final RingtoneModelAssembler assembler;

  public RingtoneController(RingtoneRepository ringtoneRepository, RingtoneModelAssembler assembler) {
    this.ringtoneRepository = ringtoneRepository;
    this.assembler = assembler;
  }

  /**
   * Get all ringTones.
   *
   * @return all ringtones
   */
  @GetMapping
  public CollectionModel<RingtoneDTO> all() {
    List<Ringtone> ringtones = ringtoneRepository.findAll();
    return assembler.toCollectionModel(ringtones).add(linkTo(methodOn(RingtoneController.class).all()).withRel(Config.RINGTONE.getUrl()));
  }

  /**
   * Get particular ringtone by specific id.
   *
   * @param id takes ringtones id
   * @return specific ringtones based on its id
   */
  @GetMapping(value = "/{id}")
  public RingtoneDTO one(@PathVariable long id) {
    Ringtone ringtone = ringtoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Config.RINGTONE.getUrl()));
    return assembler.toModel(ringtone);
  }

  /**
   * Add new ringtone.
   *
   * @param song Multipartfile (audiofile)
   * @param name name of new entry
   * @return new ringtone
   */
  @PostMapping
  ResponseEntity<?> newRingtone(@RequestParam("song") MultipartFile song, @RequestParam("name") String name) {
    // check if ContentType of the Post Request (MultipartFile) is audio
    if (!song.getContentType().contains("audio")) {
      return new ResponseEntity<>("False datatype", HttpStatus.BAD_REQUEST);
    }

    Ringtone ringtone = getChangedRingtone(song, name);

    try {
      File dir = new File(Config.FILEPATH.getPath());
      if (!dir.exists()) {
        dir.mkdirs();
      }

      if (dir.exists()) {
        ringtone = changeFileName(ringtone, 2);
      }
      Path filePath = Paths.get(ringtone.getPath());
      song.transferTo(filePath.toFile());
    } catch (IOException e) {
      return new ResponseEntity<>("Failed to upload", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    RingtoneDTO entityModel = assembler.toModel(ringtoneRepository.save(ringtone));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  /**
   * Update a particular ringtone based on its id and with a new audiofile
   *
   * @param newSong Multipart file do updated the audiofile
   * @param name    name of entry
   * @param id      id of entry
   * @return updated ringtone with audiofile
   */
  @PutMapping("/{id}")
  ResponseEntity<?> replaceRingtone(@RequestParam("song") MultipartFile newSong, @RequestParam("name") String name, @PathVariable long id) {
    // check if ContentType of the Post Request (MultipartFile) is an audiofile
    if (!newSong.getContentType().contains("audio")) {
      return new ResponseEntity<>("False datatype", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Ringtone oldRingtone = getChangedRingtone(newSong, name);

    Ringtone updateRingtone = ringtoneRepository.findById(id).map(ringtone -> {
      ringtone.setName(oldRingtone.getName());
      ringtone.setFilename(oldRingtone.getFilename());
      ringtone.setPath(oldRingtone.getPath());
      ringtone.setDate(oldRingtone.getDate());
      ringtone.setSize(oldRingtone.getSize());
      return ringtoneRepository.save(ringtone);
    }).orElseGet(() -> {
      oldRingtone.setId(id);
      return ringtoneRepository.save(oldRingtone);
    });

    RingtoneDTO entityModel = assembler.toModel(updateRingtone);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }


  /**
   * Update a particular ringtone based on its id and without a new audiofile
   *
   * @param newRingtone Multipart file do updated the audiofile
   * @param id          id of entry
   * @return updated ringtone without audiofile
   */
  @PutMapping("/description/{id}")
  ResponseEntity<?> replaceRingtone(@RequestBody RingtoneDTO newRingtone, @PathVariable long id) {
    Ringtone updateRingtone = ringtoneRepository.findById(id).map(ringtone -> {
      ringtone.setName(newRingtone.getName());
      ringtone.setFilename(newRingtone.getFilename());
      ringtone.setPath(newRingtone.getPath());
      ringtone.setDate(newRingtone.getDate());
      ringtone.setSize(newRingtone.getSize());
      return ringtoneRepository.save(ringtone);
    }).orElseGet(() -> {
      newRingtone.setId(id);
      Ringtone ringtone = DtoConverter.convertDtoToRingtone(newRingtone);
      return ringtoneRepository.save(ringtone);
    });

    RingtoneDTO entityModel = assembler.toModel(updateRingtone);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }


  /**
   * Delete a particular ringtone based on its id.
   *
   * @param id takes ringtone id
   * @return deleted ringtone
   */
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteRingtone(@PathVariable long id) {
    if (ringtoneRepository.existsById(id)) {
      RingtoneDTO ringtoneDTO = one(id);

      File file = new File(ringtoneDTO.getPath());
      if (file.exists() && file.canWrite()){
        file.delete();
      }
      ringtoneRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException(id, Config.RINGTONE.getException());
    }
  }


  /**
   * method to change a MultipartFile into an object of RingTone
   *
   * @param multipartFile MultiPartfile (from Post-Request)
   * @param name          Name (from Post-Request)
   * @return Object of Ringtone
   */
  private Ringtone getChangedRingtone(MultipartFile multipartFile, String name) {
    Ringtone ringtone = new Ringtone();

    Path filePath = Paths.get(Config.FILEPATH.getPath() + File.separator + multipartFile.getOriginalFilename());
    ringtone.setPath(filePath.toString());
    ringtone.setName(name);
    ringtone.setFilename(multipartFile.getOriginalFilename());
    ringtone.setSize(Math.round((double) multipartFile.getSize() / 1000000));
    ringtone.setDate(LocalDate.now());
    return ringtone;
  }

  /**
   * Method to change Filename and Path of a new Post-Request to provide 1:1 relationship between entry and audiofile
   *
   * @param ringtone ringtone object of Post-Request
   * @param i        counter
   * @return new Ringtone
   */
  private Ringtone changeFileName(Ringtone ringtone, int i) {
    File file = new File(ringtone.getPath());

    if (file.exists()) {
      // get index of last dot, e.g. "."mp3
      int lastDotIndex = ringtone.getFilename().lastIndexOf(".");
      // get format of the audiofile
      String format = ringtone.getFilename().substring(lastDotIndex);
      // remove format from Filename
      String newFileName = ringtone.getFilename().replace(format, "");
      // check if counter is already set at the filename and if it is before the dot
      if (newFileName.contains(String.valueOf(i - 1))) {
        // remove counter from filename
        newFileName = newFileName.substring(0, newFileName.length() - 1);
      }
      // concat filename
      newFileName = newFileName + i + format;

      // replace old filename with new filename
      String newPath = ringtone.getPath().replace(ringtone.getFilename(), newFileName);
      ringtone.setFilename(newFileName);
      ringtone.setPath(newPath);
    }

    System.out.println(ringtone.getFilename());
    System.out.println(ringtone.getPath());

    // if new File already exist, counter +1 and recall changeFileName() with new ringtone object and new counter
    if (file.exists()) {
      i++;
      changeFileName(ringtone, i);
    }

    return ringtone;
  }


}
