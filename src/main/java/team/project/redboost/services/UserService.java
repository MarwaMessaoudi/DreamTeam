package team.project.redboost.services;

import team.project.redboost.entities.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    user createUser(user newUser);

    List<user> getAllUsers();

    Optional<user> getUserById(Long id);

    user updateUser(Long id, user updatedUser);

    void deleteUser(Long id);
}
