package org.aoknordost.axxxxx_prozess;
import jakarta.inject.Named;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.Model;

public class TestService {

    @Inject
    @Named("deals")
    Process<? extends Model> deals;

    public void startdeals() {
        Model model = deals.createModel();

        // Setzen Sie die erforderlichen Prozessvariablen
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("traveller", new Traveller("John", "Doe", "john...@example.com", "American", new Address("main street", "Boston", "10005", "US")));
        parameters.put("trip", new Trip("New York", "US", new Date(), new Date()));
        model.fromMap(parameters);

        // Starten Sie die Prozessinstanz
        ProcessInstance<? extends Model> processInstance = deals.createInstance(model);
        processInstance.start();
    }
