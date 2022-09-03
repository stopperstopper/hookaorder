package ru.hookaorder.backend.feature.roles.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "roles")
@Entity
@RequiredArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @JsonProperty("role_name")
    private String roleName;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    /**
     * Ignore delete on return entity
     *
     * @return LocalDateTime
     */
    @JsonIgnore
    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

}
