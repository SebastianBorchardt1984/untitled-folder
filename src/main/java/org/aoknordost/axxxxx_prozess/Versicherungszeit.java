package org.aoknordost.axxxxx_prozess;

import java.io.Serializable;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Objects;

@RegisterForReflection(serialization = true)
public class Versicherungszeit implements Serializable {

    public String tbid;
    public String geschäftspartnernummer;
    public String versicherungszeitentyp;
    public String versicherungsart;
    public String storno;
    public String interimskennzeichen;
    public String beginn;
    public String ende;


    public Versicherungszeit() {
    }

    public Versicherungszeit(String tbid, String geschäftspartnernummer, String versicherungszeitentyp, String versicherungsart, String storno, String interimskennzeichen, String beginn, String ende) {
        this.tbid = tbid;
        this.geschäftspartnernummer = geschäftspartnernummer;
        this.versicherungszeitentyp = versicherungszeitentyp;
        this.versicherungsart = versicherungsart;
        this.storno = storno;
        this.interimskennzeichen = interimskennzeichen;
        this.beginn = beginn;
        this.ende = ende;
    }

    public String getTbid() {
        return this.tbid;
    }

    public void setTbid(String tbid) {
        this.tbid = tbid;
    }

    public String getGeschäftspartnernummer() {
        return this.geschäftspartnernummer;
    }

    public void setGeschäftspartnernummer(String geschäftspartnernummer) {
        this.geschäftspartnernummer = geschäftspartnernummer;
    }

    public String getVersicherungszeitentyp() {
        return this.versicherungszeitentyp;
    }

    public void setVersicherungszeitentyp(String versicherungszeitentyp) {
        this.versicherungszeitentyp = versicherungszeitentyp;
    }

    public String getVersicherungsart() {
        return this.versicherungsart;
    }

    public void setVersicherungsart(String versicherungsart) {
        this.versicherungsart = versicherungsart;
    }

    public String getStorno() {
        return this.storno;
    }

    public void setStorno(String storno) {
        this.storno = storno;
    }

    public String getInterimskennzeichen() {
        return this.interimskennzeichen;
    }

    public void setInterimskennzeichen(String interimskennzeichen) {
        this.interimskennzeichen = interimskennzeichen;
    }

    public String getBeginn() {
        return this.beginn;
    }

    public void setBeginn(String beginn) {
        this.beginn = beginn;
    }

    public String getEnde() {
        return this.ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public Versicherungszeit tbid(String tbid) {
        setTbid(tbid);
        return this;
    }

    public Versicherungszeit geschäftspartnernummer(String geschäftspartnernummer) {
        setGeschäftspartnernummer(geschäftspartnernummer);
        return this;
    }

    public Versicherungszeit versicherungszeitentyp(String versicherungszeitentyp) {
        setVersicherungszeitentyp(versicherungszeitentyp);
        return this;
    }

    public Versicherungszeit versicherungsart(String versicherungsart) {
        setVersicherungsart(versicherungsart);
        return this;
    }

    public Versicherungszeit storno(String storno) {
        setStorno(storno);
        return this;
    }

    public Versicherungszeit interimskennzeichen(String interimskennzeichen) {
        setInterimskennzeichen(interimskennzeichen);
        return this;
    }

    public Versicherungszeit beginn(String beginn) {
        setBeginn(beginn);
        return this;
    }

    public Versicherungszeit ende(String ende) {
        setEnde(ende);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Versicherungszeit)) {
            return false;
        }
        Versicherungszeit versicherungszeit = (Versicherungszeit) o;
        return Objects.equals(tbid, versicherungszeit.tbid) && Objects.equals(geschäftspartnernummer, versicherungszeit.geschäftspartnernummer) && Objects.equals(versicherungszeitentyp, versicherungszeit.versicherungszeitentyp) && Objects.equals(versicherungsart, versicherungszeit.versicherungsart) && Objects.equals(storno, versicherungszeit.storno) && Objects.equals(interimskennzeichen, versicherungszeit.interimskennzeichen) && Objects.equals(beginn, versicherungszeit.beginn) && Objects.equals(ende, versicherungszeit.ende);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tbid, geschäftspartnernummer, versicherungszeitentyp, versicherungsart, storno, interimskennzeichen, beginn, ende);
    }

    @Override
    public String toString() {
        return "{" +
            " tbid='" + getTbid() + "'" +
            ", geschäftspartnernummer='" + getGeschäftspartnernummer() + "'" +
            ", versicherungszeitentyp='" + getVersicherungszeitentyp() + "'" +
            ", versicherungsart='" + getVersicherungsart() + "'" +
            ", storno='" + getStorno() + "'" +
            ", interimskennzeichen='" + getInterimskennzeichen() + "'" +
            ", beginn='" + getBeginn() + "'" +
            ", ende='" + getEnde() + "'" +
            "}";
    }
    
}
