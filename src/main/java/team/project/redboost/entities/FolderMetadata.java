// src/main/java/team/project/redboost/entities/FolderMetadata.java
package team.project.redboost.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder_metadata")
public class FolderMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "folder_path")
    private String folderPath;

    // Relation One-to-Many avec FileMetadata
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileMetadata> files = new ArrayList<>();

    // Relation Many-to-One avec FolderMetadata (parent folder)
    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private FolderMetadata parentFolder;

    // Relation One-to-Many avec FolderMetadata (subfolders)
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolderMetadata> subfolders = new ArrayList<>();

    //Relation many to one with Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructeurs, Getters et Setters

    public FolderMetadata() {
    }

    public FolderMetadata(String folderName, String folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public List<FileMetadata> getFiles() {
        return files;
    }

    public void setFiles(List<FileMetadata> files) {
        this.files = files;
    }

    public FolderMetadata getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderMetadata parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<FolderMetadata> getSubfolders() {
        return subfolders;
    }

    public void setSubfolders(List<FolderMetadata> subfolders) {
        this.subfolders = subfolders;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}