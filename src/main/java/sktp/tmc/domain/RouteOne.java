package sktp.tmc.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RouteOne.
 */
@Entity
@Table(name = "route_one")
public class RouteOne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "abbr")
    private String abbr;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbr() {
        return abbr;
    }

    public RouteOne abbr(String abbr) {
        this.abbr = abbr;
        return this;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public RouteOne name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        RouteOne routeOne = (RouteOne) o;
        if (routeOne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), routeOne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RouteOne{" +
            "id=" + getId() +
            ", abbr='" + getAbbr() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
