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

import com.tricast.beans.Period;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.PeriodManager;

@Path("/periods")
public class PeriodService extends LVSResource {

		private static final Logger log = LogManager.getLogger(BetDataService.class);
	    private final PeriodManager manager;
	    private Workspace workspace;

	    @Inject
    public PeriodService(PeriodManager manager, Workspace workspace) {
			super();
			this.manager = manager;
			this.workspace = workspace;
		}

	    @GET
	    @Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all periodData");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @GET
	    @Path("{period}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response getById(@PathParam("period") long periodId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get eventData eventId = " + periodId);
	        try {
	            return respondGet(manager.getById(workspace,periodId));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createEvent(Period newPeriod) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create new event with this periodId #" + newPeriod.getId() + "and this period decription #"
	                + newPeriod.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newPeriod), "\\countries");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\countries", 500);
	        }
	    }

	    @PUT
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response updateBetData(Period updatePeriod) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to update eventdata with this eventId #" + updatePeriod.getId() + "and this eventDescription #"
	                + updatePeriod.getDescription());
	        try {
	            return respondPut(manager.update(workspace, updatePeriod));
	        } catch (SQLException ex) {
	            return respondPut(ex.getMessage(), 500);
	        }
	    }

	    @DELETE
	    @Path("/{periodId}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deleteCountryData(@PathParam("periodId") long periodId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete perioddata with this periodId #" + periodId);
	        try {
	            return respondDelete(manager.deleteById(workspace, periodId));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }

}
