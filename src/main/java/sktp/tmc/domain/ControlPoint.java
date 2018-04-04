package sktp.tmc.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ControlPoint.
 */
@Entity
@Table(name = "control_point")
public class ControlPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ordinal")
    private String ordinal;

    @ManyToOne
    private Marker marker;

    @ManyToOne
    private RouteOne route;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdinal() {
        return ordinal;
    }

    public ControlPoint ordinal(String ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public Marker getMarker() {
        return marker;
    }

    public ControlPoint marker(Marker marker) {
        this.marker = marker;
        return this;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public RouteOne getRoute() {
        return route;
    }

    public ControlPoint route(RouteOne routeOne) {
        this.route = routeOne;
        return this;
    }

    public void setRoute(RouteOne routeOne) {
        this.route = routeOne;
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
        ControlPoint controlPoint = (ControlPoint) o;
        if (controlPoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), controlPoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ControlPoint{" +
            "id=" + getId() +
            ", ordinal='" + getOrdinal() + "'" +
            "}";
    }
}
