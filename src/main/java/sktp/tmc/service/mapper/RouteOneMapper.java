package sktp.tmc.service.mapper;

import sktp.tmc.domain.*;
import sktp.tmc.service.dto.RouteOneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RouteOne and its DTO RouteOneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RouteOneMapper extends EntityMapper<RouteOneDTO, RouteOne> {



    default RouteOne fromId(Long id) {
        if (id == null) {
            return null;
        }
        RouteOne routeOne = new RouteOne();
        routeOne.setId(id);
        return routeOne;
    }
}
