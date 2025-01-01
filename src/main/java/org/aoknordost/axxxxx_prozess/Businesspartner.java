package org.aoknordost.axxxxx_prozess;

import java.io.Serializable;
import java.util.Objects;

public class Businesspartner implements Serializable {

    public String gpnr;
    public String pkrv;
    public String pkkv;
    public String pkkvde;
    public String geburtsdatum;
    public String vorname;
    public String nachname;


    public Businesspartner() {
    }

    public Businesspartner(String gpnr, String pkrv, String pkkv, String pkkvde, String geburtsdatum, String vorname, String nachname) {
        this.gpnr = gpnr;
        this.pkrv = pkrv;
        this.pkkv = pkkv;
        this.pkkvde = pkkvde;
        this.geburtsdatum = geburtsdatum;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getGpnr() {
        return this.gpnr;
    }

    public void setGpnr(String gpnr) {
        this.gpnr = gpnr;
    }

    public String getPkrv() {
        return this.pkrv;
    }

    public void setPkrv(String pkrv) {
        this.pkrv = pkrv;
    }

    public String getPkkv() {
        return this.pkkv;
    }

    public void setPkkv(String pkkv) {
        this.pkkv = pkkv;
    }

    public String getPkkvde() {
        return this.pkkvde;
    }

    public void setPkkvde(String pkkvde) {
        this.pkkvde = pkkvde;
    }

    public String getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Businesspartner gpnr(String gpnr) {
        setGpnr(gpnr);
        return this;
    }

    public Businesspartner pkrv(String pkrv) {
        setPkrv(pkrv);
        return this;
    }

    public Businesspartner pkkv(String pkkv) {
        setPkkv(pkkv);
        return this;
    }

    public Businesspartner pkkvde(String pkkvde) {
        setPkkvde(pkkvde);
        return this;
    }

    public Businesspartner geburtsdatum(String geburtsdatum) {
        setGeburtsdatum(geburtsdatum);
        return this;
    }

    public Businesspartner vorname(String vorname) {
        setVorname(vorname);
        return this;
    }

    public Businesspartner nachname(String nachname) {
        setNachname(nachname);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Businesspartner)) {
            return false;
        }
        Businesspartner businesspartner = (Businesspartner) o;
        return Objects.equals(gpnr, businesspartner.gpnr) && Objects.equals(pkrv, businesspartner.pkrv) && Objects.equals(pkkv, businesspartner.pkkv) && Objects.equals(pkkvde, businesspartner.pkkvde) && Objects.equals(geburtsdatum, businesspartner.geburtsdatum) && Objects.equals(vorname, businesspartner.vorname) && Objects.equals(nachname, businesspartner.nachname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gpnr, pkrv, pkkv, pkkvde, geburtsdatum, vorname, nachname);
    }

    @Override
    public String toString() {
        return "{" +
            " gpnr='" + getGpnr() + "'" +
            ", pkrv='" + getPkrv() + "'" +
            ", pkkv='" + getPkkv() + "'" +
            ", pkkvde='" + getPkkvde() + "'" +
            ", geburtsdatum='" + getGeburtsdatum() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", nachname='" + getNachname() + "'" +
            "}";
    }
    
}
