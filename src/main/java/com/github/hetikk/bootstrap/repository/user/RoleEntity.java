package com.github.hetikk.bootstrap.repository.user;

import com.github.hetikk.bootstrap.common.model.user.RoleName;
import com.github.hetikk.bootstrap.repository.AutoIncrementedEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class RoleEntity extends AutoIncrementedEntity {

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static RoleEntity newWithDefaultParams() {
        RoleEntity entity = new RoleEntity();
        return entity;
    }

}
