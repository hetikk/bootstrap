package com.github.hetikk.bootstrap.repository.user;

import com.github.hetikk.bootstrap.repository.AutoIncrementedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends AutoIncrementedEntity {

    private String name;

    private String phone;

    private String email;

    private String password;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = RoleEntity.Fields.user, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RoleEntity> roles;

    public static UserEntity newWithDefaultParams() {
        UserEntity entity = new UserEntity();
        entity.roles = new HashSet<>();
        return entity;
    }

}
