package ru.hookaorder.backend.feature.place.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Place entity
 */
@Entity
@Table(name = "places")
@SQLDelete(sql = "UPDATE places set deleted_at = now()::timestamp where id=?")
@Where(clause = "deleted_at IS NULL")
@Data
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Name place.
     * Can't be nullable
     */
    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * Ignore delete on return entity
     * @return  LocalDateTime
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
