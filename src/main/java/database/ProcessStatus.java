package database;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@Entity
@NamedQuery(name = "ProcessStatus.findAll", query = "SELECT f FROM ProcessStatus f ORDER BY f.id", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable
@Table(name = "ProcessStatus")
public class ProcessStatus extends PanacheEntityBase {

    public enum ValidStates {
        NEW("NEW"),
        IN_PROGRESS("IN PROGRESS"),
        POSTPONED("POSTPONED"),
        ABANDONED("ABANDONED"),
        WITHDRAWN("WITHDRAWN"),
        SUCCESSFUL("SUCCESSFUL"),
        FAILED("FAILED"),
        DELETED("DELETED");

        private String literal;

        private ValidStates(String literal) {
            this.literal = literal;
        }

        public String getLiteral() {
            return literal;
        }
    }

    public enum ValidExceptionTypes {
        
        AE("AE"),
        BE("BE"),
        EX("EX");

        private String literal;

        private ValidExceptionTypes(String literal) {
            this.literal = literal;
        }

        public String getLiteral() {
            return literal;
        }
    }


    @Id
    @Column(name = "reference") 
    public String reference;

    @Column(name = "reference_date") 
    public LocalDate referenceDate;

    @Column(name = "status") 
    public String status;

    @Column(name = "current_task") 
    public String currentTask;

    @Column(name = "last_successful_task")
    public String lastSuccessfulTask;
    
    @Column(name = "exception_type")
    public String exceptionType;

    @Column(name = "exception_reason")
    public String exceptionReason;

    @Column(name = "sub_task")
    public String subTask;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created", columnDefinition = "timestamp with time zone") 
    ZonedDateTime created;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated", columnDefinition = "timestamp with time zone") 
    ZonedDateTime updated;

    @Transactional
    public static ProcessStatus findById(String reference) {
        return find("reference", reference).firstResult();
    }

    public static List<ProcessStatus> findStatusComplete() {
        return list("status", "5");
    }

    public static void deleteIDs(String reference) {
        delete("reference", reference);
    }

    @Transactional
    public static ProcessStatus create(ProcessStatus processStatus) {

        ProcessStatus entity = ProcessStatus.findById(processStatus.reference);

        if (entity != null) {
            throw new EntityExistsException("BusinessKey existiert bereits");
        }
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
        processStatus.created = ZonedDateTime.now(berlinZone);
        processStatus.updated = processStatus.created;
        processStatus.persist();
        return processStatus;

    }

    @Transactional
    public static ProcessStatus update(String reference, ProcessStatus processStatus) {
        ProcessStatus entity = ProcessStatus.findById(reference);        
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
       
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.status = processStatus.status;
        entity.referenceDate = processStatus.referenceDate;
        entity.currentTask = processStatus.currentTask;
        entity.lastSuccessfulTask = processStatus.lastSuccessfulTask;
        entity.exceptionType = processStatus.exceptionType;
        entity.exceptionReason = processStatus.exceptionReason;
        entity.subTask = processStatus.subTask;
        entity.updated = ZonedDateTime.now(berlinZone);;
        ProcessStatus.ValidProcessstatusCheck(entity); 
        return entity;
    }


    public static void ValidProcessstatusCheck  (ProcessStatus processStatus) { 

        //check ob Status gefüllt und einen korrekten Wert enthält
        if (!ProcessStatus.ValidStatusCheck(processStatus.status)) {
            throw new RuntimeException("Dieser Status ist nicht erlaubt: " + processStatus.status);
        }

        if (processStatus.exceptionType != null) {
            if (!ProcessStatus.ValidExceptionType(processStatus.exceptionType)) {
                throw new RuntimeException("Dieser Exceptiontype ist nicht erlaubt. Erlaubt sind u.a. BE, AE oder EX");
            } else {
                if ((processStatus.exceptionReason == null || (processStatus.exceptionReason.isBlank()) || processStatus.exceptionReason.isEmpty())) {
                    throw new RuntimeException("Wenn eine Exception gespeichert werden soll, muss auch ein Detailmeldung im Feld exceptionReason erfolgen");
                }
            }
        }
    }

    public static boolean ValidStatusCheck(String status) {
        for (ValidStates validState : ValidStates.values()) {
            if(validState.getLiteral().equals(status)) 
                return true;
            }
        return false;
    }


    public static boolean ValidExceptionType(String exceptionType) {
        for (ValidExceptionTypes validExceptionType : ValidExceptionTypes.values())  {
            if (validExceptionType.getLiteral().equals(exceptionType))
                return true;
            }
        return false;
    }

    public static boolean isNullOrEmptyOrBlank(String str) {
        return str == null || StringUtils.isBlank(str); 
    }

    public static ProcessStatus mapFromProcessStatus(ProcessStatus source) {
        ProcessStatus target = new ProcessStatus();

        for (Field field : ProcessStatus.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                Logger.getLogger(ProcessStatus.class.getName()).log(Level.WARNING, 
                        "Failed to access field: " + field.getName(), e);
                        throw new RuntimeException("ProcessStatus Mapping fehlerhaft");
            }
        }

        return target;
    }

    public String getReference() {
        return reference;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentTask() {
        return currentTask;
    }

    public String getLastSuccessfulTask() {
        return lastSuccessfulTask;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public String getSubTask() {
        return subTask;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentTask(String currentTask) {
        this.currentTask = currentTask;
    }

    public void setLastSuccessfulTask(String lastSuccessfulTask) {
        this.lastSuccessfulTask = lastSuccessfulTask;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    public void setSubTask(String subTask) {
        this.subTask = subTask;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public boolean isSuccessful() {
        return this.status != null && this.status.equals(ValidStates.SUCCESSFUL.getLiteral());
    }

    public boolean isFailed() {
        return this.status != null && this.status.equals(ValidStates.FAILED.getLiteral());
    }

    public boolean isFailedWithAE() {
        return this.isFailed() && this.hasExceptionAE();
    }

    public boolean isFailedWithBE() {
        return this.isFailed() && this.hasExceptionBE();
    }

    public boolean hasExceptionBE() {
        return this.exceptionType != null && this.exceptionType.equals(ValidExceptionTypes.BE.getLiteral());
    }

    public boolean hasExceptionAE() {
        return this.exceptionType != null && this.exceptionType.equals(ValidExceptionTypes.AE.getLiteral());
    }

    public void setExceptionBE(String reason) {
        this.setExceptionType(ValidExceptionTypes.BE.getLiteral());
        this.setExceptionReason(reason);
    }

    public void setExceptionAE(String reason) {
        this.setExceptionType(ValidExceptionTypes.AE.getLiteral());
        this.setExceptionReason(reason);
    }

    @Override
    public String toString() {
        return "{" +
            " reference='" + getReference() + "'" +
            ", referenceDate='" + getReferenceDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", currentTask='" + getCurrentTask() + "'" +
            ", lastSuccessfulTask='" + getLastSuccessfulTask() + "'" +
            ", exceptionType='" + getExceptionType() + "'" +
            ", exceptionReason='" + getExceptionReason() + "'" +
            ", subTask='" + getSubTask() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }


}
