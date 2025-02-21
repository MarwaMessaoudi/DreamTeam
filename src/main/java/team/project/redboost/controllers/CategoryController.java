package team.project.redboost.controllers;

import team.project.redboost.entities.Category; // Importe la classe Category (entité JPA).
import team.project.redboost.services.CategoryService; // Importe l'interface CategoryService (logique métier).
import org.springframework.beans.factory.annotation.Autowired; // Importe l'annotation Autowired pour l'injection de dépendances.
import org.springframework.http.HttpStatus; // Importe la classe HttpStatus pour définir les codes d'état HTTP.
import org.springframework.http.ResponseEntity; // Importe la classe ResponseEntity pour construire les réponses HTTP.
import org.springframework.web.bind.annotation.*; // Importe les annotations pour définir les endpoints REST.

import java.util.List; // Importe la classe List pour gérer les collections de catégories.
import java.util.NoSuchElementException; // Importe la classe NoSuchElementException pour gérer les cas où une catégorie n'est pas trouvée.

@RestController // Indique que cette classe est un contrôleur REST.
@RequestMapping("/api/categories") // Définit le chemin de base pour tous les endpoints de ce contrôleur.
public class CategoryController {

    private final CategoryService categoryService; // Déclare une dépendance vers CategoryService.

    @Autowired // Injecte automatiquement une instance de CategoryService dans ce contrôleur.
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping // Définit un endpoint pour la création d'une catégorie (POST).
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category); // Appelle le service pour créer la catégorie.
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED); // Retourne une réponse avec la catégorie créée et un code d'état 201 Created.
    }

    @GetMapping("/{id}") // Définit un endpoint pour récupérer une catégorie par son ID (GET).
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id); // Appelle le service pour récupérer la catégorie par son ID.
            return new ResponseEntity<>(category, HttpStatus.OK); // Retourne une réponse avec la catégorie et un code d'état 200 OK.
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne une réponse avec un code d'état 404 Not Found si la catégorie n'est pas trouvée.
        }
    }

    @GetMapping // Définit un endpoint pour récupérer toutes les catégories (GET).
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories(); // Appelle le service pour récupérer toutes les catégories.
        return new ResponseEntity<>(categoryList, HttpStatus.OK); // Retourne une réponse avec la liste des catégories et un code d'état 200 OK.
    }

    @PutMapping("/{id}") // Définit un endpoint pour mettre à jour une catégorie (PUT).
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category); // Appelle le service pour mettre à jour la catégorie.
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK); // Retourne une réponse avec la catégorie mise à jour et un code d'état 200 OK.
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne une réponse avec un code d'état 404 Not Found si la catégorie n'est pas trouvée.
        }
    }

    @DeleteMapping("/{id}") // Définit un endpoint pour supprimer une catégorie (DELETE).
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id); // Appelle le service pour supprimer la catégorie.
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retourne une réponse avec un code d'état 204 No Content (indiquant une suppression réussie).
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne une réponse avec un code d'état 404 Not Found si la catégorie n'est pas trouvée.
        }
    }
}