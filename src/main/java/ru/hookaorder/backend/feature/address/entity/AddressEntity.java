package ru.hookaorder.backend.feature.address.entity;

import lombok.Getter;
import lombok.Setter;
import ru.hookaorder.backend.feature.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Getter
@Setter
public class AddressEntity extends BaseEntity {
    @Column(name = "country")
    private String country;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "building", nullable = false)
    private String building;
    @Column(name = "apartment")
    private String apartment;
    @Column(name = "lat")
    private double lat;
    @Column(name = "lng")
    private double lng;
}
