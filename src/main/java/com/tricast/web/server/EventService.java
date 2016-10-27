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

		private static final Logger log = LogManager.getLogger(BetDataService.class);
	    private final EventManager manager;
	    private Workspace workspace;

	    @Inject
    public EventService(EventManager manager, Workspace workspace) {
			super();
			this.manager = manager;
			this.workspace = workspace;
		}

	    @GET
	    @Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all betdata");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @GET
	    @Path("{event}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response getById(@PathParam("event") long eventId, @PathParam("outcomeId") long outcomeId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get eventData eventId = " + eventId);
	        try {
	            return respondGet(manager.getById(workspace,eventId));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createEvent(Event newEvent) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create new event with this eventId #" + newEvent.getId() + "and this event decription #"
	                + newEvent.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newEvent), "\\countries");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\countries", 500);
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
	    @Path("/{eventId}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deleteEvent(@PathParam("EventId") long eventId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete eventdata with this eventId #" + eventId);
	        try {
	            return respondDelete(manager.deleteById(workspace, eventId));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }



}
