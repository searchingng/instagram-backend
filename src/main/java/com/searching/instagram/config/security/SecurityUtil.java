package com.searching.instagram.config.security;

import com.searching.instagram.config.detail.CustomUserDetails;
import com.searching.instagram.entity.ProfileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static ProfileEntity getCurrentUser(){
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getProfile();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

}
