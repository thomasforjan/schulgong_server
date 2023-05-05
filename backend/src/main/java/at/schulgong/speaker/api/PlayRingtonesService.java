package at.schulgong.speaker.api;

import at.schulgong.Ringtime;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.repository.RingtimeRepository;
import at.schulgong.util.DtoConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayRingtonesService {

    @Autowired private RingtimeRepository ringTimeRepository;

    public List<RingtimeDTO> testMethod() {
        List<RingtimeDTO> ringtimeDTOList = new ArrayList<>();
        List<Ringtime> ringtimesList = this.ringTimeRepository.findAll();
        for (Ringtime ringtime : ringtimesList) {
            RingtimeDTO ringtimeDTO = DtoConverter.convertRingtimeToDTO(ringtime, true);
            ringtimeDTOList.add(ringtimeDTO);
        }
        return ringtimeDTOList;
    }

    //  SELECT * FROM schulgong.ringtime r WHERE CURDATE() between r.startdate and r.enddate
    //  and IF(WEEKDAY(CURDATE()) = 0, r.monday = 1, IF(WEEKDAY(CURDATE()) = 1, r.tuesday = 1,
    // IF(WEEKDAY(CURDATE()) = 2, r.wednesday = 1, IF(WEEKDAY(CURDATE()) = 3, r.thursday = 1,
    // IF(WEEKDAY(CURDATE()) = 4, r.friday = 1, IF(WEEKDAY(CURDATE()) = 0, r.saturday = 1, r.sunday
    // = 1))))));

}
