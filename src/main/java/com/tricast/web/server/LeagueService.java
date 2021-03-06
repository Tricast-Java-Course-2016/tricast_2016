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
import com.tricast.beans.League;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.filters.AuthenticationFilter;
import com.tricast.web.manager.LeagueManager;

@Path("/leagues")
@ResourceFilters(AuthenticationFilter.class)
public class LeagueService extends LVSResource {

		private static final Logger log = LogManager.getLogger(LeagueService.class);
		
	    private final LeagueManager manager;
	    
	    private Workspace workspace;

	    @Inject
    public LeagueService(LeagueManager manager, Workspace workspace) {
			super();
			this.manager = manager;
			this.workspace = workspace;
		}

	    @GET
	    @Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all Leagues");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @GET
	    @Path("{league}")
	    @Produces(APPLICATION_JSON)

	    public Response getById(@PathParam("league") long id) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get leagueData # " + id);
	        try {
	            return respondGet(manager.getById(workspace,id));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createLeague(League newLeague) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create  league #" + newLeague.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newLeague), "\\leagues");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\leagues", 500);
	        }
	    }

	    @PUT
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response updateLeague(League updateLeague) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to update leaguedata with this leagueId #" + updateLeague.getId() + "and this leagueDescription #"
	                + updateLeague.getDescription());
	        try {
	            return respondPut(manager.update(workspace, updateLeague));
	        } catch (SQLException ex) {
	            return respondPut(ex.getMessage(), 500);
	        }
	    }

	    @DELETE
	    @Path("/{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deleteLeague(@PathParam("id") long id)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete leaguedata with this leagueId #" + id);
	        try {
	            return respondDelete(manager.deleteById(workspace, id));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }



}
