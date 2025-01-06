import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.kie.kogito.incubation.application.AppRoot;
import org.kie.kogito.incubation.common.DataContext;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.processes.ProcessIds;
import org.kie.kogito.incubation.processes.services.StraightThroughProcessService;

@Path("/custom-rest-process")
public class CustomRestProcess {

    @Inject
    AppRoot appRoot;
    @Inject
    StraightThroughProcessService svc;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DataContext customProcessScripts(Map<String, Object> payload) {
        // path: /processes/scripts

        var id = appRoot.get(ProcessIds.class).get("deals");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("traveller", new Traveller("John", "Doe", "john...@example.com", "American", new Address("main street", "Boston", "10005", "US")));
        parameters.put("trip", new Trip("New York", "US", new Date(), new Date()));
        MapDataContext ctx = MapDataContext.of(parameters);
        return svc.evaluate(id, ctx);
    }
}