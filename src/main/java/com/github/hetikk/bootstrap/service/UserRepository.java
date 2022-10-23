package com.github.hetikk.bootstrap.service;

import com.github.hetikk.bootstrap.common.model.user.User;

import java.util.List;

public interface UserRepository {

    User getOne(Long id);

    List<User> getAll();

    User create(User user);

    User update(User newState);

    void delete(Long id);

}
