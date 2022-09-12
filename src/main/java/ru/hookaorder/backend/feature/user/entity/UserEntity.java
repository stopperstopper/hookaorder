package ru.hookaorder.backend.feature.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.hookaorder.backend.feature.BaseEntity;
import ru.hookaorder.backend.feature.roles.entity.RoleEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Pattern(regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+")
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$")
    private String phone;

    @NotBlank
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "is_enabled")
    @JsonProperty(value = "is_enabled", access = JsonProperty.Access.READ_ONLY)
    private boolean isEnabled = true;

    @ManyToMany
    @JoinColumn
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<RoleEntity> rolesSet = Collections.emptySet();

}
