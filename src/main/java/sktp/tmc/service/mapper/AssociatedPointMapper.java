package sktp.tmc.service.mapper;

import sktp.tmc.domain.*;
import sktp.tmc.service.dto.AssociatedPointDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AssociatedPoint and its DTO AssociatedPointDTO.
 */
@Mapper(componentModel = "spring", uses = {ControlPointMapper.class})
public interface AssociatedPointMapper extends EntityMapper<AssociatedPointDTO, AssociatedPoint> {

    @Mapping(source = "associated.id", target = "associatedId")
    AssociatedPointDTO toDto(AssociatedPoint associatedPoint);

    @Mapping(source = "associatedId", target = "associated")
    AssociatedPoint toEntity(AssociatedPointDTO associatedPointDTO);

    default AssociatedPoint fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssociatedPoint associatedPoint = new AssociatedPoint();
        associatedPoint.setId(id);
        return associatedPoint;
    }
}
