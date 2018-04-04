package sktp.tmc.service.mapper;

import sktp.tmc.domain.*;
import sktp.tmc.service.dto.ControlPointDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ControlPoint and its DTO ControlPointDTO.
 */
@Mapper(componentModel = "spring", uses = {MarkerMapper.class, RouteOneMapper.class})
public interface ControlPointMapper extends EntityMapper<ControlPointDTO, ControlPoint> {

    @Mapping(source = "marker.id", target = "markerId")
    @Mapping(source = "route.id", target = "routeId")
    ControlPointDTO toDto(ControlPoint controlPoint);

    @Mapping(source = "markerId", target = "marker")
    @Mapping(source = "routeId", target = "route")
    ControlPoint toEntity(ControlPointDTO controlPointDTO);

    default ControlPoint fromId(Long id) {
        if (id == null) {
            return null;
        }
        ControlPoint controlPoint = new ControlPoint();
        controlPoint.setId(id);
        return controlPoint;
    }
}
