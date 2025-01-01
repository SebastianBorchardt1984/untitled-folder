package database;

import java.util.Arrays;
import java.util.Map;

import org.kie.api.event.process.*;
import org.kie.kogito.internal.process.event.KogitoProcessEventListener;

public class ProcessStatusListener implements KogitoProcessEventListener {

    /** Zentrale Methode zur Behandlung der Meta-Attributen zu Vermeidung von Redudanz
     * @param event Node-Evewtn der den Aufruf dieser Methode zugrunde liegt
     * @param metadata Liste der Meta-Attribite am Node
     */
    private void handleMetaAttributes(ProcessNodeEvent event, Map<String, Object> metadata) {

        if (metadata.containsKey("setBusinessKey")) {
            ProcessStatusListenerService.setBusinessKey(event, metadata);
        }

        if (metadata.containsKey("createProcessStatus")) {
            ProcessStatusListenerService.createProcessStatus(event, metadata);
        }

        String[] keysToCheck = {    
            "clearExceptionInfo",
            "exceptionReason", 
            "exceptionType", 
            "currentTask", 
            "lastSuccessfulTask",
            "referenceDate", 
            "status", 
            "setState",
            "subTask"            
        };
        if (Arrays.stream(keysToCheck).anyMatch(metadata::containsKey)) {
            ProcessStatusListenerService.searchBusinessKey(event, metadata);
            ProcessStatusListenerService.updateProcessStatus(event, metadata);
        }

        if (metadata.containsKey("getProcessStatus")) {
            ProcessStatusListenerService.currentProcessStatusToVariable(event, metadata);
        }
    }    

    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        // itentionally left blank
    }

    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        // itentionally left blank
    }

    public void afterProcessCompleted(ProcessCompletedEvent event) {
        // intentionally left blank
    }

    public void afterProcessStarted(ProcessStartedEvent event) {

    }

    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        // System.out.println("VariableName: " + event.getVariableId());

    }

    public void beforeNodeLeft(ProcessNodeLeftEvent event) {

        // Reihenfolge ist absichtlich so gewählt ggf. kann man später alle Metadaten
        // über Sichtbare Reihenfolge als Logik implementieren. Metadaten kommen als
        // hashMap zurück, allerdings weiß ich nicht ob die indexartig erzeugt werden

        Map<String, Object> metadata = event.getNodeInstance().getNode().getMetaData();

        handleMetaAttributes(event, metadata);
    }

    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        // Map<String, Object> metadata =
        // event.getNodeInstance().getProcessInstance().getProcess().getMetaData();
    }

    public void beforeProcessCompleted(ProcessCompletedEvent event) {
        // // intentionally left blank
    }

    public void beforeProcessStarted(ProcessStartedEvent event) {

        /* Diese Lösung führt zum Absturz weil sich das Event-Objekt nicht casten lässt 
        Map<String, Object> metadata = event.getProcessInstance().getProcess().getMetaData();

        if (metadata.containsKey("setBusinessKey")) {
            ProcessStatusListenerService.setBusinessKey((ProcessNodeEvent) event, metadata);
        }

        if (metadata.containsKey("createProcessStatus")) {
            ProcessStatusListenerService.createProcessStatus((ProcessNodeEvent) event, metadata);
        }

        String[] keysToCheck = { "status", "currentTask", "referenceDate", "lastSuccessfulTask",
                "exceptionType", "exceptionReason", "subTask", "setState" };
        if (Arrays.stream(keysToCheck).anyMatch(metadata::containsKey)) {
            ProcessStatusListenerService.searchBusinessKey((ProcessNodeEvent) event, metadata);
            ProcessStatusListenerService.updateProcessStatus((ProcessNodeEvent) event, metadata);
        }

        if (metadata.containsKey("getProcessStatus")) {
            ProcessStatusListenerService.currentProcessStatusToVariable((ProcessNodeEvent) event, metadata);
        }
        */
    }

    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
        // intentionally left blank
    }
}
