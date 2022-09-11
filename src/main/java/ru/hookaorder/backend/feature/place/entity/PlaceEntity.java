package ru.hookaorder.backend.feature.place.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import ru.hookaorder.backend.feature.BaseEntity;
import ru.hookaorder.backend.feature.address.entity.AddressEntity;
import ru.hookaorder.backend.feature.user.entity.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Place entity
 */
@Entity
@Table(name = "places")
@Getter
@Setter
public class PlaceEntity extends BaseEntity {

    /**
     * Name place.
     * Can't be nullable
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Time when place open
     */
    @Column(name = "start_time")
    @JsonProperty(value = "start_time")
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])$")
    private String startTime;

    /**
     * Time when place close
     */
    @Column(name = "end_time")
    @JsonProperty(value = "end_time")
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])$")
    private String endTime;

    /**
     * Url place logo
     */
    @Column(name = "logo_url")
    @JsonProperty(value = "logo_url")
    @URL(regexp = "^(http|https).*")
    private String logoUrl;

    @ManyToOne
    @JoinColumn
    @JsonProperty(value = "owner")
    private UserEntity owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;
}
