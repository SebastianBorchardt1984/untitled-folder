package org.aoknordost.axxxxx_prozess.config;

import org.kie.kogito.config.ConfigBean;
import org.kie.kogito.process.impl.DefaultProcessEventListenerConfig;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/*
 * Dienst dazu den Konfigurations-Listener InitConfigListener zu registieren
 */
@ApplicationScoped
public class InitConfigListenerConfig extends DefaultProcessEventListenerConfig {

    private InitConfigListener listener;

    @Inject
    ConfigBean configBean;

    public InitConfigListenerConfig() {
        super();
    }

    @PostConstruct
    public void setup() {
        this.listener = new InitConfigListener();
        register(this.listener);
    }

}
