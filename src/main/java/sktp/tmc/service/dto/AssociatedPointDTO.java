package sktp.tmc.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AssociatedPoint entity.
 */
public class AssociatedPointDTO implements Serializable {

    private Long id;

    private Long associatedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Long controlPointId) {
        this.associatedId = controlPointId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssociatedPointDTO associatedPointDTO = (AssociatedPointDTO) o;
        if(associatedPointDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), associatedPointDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssociatedPointDTO{" +
            "id=" + getId() +
            "}";
    }
}
