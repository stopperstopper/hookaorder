package ru.hookaorder.backend.utils;

import org.springframework.security.core.Authentication;
import ru.hookaorder.backend.feature.place.entity.PlaceEntity;
import ru.hookaorder.backend.feature.roles.entity.ERole;

public class CheckOwnerAndRolesAccess {
    public static Boolean isOwnerOrAdmin(PlaceEntity place, Authentication authentication) {
        return place.getOwner().getId().equals(authentication.getPrincipal()) || authentication.getAuthorities().contains(ERole.ADMIN);
    }

}
