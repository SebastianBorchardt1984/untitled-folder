package org.aoknordost.axxxxx_prozess.config;

import java.util.Map;
import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.kie.api.event.process.*;
import org.kie.kogito.internal.process.event.KogitoProcessEventListener;

/**
 * Die Klasse dient dazu dass man, sofern das entsprechende Meta-Datum gesetzt ist, die Konfigurationseinstellungen zu laden und als Container in den Kogito-Prozess zu legen
 */
public class InitConfigListener implements KogitoProcessEventListener {

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
    }

    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {

        /* Laden von prozess-spezifischen Einstellungen
         */
        Map<String, Object> metadata = event.getProcessInstance().getProcess().getMetaData();
        if (metadata.containsKey("loadProperties")) {
            String setting = metadata.get("loadProperties").toString();
            if(setting != null && setting.equals("true")) {
                ApplicationProperties props = new ApplicationProperties();
                RuleFlowProcessInstance processInstance = (RuleFlowProcessInstance) event.getProcessInstance();
                processInstance.setVariable("properties", props);
                System.out.println("Konfiguration mit Prefix " + setting + " geladen");

            }
            //else throw new InvalidMetadataException("loadProperties darf nicht leer sein");
        }
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
    }
    
}

class InvalidMetadataException extends RuntimeException {
    InvalidMetadataException(String message) {
        super(message);
    }
}

