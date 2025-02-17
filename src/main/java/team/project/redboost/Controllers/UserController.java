package team.project.redboost.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import team.project.redboost.Models.user;
import team.project.redboost.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 Récupérer tous les utilisateurs
    @GetMapping("ListUser")
    public ResponseEntity<List<user>> getAllusers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔹 Récupérer un utilisateur par ID
    @GetMapping("getByID/{id}")
    public ResponseEntity<user> getuserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 🔹 Créer un nouvel utilisateur
    @PostMapping("create")
    public ResponseEntity<user> createuser(@RequestBody user user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // 🔹 Supprimer un utilisateur
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Void> deleteuser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}

