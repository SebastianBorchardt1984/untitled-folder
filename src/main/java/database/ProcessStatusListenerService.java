package database;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.kie.api.event.process.ProcessNodeEvent;
import org.mvel2.MVEL;
import java.lang.String;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.EntityExistsException;

public class ProcessStatusListenerService {

    public static void updateProcessStatus(ProcessNodeEvent event, Map<String, Object> metadata) {
        
        
        String businessKey = searchBusinessKey(event, metadata);
        if (searchBusinessKey(event, metadata) == null) {
            throw new MissingReferenceException("Kein Businesskey/Reference vorhanden");
        } 

        ProcessStatus entity = ProcessStatus.findById(businessKey);
        if (entity == null) {
            throw new MissingReferenceException("Kein Datenbankeintrag zum Businesskey/reference gefunden");
        }

        // In Absprache mit Basti sind "status" und "currentTask" immer zusammen anzugeben aber nicht mehr Pflicht
        if(metadata.containsKey("status") || metadata.containsKey("currentTask")) {

	        if (metadata.containsKey("status")) {
	            String status = metadata.get("status").toString();
	            if(status.contains("#{"))
	                throw new InvalidMetadataException("Status muss als Literal übergeben werden! MVEL #{} sind vorerst nicht erwünscht ! Node " + event.getNodeInstance().getNodeName());
	            if(!ProcessStatus.ValidStatusCheck(metadata.get("status").toString().toUpperCase()))
	                throw new InvalidMetadataException("Status hat einen ungültigen Wert " + status + " ! Node " + event.getNodeInstance().getNodeName());
	            entity.status = status;
	        }
            else throw new InvalidMetadataException("Status muss als Attribut übergeben werden ! Node " + event.getNodeInstance().getNodeName());

            if (metadata.containsKey("currentTask")) {
                String currentTask = metadata.get("currentTask").toString();
                currentTask = evaluateExpressionValue(currentTask, event);
                if(currentTask != null && !currentTask.isBlank()) {
                    entity.currentTask = currentTask;    
        		}
                else throw new InvalidMetadataException("currentTask ist ungültig (null, leer oder nur Leerzeichen), Node " + event.getNodeInstance().getNodeName());
        	}
            else throw new InvalidMetadataException("currentTask muss als Attribut übergeben werden ! Node " + event.getNodeInstance().getNodeName());
        }

        if (metadata.containsKey("lastSuccessfulTask")) {
            entity.lastSuccessfulTask = metadata.get("lastSuccessfulTask").toString();
        }
        
        // Attribute für die Exception-Informationen
        //
        if (metadata.containsKey("clearExceptionInfo")) {
            entity.exceptionType = null;
            entity.exceptionReason = null;
        }
        else{
            if (metadata.containsKey("exceptionType")) {
                String exceptionType = metadata.get("exceptionType").toString();
                exceptionType = evaluateExpressionValue(exceptionType, event);
                if(exceptionType != null && !exceptionType.isBlank()) 
                    ProcessStatus.ValidExceptionType(exceptionType);
                else
                    exceptionType = null;
                    entity.exceptionType = exceptionType;
                }
            
            if (metadata.containsKey("exceptionReason")) {
                String exceptionReason = metadata.get("exceptionReason").toString();
                exceptionReason = evaluateExpressionValue(exceptionReason, event);
                entity.exceptionReason = exceptionReason;
                }
        }



        //Plausicheck muss noch erfolgen auf Exceptiontype und Exception Reason 

        if (metadata.containsKey("subTask")) {
            String subTask = metadata.get("subTask").toString();
            if (subTask == null || subTask.isBlank()) {
                entity.subTask = "";
            } else {            
               entity.subTask = subTask;
            }
        }       
        
        if (metadata.containsKey("referenceDate")) {
            String referenceDate = metadata.get("referenceDate").toString();
            if (referenceDate == null || referenceDate.isBlank()) {
                throw new InvalidMetadataException("referenceDate ist ungültig (null, leer oder nur Leerzeichen)");
            } else if (metadata.get("referenceDate").toString().startsWith("#{")){            
                String expression = metadata.get("referenceDate").toString();
                entity.referenceDate = LocalDate.parse(exececuteSimpleMVELexpression(expression, event).toString());
        
            } else {
                entity.referenceDate = LocalDate.parse(referenceDate);
            }
        }

        /* Persistierung eines abgeschlossenen fachlichen Schrittes falls gewünscht
        */
        if (metadata.containsKey("stepId")) {
            String stepId = metadata.get("stepId").toString();
            stepId = evaluateExpressionValue(stepId, event);
            ProcessStep.createEntry(businessKey, stepId);
        }

            //Entity updaten und persitieren
            ZoneId berlinZone = ZoneId.of("Europe/Berlin");
            entity.updated = ZonedDateTime.now(berlinZone);

            //Last Check
            ProcessStatus.ValidProcessstatusCheck(entity);
            //Persist
            ProcessStatus.update(businessKey, entity);
            //Fallstatus in Variable zurückschreiben
        ProcessStatusListenerService.currentProcessStatusToVariable((ProcessNodeEvent) event, metadata);

        /*
            if (metadata.containsKey("setState")) { // klappt nicht/ kann weg?
                String KogitoProcessInstanceState = metadata.get("setState").toString();
                if (KogitoProcessInstanceState == null || KogitoProcessInstanceState.isBlank()) {
                    throw new InvalidMetadataException("KogitoProcessInstanceState ist ungültig (null, leer oder nur Leerzeichen)");
                } else {            
                    RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getNodeInstance().getProcessInstance();
                    
                                        //
                    //int STATE_PENDING = 0;
                    //int STATE_ACTIVE = 1;
                    //int STATE_COMPLETED = 2;
                    //int STATE_ABORTED = 3;
                    //int STATE_SUSPENDED = 4;
                    //int STATE_ERROR = 5;
                    //

                    switch (KogitoProcessInstanceState) {
                        case "STATE_PENDING":
                          processInstance.setState(ProcessInstance.STATE_ABORTED);
                          break;
                        case "STATE_ACTIVE":
                          processInstance.setState(ProcessInstance.STATE_ACTIVE);
                          break;                        
                        case "STATE_COMPLETED":
                          processInstance.setState(ProcessInstance.STATE_COMPLETED);
                          break; 
                        case "STATE_ABORTED":
                          processInstance.setState(ProcessInstance.STATE_ABORTED);
                          break; 
                        case "STATE_SUSPENDED":
                          processInstance.setState(ProcessInstance.STATE_SUSPENDED);
                          break; 
                        case "STATE_ERROR":
                          processInstance.setState(ProcessInstance.STATE_ERROR);
                          break; 
                        default: 
                          throw new InvalidMetadataException("KogitoProcessInstanceState ist ungültig, bitte nur erlaubte States eintragen: STATE_PENDING, STATE_ACTIVE, STATE_COMPLETED, STATE_ABORTED, STATE_SUSPENDED, STATE_ERROR )");
                        }
                    
                }   
        }*/

        }
            
    public static String searchBusinessKey(ProcessNodeEvent event, Map<String, Object> metadata) {

        RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getNodeInstance().getProcessInstance();
        
        //CorrelationKey ist technisch der Businesskey den man z.B. über den Webservice setzen kann 
        if (processInstance.getCorrelationKey() == null || (processInstance.getCorrelationKey().isBlank())) {

            //es gibt zwar diese Methode, aber ich weiß nicht ob die jemals befüllt ist. Hab sie aber implementiert vorsichtshalber
            if (processInstance.getBusinessKey() == null || (processInstance.getBusinessKey().isBlank())) {

                //  wenn beides nicht befüllt ist (Correlation oder Businesskey) dann noch überprüfen ob der Businesskey in den Metadaten mit übergeben wurde als "BusinessKey"
                if (metadata.get("BusinessKey") == null || (metadata.get("BusinessKey").toString().isBlank())) {
                    return null;
                } else {
                    return metadata.get("BusinessKey").toString();
                }
            } else {
                return processInstance.getBusinessKey();
            }
        } else {
            return processInstance.getCorrelationKey();
        }
    }

    public static void setBusinessKey(ProcessNodeEvent event, Map<String, Object> metadata) {

        RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getNodeInstance().getProcessInstance();
        String BusinessKey = searchBusinessKey(event, metadata);

        if (BusinessKey == null || BusinessKey.isBlank()) {
            BusinessKey = metadata.get("setBusinessKey").toString();
        } else {
            System.out.println("Methode setBusinessKey wurde übersprungen, da der Busineskey bereits gesetzt wurde");
            return;
        }

        if (BusinessKey.startsWith("#{")) {

            processInstance.setCorrelationKey(exececuteSimpleMVELexpression(BusinessKey, event).toString());

        } else {
            processInstance.setCorrelationKey(BusinessKey);
        }
    }

    public static void currentProcessStatusToVariable(ProcessNodeEvent event, Map<String, Object> metadata) {

        RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getNodeInstance().getProcessInstance();
        String BusinessKey = searchBusinessKey(event, metadata);
        ProcessStatus entity = ProcessStatus.findById(BusinessKey);
        
        if (entity == null) {
            throw new RuntimeException("Kein Datenbankeintrag zum Businesskey gefunden");
        }

        //Set Kogito Variable
        //if (processInstance.getVariables().containsKey("fallStatus")) {

        // Eigentlich können wir auch gleich unser Objekt direkt als Variable setzen
        processInstance.setVariable("fallStatus", entity);

        /*ProcessStatus fallStatus = new ProcessStatus();

        // Falls die Variable im Prozess existiert, nehmen wir lieber diese anstatt die jedes Mal platt zu machen
        //
        if (processInstance.getVariables().containsKey("fallStatus") && processInstance.getVariables().get("fallStatus") != null)
            fallStatus = (ProcessStatus) processInstance.getVariables().get("fallStatus");


            fallStatus.reference = entity.reference;
            fallStatus.referenceDate = entity.referenceDate;
            fallStatus.status = entity.status;
            fallStatus.currentTask = entity.currentTask;
            fallStatus.lastSuccessfulTask = entity.lastSuccessfulTask;
            fallStatus.exceptionType = entity.exceptionType;
            fallStatus.exceptionReason = entity.exceptionReason;
            fallStatus.subTask = entity.subTask;
            fallStatus.created = entity.created;
            fallStatus.updated = entity.updated;
            

            //Set Kogito Variablie
        processInstance.setVariable("fallStatus", fallStatus);*/
        //} else {
        //    throw new RuntimeException("Die Variable-> fallStatus mit typ database.FallStatus <- muss für diese Methode gesetzt sein");
        //}

    }

    public static void createProcessStatus(ProcessNodeEvent event, Map<String, Object> metadata) {

        String BusinessKey = searchBusinessKey(event, metadata);
        ProcessStatus processStatus = new ProcessStatus();
        ProcessStatus entity = ProcessStatus.findById(BusinessKey);
        RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getNodeInstance().getProcessInstance();

        String status = "NEW";        
        String currentTask = "start";
        LocalDate referenceDate = LocalDate.parse("9999-12-31");

        if (entity != null) {
            if (metadata.get("createProcessStatus").toString() == "CatchEntityExistsException") {
                System.out.println(
                        "EntityExistsException wurde durch CatchEntityExistsException in Metadata createProcessStatus abgefangen. Ursache: es ist bereits eine ID der Process_Status Tabelle enthalten");
                return;
            }
            throw new EntityExistsException("BusinessKey existiert bereits");
        }

        if (processInstance.getVariables().containsKey("fallStatus")) {
            if (metadata.get("status") == null || (metadata.get("status").toString().isBlank())) {
                status = "NEW";
            } else {
                status = metadata.get("status").toString().toUpperCase();
            }

            if (metadata.get("currentTask") == null || metadata.get("currentTask").toString().isEmpty() || (metadata.get("currentTask").toString().isBlank())) {
                currentTask = "start";
            } else {
                if (metadata.get("currentTask").toString().startsWith("#{")) {
                    String expression = metadata.get("currentTask").toString();
                    currentTask = exececuteSimpleMVELexpression(expression, event).toString();
        
                } else {
                    currentTask =  metadata.get("currentTask").toString();
                }
                //currentTask = metadata.get("currentTask").toString();
            }

            if (metadata.get("referenceDate") == null || metadata.get("referenceDate").toString().isEmpty() || (metadata.get("referenceDate").toString().isBlank())) {
                throw new RuntimeException("Es ist kein reference_date gesetzt");
            } else {
                
                if (metadata.get("referenceDate").toString().startsWith("#{")) {
                    String expression = metadata.get("referenceDate").toString();
                    referenceDate = LocalDate.parse(exececuteSimpleMVELexpression(expression, event).toString());
        
                } else {
                    referenceDate = LocalDate.parse((metadata.get("referenceDate").toString()));
                }
                
            }
        }

        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
        processStatus.created = ZonedDateTime.now(berlinZone);
        processStatus.updated = processStatus.created;
        processStatus.reference = BusinessKey;
        processStatus.status = status;
        processStatus.currentTask = currentTask;
        processStatus.referenceDate = referenceDate;

        // Check 
        ProcessStatus.ValidProcessstatusCheck(processStatus);
        //Persist
        ProcessStatus.create(processStatus);
    }

    public static String evaluateExpressionValue(String expressionString, ProcessNodeEvent event) {

        String exprValue = null;
        if(expressionString != null && !expressionString.isBlank())
        {
            // MVEL erkennen und auflösen
            if(expressionString.startsWith("#{")) {

                Object mvelResult = exececuteSimpleMVELexpression(expressionString, event);
                if(mvelResult != null) 
                    exprValue = mvelResult.toString();
            }
            else exprValue = expressionString;
        }
        return exprValue;
    }

    public static Object exececuteSimpleMVELexpression(String expressionString, ProcessNodeEvent event) {
        
        try {
            // Sicheren Parser-Kontext erstellen
            RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getNodeInstance().getProcessInstance();
            
            expressionString = expressionString.replace("#{", "");
            expressionString = expressionString.replace("}", "");

            int iend = expressionString.indexOf(".");
            String VariableName = new String();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

             if (iend != -1) {

                VariableName = expressionString.substring(0, iend);
                Map<String, Object> map = objectMapper.convertValue(processInstance.getVariable(VariableName), new TypeReference<Map<String, Object>>() {});
                Map<String, Object> finalmap = new HashMap<String, Object>();
                finalmap.put(VariableName, map);
                return MVEL.eval(expressionString, finalmap);

            } else {
                VariableName = expressionString;
                return processInstance.getVariable(VariableName).toString();              
            }
        } catch (Exception e) {
            // Fehlerbehandlung (z.B. Logging, benutzerdefinierte Exceptions)
            throw new RuntimeException("Fehler bei der Auswertung des MVEL-Ausdrucks: " + e.getMessage(), e);
        }
    }
}

class InvalidMetadataException extends RuntimeException {
    InvalidMetadataException(String message) {
        super(message);
    }
}

class MissingReferenceException extends RuntimeException {
    MissingReferenceException(String message) {
        super(message);
    }
}
