package database;

import org.kie.kogito.config.ConfigBean;
import org.kie.kogito.process.impl.DefaultProcessEventListenerConfig;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProcessStatusListenerConfig extends DefaultProcessEventListenerConfig {

    private ProcessStatusListener listener;

    @Inject
    ConfigBean configBean;

    public ProcessStatusListenerConfig() {
        super();
    }

    @PostConstruct
    public void setup() {
        this.listener = new ProcessStatusListener();
        register(this.listener);
    }

}
