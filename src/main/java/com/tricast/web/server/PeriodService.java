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

import com.sun.jersey.spi.container.ResourceFilters;
import com.tricast.beans.Period;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.filters.AuthenticationFilter;
import com.tricast.web.manager.PeriodManager;

@Path("/periods")
@ResourceFilters(AuthenticationFilter.class)
public class PeriodService extends LVSResource {

		private static final Logger log = LogManager.getLogger(PeriodService.class);
	    private final PeriodManager manager;
	    private Workspace workspace;

	    @Inject
	    public PeriodService(PeriodManager manager, Workspace workspace) {
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
	    @Path("{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response getById(@PathParam("id") long id)throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get period # " + id);
	        try {
	            return respondGet(manager.getById(workspace,id));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createPeriod(Period newPeriod) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create new period  #"
	                + newPeriod.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newPeriod), "\\periods");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\periods", 500);
	        }
	    }

	    @PUT
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response updatePeriod(Period updatePeriod) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to update period with this id #" + updatePeriod.getId() + " with description # "
	                + updatePeriod.getDescription());
	        try {
	            return respondPut(manager.update(workspace, updatePeriod));
	        } catch (SQLException ex) {
	            return respondPut(ex.getMessage(), 500);
	        }
	    }

	    @DELETE
	    @Path("/{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deletePeriod(@PathParam("id") long id)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete period with this id #" + id);
	        try {
	            return respondDelete(manager.deleteById(workspace, id));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }
	    
	    @GET
	    @Path("/types")
	    @Produces(APPLICATION_JSON)
	    public Response getAllPeriodTypes()throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all period types");
	        try {
	            return respondGet(manager.getAllPeriodType(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

}
