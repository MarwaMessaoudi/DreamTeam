package team.project.redboost.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/coachlist")
@Tag(name = "Coach List", description = "API pour la liste statique des coachs")
@CrossOrigin(origins = "*")
public class CoachListController {

    private static final List<Map<String, Object>> staticCoaches = new ArrayList<>();

    static {
        Map<String, Object> coach1 = new HashMap<>();
        coach1.put("id", 1);
        coach1.put("firstName", "John");
        coach1.put("lastName", "Doe");
        coach1.put("speciality", "Musculation");
        coach1.put("email", "john.doe@email.com");
        coach1.put("phoneNumber", "+1234567890");
        coach1.put("experience", "5 ans");
        coach1.put("isAvailable", true);
        staticCoaches.add(coach1);

        Map<String, Object> coach2 = new HashMap<>();
        coach2.put("id", 2);
        coach2.put("firstName", "Jane");
        coach2.put("lastName", "Smith");
        coach2.put("speciality", "Yoga");
        coach2.put("email", "jane.smith@email.com");
        coach2.put("phoneNumber", "+0987654321");
        coach2.put("experience", "3 ans");
        coach2.put("isAvailable", true);
        staticCoaches.add(coach2);

        Map<String, Object> coach3 = new HashMap<>();
        coach3.put("id", 3);
        coach3.put("firstName", "Mike");
        coach3.put("lastName", "Johnson");
        coach3.put("speciality", "CrossFit");
        coach3.put("email", "mike.j@email.com");
        coach3.put("phoneNumber", "+1122334455");
        coach3.put("experience", "7 ans");
        coach3.put("isAvailable", true);
        staticCoaches.add(coach3);
    }

    // Endpoint pour récupérer la liste
    @GetMapping
    @Operation(summary = "Récupérer la liste statique des coachs")
    public ResponseEntity<List<Map<String, Object>>> getCoachList() {
        return ResponseEntity.ok(staticCoaches);
    }

    // Endpoint pour récupérer un coach par ID
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un coach par son ID")
    public ResponseEntity<Map<String, Object>> getCoachById(@PathVariable Integer id) {
        Optional<Map<String, Object>> coach = staticCoaches.stream()
                .filter(c -> c.get("id").equals(id))
                .findFirst();

        return coach.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}