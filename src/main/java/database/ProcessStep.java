package database;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

/**
 * Klasse für das Persistierung der (fachlichen) Prozessschritte
 */
@Entity
@Table(name = "ProcessStep")
public class ProcessStep extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference") 
    private String reference;

    @Column(name = "step_id") 
    private String stepId;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    @Transactional
    public static ProcessStep createEntry(String reference, String stepId)
    {
        ProcessStep step = new ProcessStep();

        step.setReference(reference);
        step.setStepId(stepId);
        step.persist();

        return step;
    }

    
}
