package com.tricast.web.server;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tricast.beans.Event;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.EventManager;


@Path("/events")
public class EventService extends LVSResource {

		private static final Logger log = LogManager.getLogger(EventService.class);
	    private final EventManager manager;
	    private Workspace workspace;

	    @Inject
	    public EventService(EventManager manager, Workspace workspace) {
        this.manager = manager;
			this.workspace = workspace;
		}

	    @GET
	    @Path("/all")
	    @Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all Events");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @GET
	    @Path("/open")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response getAllOpen()
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all Open Events");
	        try {
	            return respondGet(manager.getAllOpen(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @GET
	    @Path("{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response getById(@PathParam("id") long id, @PathParam("outcomeId") long outcomeId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get event id @ " + id);
	        try {
	            return respondGet(manager.getById(workspace,id));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createEvent(Event newEvent) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create new event with this decription #"
	                + newEvent.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newEvent), "\\events");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\events", 500);
	        }
	    }

	    @PUT
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response updateEvent(Event updateEvent) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to update eventdata with this eventId #" + updateEvent.getId() + "and this eventDescription #"
	                + updateEvent.getDescription());
	        try {
	            return respondPut(manager.update(workspace, updateEvent));
	        } catch (SQLException ex) {
	            return respondPut(ex.getMessage(), 500);
	        }
	    }

	    @DELETE
	    @Path("/{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deleteEvent(@PathParam("id") long id)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete event id #" + id);
	        try {
	            return respondDelete(manager.deleteById(workspace, id));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }
	    
	    @GET
	    @Path("details/{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response getById(@PathParam("id") long eventId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get event details with id = " + eventId);
	        try {
	            return respondGet(manager.getEventDetails(workspace, eventId));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }



}
