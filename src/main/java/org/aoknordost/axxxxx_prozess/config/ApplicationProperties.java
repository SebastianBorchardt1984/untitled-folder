package org.aoknordost.axxxxx_prozess.config;

import java.io.Serializable;

import org.eclipse.microprofile.config.ConfigProvider;
import io.smallrye.config.SmallRyeConfig;

/**
 * Klasse für den Zugriff auf die applications.properties
 * Eigentlich soll das über Annotations funktionieren, tut es aber nicht deshalb die etwas
 * aufwendigere Implementierung
 */
public class ApplicationProperties implements Serializable {


    /* Prefix für die Einstellungen -> Muss angepasst werden
     */
    private static final String confPrefix = "aXXXXX";

    /* Variablen für die einzelnen Einstellungen
    */
    public String queuesFallanlage;
    public String queuesBrief;
    public String userAsset;


    /**  Standard-Kosntruktor -> Muss für jede Einstellung erweitert werden
     */
    public ApplicationProperties() {
        
        SmallRyeConfig config = ConfigProvider.getConfig().unwrap(SmallRyeConfig.class);
        this.queuesFallanlage = this.getValue(config, confPrefix, "queues.fallanlage");
        this.queuesBrief = this.getValue(config, confPrefix, "queues.brief");
        this.userAsset = this.getValue(config, confPrefix, "userAsset");
    }

    /** Wert eines Konfigurationsschalters auslesen
     * @param config Objekt für den zugriff auf die Config
     * @param prefix Prefix der Konfigurationseinstellung
     * @param name Name des Konfigurationsschalters hinter dem dem Präfix mit Punkt, also z.B. "port" für die Einstellung "aXXXXXX.port"
     * @return Wert des Konfigurationsschalters
     */
    private String getValue(SmallRyeConfig config, String prefix, String name) {
        String value = config.getConfigValue(prefix + "." + name).getValue();        
        System.out.println("Config-Parameter: " + prefix + "." + name + ": " + value);
        return value;
    }
}
