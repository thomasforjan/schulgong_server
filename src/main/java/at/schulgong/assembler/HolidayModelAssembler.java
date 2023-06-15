package at.schulgong.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import at.schulgong.controller.HolidayController;
import at.schulgong.dto.HolidayDTO;
import at.schulgong.model.Holiday;
import at.schulgong.util.Config;
import at.schulgong.util.DtoConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assembler to add self and global url to entry to make it Restful
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
@Component
public class HolidayModelAssembler
        extends RepresentationModelAssemblerSupport<Holiday, HolidayDTO> {

    /** Constructor of assembler */
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
        holidayDTO.add(
                linkTo(methodOn(HolidayController.class).one(holidayDTO.getId())).withSelfRel());
        holidayDTO.add(
                linkTo(methodOn(HolidayController.class).all()).withRel(Config.HOLIDAY.getUrl()));
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
            holidayDTO.add(
                    linkTo(methodOn(HolidayController.class).one(holidayDTO.getId()))
                            .withSelfRel());
            holidayDTO.add(
                    linkTo(methodOn(HolidayController.class).all())
                            .withRel(Config.HOLIDAY.getUrl()));
            holidayDTOS.add(holidayDTO);
        }
        return CollectionModel.of(holidayDTOS);
    }
}
