package sktp.tmc.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Marker.
 */
@Entity
@Table(name = "marker")
public class Marker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "point_x")
    private Double pointX;

    @Column(name = "point_y")
    private Double pointY;

    @Column(name = "marker_code")
    private String markerCode;

    @Column(name = "beacon_code")
    private String beaconCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPointX() {
        return pointX;
    }

    public Marker pointX(Double pointX) {
        this.pointX = pointX;
        return this;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public Double getPointY() {
        return pointY;
    }

    public Marker pointY(Double pointY) {
        this.pointY = pointY;
        return this;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

    public String getMarkerCode() {
        return markerCode;
    }

    public Marker markerCode(String markerCode) {
        this.markerCode = markerCode;
        return this;
    }

    public void setMarkerCode(String markerCode) {
        this.markerCode = markerCode;
    }

    public String getBeaconCode() {
        return beaconCode;
    }

    public Marker beaconCode(String beaconCode) {
        this.beaconCode = beaconCode;
        return this;
    }

    public void setBeaconCode(String beaconCode) {
        this.beaconCode = beaconCode;
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
        Marker marker = (Marker) o;
        if (marker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Marker{" +
            "id=" + getId() +
            ", pointX=" + getPointX() +
            ", pointY=" + getPointY() +
            ", markerCode='" + getMarkerCode() + "'" +
            ", beaconCode='" + getBeaconCode() + "'" +
            "}";
    }
}
