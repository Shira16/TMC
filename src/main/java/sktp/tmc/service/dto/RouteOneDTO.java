package sktp.tmc.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RouteOne entity.
 */
public class RouteOneDTO implements Serializable {

    private Long id;

    private String abbr;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RouteOneDTO routeOneDTO = (RouteOneDTO) o;
        if(routeOneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), routeOneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RouteOneDTO{" +
            "id=" + getId() +
            ", abbr='" + getAbbr() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
