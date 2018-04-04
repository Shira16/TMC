package sktp.tmc.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Marker entity.
 */
public class MarkerDTO implements Serializable {

    private Long id;

    private Double pointX;

    private Double pointY;

    private String markerCode;

    private String beaconCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPointX() {
        return pointX;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public Double getPointY() {
        return pointY;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

    public String getMarkerCode() {
        return markerCode;
    }

    public void setMarkerCode(String markerCode) {
        this.markerCode = markerCode;
    }

    public String getBeaconCode() {
        return beaconCode;
    }

    public void setBeaconCode(String beaconCode) {
        this.beaconCode = beaconCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarkerDTO markerDTO = (MarkerDTO) o;
        if(markerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), markerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarkerDTO{" +
            "id=" + getId() +
            ", pointX=" + getPointX() +
            ", pointY=" + getPointY() +
            ", markerCode='" + getMarkerCode() + "'" +
            ", beaconCode='" + getBeaconCode() + "'" +
            "}";
    }
}
