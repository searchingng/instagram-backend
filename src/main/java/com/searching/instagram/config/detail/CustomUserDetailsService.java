package com.searching.instagram.config.detail;

import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProfileEntity profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Auth Exception"));

        return new CustomUserDetails(profile);
    }
}
