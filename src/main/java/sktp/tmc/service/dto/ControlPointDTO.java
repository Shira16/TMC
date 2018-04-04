package sktp.tmc.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ControlPoint entity.
 */
public class ControlPointDTO implements Serializable {

    private Long id;

    private String ordinal;

    private Long markerId;

    private Long routeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public Long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Long markerId) {
        this.markerId = markerId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeOneId) {
        this.routeId = routeOneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ControlPointDTO controlPointDTO = (ControlPointDTO) o;
        if(controlPointDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), controlPointDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ControlPointDTO{" +
            "id=" + getId() +
            ", ordinal='" + getOrdinal() + "'" +
            "}";
    }
}
