package com.sartop.demoproductsapi.jwt;

import com.sartop.demoproductsapi.entity.UserEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User
{

    public JwtUserDetails(UserEntity user)
    {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserRole().name()));
        this.User = user;
    }

    public Long getId()
    {
        return this.User.getId();
    }

    public String getRole()
    {
        return this.User.getUserRole().name();
    }

    private UserEntity User;
}
