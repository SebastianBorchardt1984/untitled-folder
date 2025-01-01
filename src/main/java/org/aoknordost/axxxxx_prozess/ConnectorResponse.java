package org.aoknordost.axxxxx_prozess;

import java.util.HashMap;
import java.util.Objects;

/** JavaBean für die Antwort vom UiConnector
 */
public class ConnectorResponse{

    public String reference;
    public String status;
    public String exceptionType;
    public String exceptionReason;
    public HashMap<String, String> output;

    public ConnectorResponse() {
        this.reference = null;
        this.status =  null;
        this.exceptionType = null;
        this.exceptionReason =  null;
    }

    public ConnectorResponse(String reference, String status, String exceptionType, String exceptionReason, HashMap<String, String> output) {
        this.reference = reference;
        this.status = status;
        this.exceptionType = exceptionType;
        this.exceptionReason = exceptionReason;
        this.output = output;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccessful() {
        return this.getStatus().toUpperCase().equals("SUCCESSFUL");
    }

    public boolean isWithdrawn() {
        return this.getStatus().toUpperCase().equals("WITHDRAWN");
    }

    public boolean isFailed() {
        return this.getStatus().toUpperCase().equals("FAILED");
    }

    public boolean isFailedBE() {
        return this.isFailed() && this.getExceptionType().toUpperCase().equals("BUSINESSEXCEPTION");
    }

    public boolean isFailedAE() {
        return this.isFailed() && this.getExceptionType().toUpperCase().equals("APPLICATIONEXCEPTION");
    }


    public String getExceptionType() {
        return this.exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionReason() {
        return this.exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    public HashMap<String, String> getOutput() {
        return output;
    }

    public void setOutput(HashMap<String, String> output) {
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ConnectorResponse)) {
            return false;
        }
        ConnectorResponse connectorResponse = (ConnectorResponse) o;
        return Objects.equals(reference, connectorResponse.reference) && Objects.equals(status, connectorResponse.status) && Objects.equals(exceptionType, connectorResponse.exceptionType) && Objects.equals(exceptionReason, connectorResponse.exceptionReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, status, exceptionType, exceptionReason);
    }

    @Override
    public String toString() {
        return "{" +
            " reference='" + getReference() + "'" +
            ", status='" + getStatus() + "'" +
            ", exceptionType='" + getExceptionType() + "'" +
            ", exceptionReason='" + getExceptionReason() + "'" +
            ", output='" + getOutput() + "'" +
            "}";
    }

}
