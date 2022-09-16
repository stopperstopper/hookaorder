package ru.hookaorder.backend.feature.rating.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.hookaorder.backend.feature.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static ru.hookaorder.backend.config.Vars.MAX_RATING_VALUE;
import static ru.hookaorder.backend.config.Vars.MIN_RATING_VALUE;

@Entity
@Table(name = "ratings")
@Data
public class RatingEntity extends BaseEntity {

    @Column(name = "rating_value")
    @JsonProperty(value = "rating_value")
    @Min(MIN_RATING_VALUE)
    @Max(MAX_RATING_VALUE)
    private byte ratingValue;

    @Column(name = "is_moderated")
    @JsonProperty(value = "is_enabled", access = JsonProperty.Access.READ_ONLY)
    private boolean isModerated;
}
