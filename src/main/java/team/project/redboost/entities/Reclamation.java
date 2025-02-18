package team.project.redboost.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReclamation;

    // L'ID utilisateur est directement un Long, pas une entité
    @NotNull(message = "L'id utilisateur est obligatoire")
    private Long idUtilisateur;  // Id utilisateur statique

    @NotNull(message = "Le titre est obligatoire")  // Titres requis
    private String titre;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "La date est obligatoire")  // Date requise
    private Date date;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut est obligatoire")  // Statut requis
    private StatutReclamation statut;

    @Column(length = 1000)
    @NotNull(message = "La description est obligatoire")  // Description requise
    private String description;

    @Lob
    private byte[] fichierReclamation;  // Non-required, peut être nul

}
