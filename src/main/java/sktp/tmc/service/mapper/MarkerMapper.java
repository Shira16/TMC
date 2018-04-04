package sktp.tmc.service.mapper;

import sktp.tmc.domain.*;
import sktp.tmc.service.dto.MarkerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Marker and its DTO MarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarkerMapper extends EntityMapper<MarkerDTO, Marker> {



    default Marker fromId(Long id) {
        if (id == null) {
            return null;
        }
        Marker marker = new Marker();
        marker.setId(id);
        return marker;
    }
}
