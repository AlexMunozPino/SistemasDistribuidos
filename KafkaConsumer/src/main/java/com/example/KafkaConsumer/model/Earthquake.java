package com.example.KafkaConsumer.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Earthquake {

    @Id
    private String id;

    @Column
    private Float longitude;

    @Column
    private Float latitude;

    @Column
    private Float magnitude;

    @Column
    private Float depth;

    @Column
    private Long timestamp;

    @Column
    private String place;

    // GETTERS

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getMagnitude() {
        return magnitude;
    }

    public Float getDepth() {
        return depth;
    }

    // SETTERS

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setMagnitude(Float magnitude) {
        this.magnitude = magnitude;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    // CONSTRUCTORS

    public Earthquake(){
    }

    public Earthquake(Float longitude, Float latitude, Float magnitude, Float depth){
        this.longitude = longitude;
        this.latitude = latitude;
        this.magnitude = magnitude;
        this.depth = depth;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Earthquake{");
        sb.append("longitude='").append(longitude).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append(", magnitude='").append(magnitude).append('\'');
        sb.append(", depth='").append(depth).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

