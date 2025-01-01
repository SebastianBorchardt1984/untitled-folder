package org.aoknordost.axxxxx_prozess;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashMap;

public class UiPathQueueItem implements Serializable{
    public String reference;
    public String queueName;
    public String responseTopic;
    public String responseType;
    public HashMap<String,String> specificContent;

    public UiPathQueueItem() {
    }

    public UiPathQueueItem(String reference, String queueName, String responseTopic, String responseType, HashMap<String,String> specificContent) {
        this.reference = reference;
        this.queueName = queueName;
        this.responseTopic = responseTopic;
        this.responseType = responseType;
        this.specificContent = specificContent;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getResponseTopic() {
        return this.responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public HashMap<String,String> getSpecificContent() {
        return this.specificContent;
    }

    public void setSpecificContent(HashMap<String,String> specificContent) {
        this.specificContent = specificContent;
    }

    public UiPathQueueItem reference(String reference) {
        setReference(reference);
        return this;
    }

    public UiPathQueueItem queueName(String queueName) {
        setQueueName(queueName);
        return this;
    }

    public UiPathQueueItem responseTopic(String responseTopic) {
        setResponseTopic(responseTopic);
        return this;
    }

    public UiPathQueueItem responseType(String responseType) {
        setResponseType(responseType);
        return this;
    }

    public UiPathQueueItem specificContent(HashMap<String,String> specificContent) {
        setSpecificContent(specificContent);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UiPathQueueItem)) {
            return false;
        }
        UiPathQueueItem queueItem = (UiPathQueueItem) o;
        return Objects.equals(reference, queueItem.reference) && Objects.equals(queueName, queueItem.queueName) && Objects.equals(responseTopic, queueItem.responseTopic) && Objects.equals(responseType, queueItem.responseType) && Objects.equals(specificContent, queueItem.specificContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, queueName, responseTopic, responseType, specificContent);
    }

    @Override
    public String toString() {
        return "{" +
            " reference='" + getReference() + "'" +
            ", queueName='" + getQueueName() + "'" +
            ", responseTopic='" + getResponseTopic() + "'" +
            ", responseType='" + getResponseType() + "'" +
            ", specificContent='" + getSpecificContent().toString() + "'" +
            "}";
    }
    
}
