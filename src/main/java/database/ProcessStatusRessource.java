package database;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
//import java.util.logging.Logger;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("process/status") // Base path for all status endpoints
@Produces("application/json")
@Consumes("application/json")
public class ProcessStatusRessource {

    //private static final Logger LOGGER = Logger.getLogger(ProcessStatusRessource.class.getName());

    @GET
    public List<ProcessStatus> list() {
        return ProcessStatus.listAll();
    }

    @GET
    @Path("/{reference}")
    public ProcessStatus get(String reference) {
        return ProcessStatus.findById(reference);
    }

    @POST
    @Transactional
    public Response create(ProcessStatus processStatus) {

        ProcessStatus entity = ProcessStatus.findById(processStatus.reference);
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");

        if (entity != null) {
            throw new EntityExistsException("BusinessKey existiert bereits");
        }

        ProcessStatus.ValidProcessstatusCheck(processStatus);        
        processStatus.created = ZonedDateTime.now(berlinZone);
        processStatus.updated = processStatus.created;
        processStatus.persist();
        return Response.created(URI.create("/process/status/" + processStatus.reference)).build();
    }



    @PUT
    @Path("/{reference}")
    @Transactional
    public ProcessStatus update(String reference, ProcessStatus processStatus) {
        ProcessStatus entity = ProcessStatus.findById(reference);        
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
       
        if (entity == null) {
            throw new NotFoundException();
        }

        //processStatus = ProcessStatus.mapFromProcessStatus(processStatus);
        ProcessStatus.ValidProcessstatusCheck(processStatus); 
        processStatus.reference = entity.reference; 
        processStatus.updated = ZonedDateTime.now(berlinZone);
        return processStatus;
    }

    @DELETE
    @Path("/{reference}")
    @Transactional
    public void delete(String reference) {
        ProcessStatus processStatus = ProcessStatus.findById(reference);
        if (processStatus == null) {
            throw new NotFoundException();
        }
        processStatus.delete();
    }

}
