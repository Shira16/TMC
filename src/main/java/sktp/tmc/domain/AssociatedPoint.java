package sktp.tmc.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AssociatedPoint.
 */
@Entity
@Table(name = "associated_point")
public class AssociatedPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private ControlPoint associated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ControlPoint getAssociated() {
        return associated;
    }

    public AssociatedPoint associated(ControlPoint controlPoint) {
        this.associated = controlPoint;
        return this;
    }

    public void setAssociated(ControlPoint controlPoint) {
        this.associated = controlPoint;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssociatedPoint associatedPoint = (AssociatedPoint) o;
        if (associatedPoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), associatedPoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssociatedPoint{" +
            "id=" + getId() +
            "}";
    }
}
