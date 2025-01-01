/*
 * Diese Klasse dienst dazu einen Request von Kogito zum Auslösen eines Dokuments in oscare (CORE) abzubilden 
 */

 package org.aoknordost.axxxxx_prozess;

import java.util.HashMap;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PrintDocumentRequest {

    /*
     * Reference key for the request
     */
    private String reference;

    /*
     * Asset  name for user authentification within osacre
     */
    private String userAsset;

    /*
     * Number of claim where the document should be triggered
     */
    private String claim;

    /*
     * Sarch string to find item within the claim tree which has to be selected
     */
    private String guiTreeSearchString;

    /*
     * Id of the document type which should be triggered
     */
    private String documentNumber;

    /*
     * Mode of printing, valid values are "single" and "mass"
     */
    private String printMode;

    /*
     * Getter
     */
    public String getReference() {
        return reference;
    }

    public String getUserAsset() {
        return userAsset;
    }

    public String getClaim() {
        return claim;
    }

    public String getGuiTreeSearchString() {
        return guiTreeSearchString;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getPrintMode() {
        return printMode;
    }

    /*
     * Setter 
     */
    public void setReference(String reference) {
        this.reference = reference;
    }


    public void setUserAsset(String userAsset) {
        this.userAsset = userAsset;
    }


    public void setClaim(String claim) {
        this.claim = claim;
    }


    public void setGuiTreeSearchString(String guiTreeSearchString) {
        this.guiTreeSearchString = guiTreeSearchString;
    }


    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }


    public void setPrintMode(String printMode) {
        this.printMode = printMode;
    }

    /** Get all relevant business values as HashMap
     */
    HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("reference", this.getReference());
        hashMap.put("userAsset", this.getUserAsset());
        hashMap.put("claim", this.getClaim());
        hashMap.put("guiTreeSearchString", this.getGuiTreeSearchString());
        hashMap.put("documentNumber", this.getDocumentNumber());
        hashMap.put("printMode", this.getPrintMode());

        return hashMap;
    }

    /*
     * Overloaded method to see objects content
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}