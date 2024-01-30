package com.sartop.demoproductsapi.jwt;

import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService
{
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getByUsername(username);

        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username)
    {
        UserEntity.Role role = userService.getRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring(JwtUtils.JWT_BEARER.length()));
    }

}
