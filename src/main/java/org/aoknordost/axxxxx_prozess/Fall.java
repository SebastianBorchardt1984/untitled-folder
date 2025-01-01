package org.aoknordost.axxxxx_prozess;

import java.io.Serializable;
import java.util.Objects;

public class Fall implements Serializable{
    
    public String reference;
    public String teilfall;
    public String procurement;
    public String gposnr;
    public String eingangsdatum;
    public String le_nummer;
    public String datum_von;


    public Fall() {
    }


    public Fall(String reference, String teilfall, String procurement, String gposnr, String eingangsdatum, String le_nummer, String datum_von) {
        this.reference = reference;
        this.teilfall = teilfall;
        this.procurement = procurement;
        this.gposnr = gposnr;
        this.eingangsdatum = eingangsdatum;
        this.le_nummer = le_nummer;
        this.datum_von = datum_von;
    }


    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTeilfall() {
        return this.teilfall;
    }

    public void setTeilfall(String teilfall) {
        this.teilfall = teilfall;
    }

    public String getProcurement() {
        return this.procurement;
    }

    public void setProcurement(String procurement) {
        this.procurement = procurement;
    }

    public String getGposnr() {
        return this.gposnr;
    }

    public void setGposnr(String gposnr) {
        this.gposnr = gposnr;
    }

    public String getEingangsdatum() {
        return this.eingangsdatum;
    }

    public void setEingangsdatum(String eingangsdatum) {
        this.eingangsdatum = eingangsdatum;
    }

    public String getLe_nummer() {
        return this.le_nummer;
    }

    public void setLe_nummer(String le_nummer) {
        this.le_nummer = le_nummer;
    }

    public String getDatum_von() {
        return this.datum_von;
    }

    public void setDatum_von(String datum_von) {
        this.datum_von = datum_von;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Fall)) {
            return false;
        }
        Fall fall = (Fall) o;
        return Objects.equals(reference, fall.reference) && Objects.equals(teilfall, fall.teilfall) && Objects.equals(procurement, fall.procurement) && Objects.equals(gposnr, fall.gposnr) && Objects.equals(eingangsdatum, fall.eingangsdatum) && Objects.equals(le_nummer, fall.le_nummer) && Objects.equals(datum_von, fall.datum_von);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, teilfall, procurement, gposnr, eingangsdatum, le_nummer, datum_von);
    }

    @Override
    public String toString() {
        return "{" +
            " reference='" + getReference() + "'" +
            ", teilfall='" + getTeilfall() + "'" +
            ", procurement='" + getProcurement() + "'" +
            ", gposnr='" + getGposnr() + "'" +
            ", eingangsdatum='" + getEingangsdatum() + "'" +
            ", le_nummer='" + getLe_nummer() + "'" +
            ", datum_von='" + getDatum_von() + "'" +
            "}";
    }

}