package com.searching.instagram.config.jwt;

import com.searching.instagram.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {
    private Long id;
    private String username;
    private Role role;
}
