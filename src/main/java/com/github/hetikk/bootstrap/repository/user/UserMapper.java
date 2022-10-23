package com.github.hetikk.bootstrap.repository.user;

import com.github.hetikk.bootstrap.common.model.user.User;
import com.github.hetikk.bootstrap.repository.AbstractMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper extends AbstractMapper<User, UserEntity> {

    private final RoleDao roleDao;

    public UserMapper(RoleDao roleDao) {
        super(User.class, UserEntity.class);
        this.roleDao = roleDao;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserEntity.class, User.class)
                .addMappings(m -> m.skip(User::setRoles)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(User.class, UserEntity.class)
                .addMappings(m -> m.skip(UserEntity::setRoles)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserEntity source, User destination) {
        destination.roles = Optional.ofNullable(source.getRoles())
                .orElse(Set.of())
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
    }

    @Override
    protected void mapSpecificFields(User source, UserEntity destination) {
        final Set<RoleEntity> roleEntities = Optional.ofNullable(source.getRoles())
                .orElse(Set.of())
                .stream()
                .map(roleName -> {
                    RoleEntity roleEntity = RoleEntity.newWithDefaultParams();
                    roleEntity.setUser(destination);
                    roleEntity.setName(roleName);
                    return roleEntity;
                })
                .collect(Collectors.toSet());
        destination.setRoles(roleEntities);
    }

}
