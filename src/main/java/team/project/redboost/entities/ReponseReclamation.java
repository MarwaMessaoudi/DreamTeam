package team.project.redboost.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReponseReclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReponse;

    @ManyToOne
    @JoinColumn(name = "idReclamation", nullable = false)
    private Reclamation reclamation;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReponse;

    // Getter pour renvoyer uniquement l'idReclamation
    public Long getIdReclamation() {
        return this.reclamation != null ? this.reclamation.getIdReclamation() : null;
    }
}
