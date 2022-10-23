package com.github.hetikk.bootstrap.repository.user;

import com.github.hetikk.bootstrap.common.model.user.User;
import com.github.hetikk.bootstrap.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public User getOne(Long id) {
        return userDao.findById(id)
                .map(userMapper::toSchema)
                .orElseThrow(() -> new EntityNotFoundException("User not found. ID=" + id));
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll().stream()
                .map(userMapper::toSchema)
                .collect(Collectors.toList());
    }

    @Override
    public User create(User user) {
        UserEntity entity = UserEntity.newWithDefaultParams();
        userMapper.enrichFromSchema(user, entity);
//        UserEntity entity = userMapper.toEntity(user);
        UserEntity created = userDao.save(entity);
        return userMapper.toSchema(created);
    }

    @Override
    public User update(User newState) {
        UserEntity currentState = userDao.getOne(newState.id);
        userMapper.enrichFromSchema(newState, currentState);
        UserEntity created = userDao.save(currentState);
        return userMapper.toSchema(created);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }

}
