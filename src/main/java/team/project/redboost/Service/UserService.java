package team.project.redboost.Service;

import team.project.redboost.Models.user;
import java.util.List;

public interface UserService {
    List<user> getAllUsers();
    user getUserById(Long id);
    user createUser(user user);
    void deleteUser(Long id);
}

