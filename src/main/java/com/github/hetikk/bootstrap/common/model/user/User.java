package com.github.hetikk.bootstrap.common.model.user;

import com.github.hetikk.bootstrap.common.model.Dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class User implements Dto {

    public Long id;

    public String name;

    public String phone;

    public String email;

    public String password;

    public Set<RoleName> roles;

    public LocalDateTime createdAt;

}
