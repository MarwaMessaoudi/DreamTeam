// src/main/java/team/project/redboost/controllers/FolderMetadataController.java
package team.project.redboost.controllers;

import team.project.redboost.entities.Category;
import team.project.redboost.entities.FolderMetadata;
import team.project.redboost.services.CategoryService;
import team.project.redboost.services.FolderMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/folders")
public class FolderMetadataController {

    private final FolderMetadataService folderMetadataService;
    private final CategoryService categoryService;

    @Autowired
    public FolderMetadataController(FolderMetadataService folderMetadataService,CategoryService categoryService) {
        this.folderMetadataService = folderMetadataService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<FolderMetadata> createFolderMetadata(@RequestBody FolderMetadata folderMetadata ,@RequestParam(required = false) Long categoryId) {

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            folderMetadata.setCategory(category);
        }
        FolderMetadata createdFolderMetadata = folderMetadataService.createFolderMetadata(folderMetadata);
        return new ResponseEntity<>(createdFolderMetadata, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderMetadata> getFolderMetadataById(@PathVariable Long id) {
        try {
            FolderMetadata folderMetadata = folderMetadataService.getFolderMetadataById(id);
            return new ResponseEntity<>(folderMetadata, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/path")
    public ResponseEntity<FolderMetadata> getFolderMetadataByPath(@RequestParam String folderPath) {
        try {
            FolderMetadata folderMetadata = folderMetadataService.getFolderMetadataByPath(folderPath);
            return new ResponseEntity<>(folderMetadata, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<FolderMetadata>> getAllFolderMetadata() {
        List<FolderMetadata> folderMetadataList = folderMetadataService.getAllFolderMetadata();
        return new ResponseEntity<>(folderMetadataList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FolderMetadata> updateFolderMetadata(@PathVariable Long id, @RequestBody FolderMetadata folderMetadata,@RequestParam(required = false) Long categoryId) {
        try {
            if (categoryId != null) {
                Category category = categoryService.getCategoryById(categoryId);
                folderMetadata.setCategory(category);
            }
            FolderMetadata updatedFolderMetadata = folderMetadataService.updateFolderMetadata(id, folderMetadata);
            return new ResponseEntity<>(updatedFolderMetadata, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolderMetadata(@PathVariable Long id) {
        try {
            folderMetadataService.deleteFolderMetadata(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}