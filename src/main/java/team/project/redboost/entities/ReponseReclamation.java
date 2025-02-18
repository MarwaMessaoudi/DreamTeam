// src/main/java/team/project/redboost/entities/ReponseReclamation.java
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



    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @ManyToOne
    private Reclamation reclamation;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReponse;
}
