package ru.hookaorder.backend.feature.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import ru.hookaorder.backend.feature.place.entity.PlaceEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Data
@SQLDelete(sql = "UPDATE places set deleted_at = now()::timestamp where id=?")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
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

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * Ignore delete on return entity
     *
     * @return LocalDateTime
     */
    @JsonIgnore
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
