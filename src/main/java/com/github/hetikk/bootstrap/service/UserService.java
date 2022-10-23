package com.github.hetikk.bootstrap.service;

import com.github.hetikk.bootstrap.common.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Transactional
    public User create(User user) {
        validate(user);
        user.createdAt = LocalDateTime.now();
        return userRepository.create(user);
    }

    @Transactional
    public User update(User updates) {
        validate(updates);
        User currentState = userRepository.getOne(updates.id);
        copeEditableFields(currentState, updates);
        return userRepository.update(currentState);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    private void validate(User user) {

    }

    private void copeEditableFields(User currentState, User newState) {
        currentState.name = newState.name;
        currentState.phone = newState.phone;
        currentState.roles = newState.roles;
        currentState.password = newState.password;
    }

}
