package at.schulgong.assembler;

import at.schulgong.Holiday;
import at.schulgong.controller.HolidayController;
import at.schulgong.dto.HolidayDTO;
import at.schulgong.util.Config;
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
public class HolidayModelAssembler extends RepresentationModelAssemblerSupport<Holiday, HolidayDTO> {
  public HolidayModelAssembler() {
    super(HolidayController.class, HolidayDTO.class);
  }

  /**
   * Method to change object holiday to the DTO and to add selfrel and global rel.
   *
   * @param holiday holiday object
   * @return holidayDTO
   */
  @Override
  public HolidayDTO toModel(Holiday holiday) {
    HolidayDTO holidayDTO = DtoConverter.convertHolidayToDTO(holiday);
    holidayDTO.add(linkTo(methodOn(HolidayController.class).one(holidayDTO.getId())).withSelfRel());
    holidayDTO.add(linkTo(methodOn(HolidayController.class).all()).withRel(Config.HOLIDAY.getUrl()));
    return holidayDTO;
  }

  /**
   * Method to create List of holiday objects adding selfrel and global rel.
   *
   * @param holidaysList must not be {@literal null}.
   * @return CollectionModel of holiday's
   */
  @Override
  public CollectionModel<HolidayDTO> toCollectionModel(Iterable<? extends Holiday> holidaysList) {
    List<HolidayDTO> holidayDTOS = new ArrayList<>();

    for (Holiday holiday : holidaysList) {
      HolidayDTO holidayDTO = DtoConverter.convertHolidayToDTO(holiday);
      holidayDTO.add(linkTo(methodOn(HolidayController.class).one(holidayDTO.getId())).withSelfRel());
      holidayDTO.add(linkTo(methodOn(HolidayController.class).all()).withRel(Config.HOLIDAY.getUrl()));
      holidayDTOS.add(holidayDTO);
    }
    return CollectionModel.of(holidayDTOS);
  }

}
