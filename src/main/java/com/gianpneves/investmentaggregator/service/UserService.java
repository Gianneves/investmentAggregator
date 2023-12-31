package com.gianpneves.investmentaggregator.service;

import com.gianpneves.investmentaggregator.controller.CreateUserDTO;
import com.gianpneves.investmentaggregator.controller.UpdateUserDTO;
import com.gianpneves.investmentaggregator.entity.User;
import com.gianpneves.investmentaggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UUID createUser(CreateUserDTO createUserDTO) {

        var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password()
        );
        var userSaved = repository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return repository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDTO userDTO) {
        var id = UUID.fromString(userId);
        var userEntity = repository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (userDTO.username() != null) {
                user.setUsername(userDTO.username());
            }

            if (userDTO.password() != null) {
                user.setUsername(userDTO.password());
            }
            repository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = repository.existsById(id);

        if (userExists) {
            repository.deleteById(id);
        }
    }
}
