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
import com.tricast.beans.Team;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.filters.AuthenticationFilter;
import com.tricast.web.manager.TeamManager;

@Path("/teams")
@ResourceFilters(AuthenticationFilter.class)
public class TeamService extends LVSResource {

    private static final Logger log = LogManager.getLogger(TeamService.class);
	private final TeamManager manager;
	private Workspace workspace;

	@Inject
    public TeamService(TeamManager manager, Workspace workspace) {
			this.manager = manager;
			this.workspace = workspace;
		}

	@GET
	@Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get teams");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	@GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getById(@PathParam("id") long id)
	            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get Team # " + id);
	        try {
	            return respondGet(manager.getById(workspace,id));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createTeam(Team newTeam) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create new team with this id #" + newTeam.getId() + "and this decription #"
	                + newTeam.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newTeam), "\\teams");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\teams", 500);
	        }
	    }

	    @PUT
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response updateTeam(Team updateTeam) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to update Team with this teamId #" + updateTeam.getId() + "and this teamDescription #"
	                + updateTeam.getDescription());
	        try {
	            return respondPut(manager.update(workspace, updateTeam));
	        } catch (SQLException ex) {
	            return respondPut(ex.getMessage(), 500);
	        }
	    }

	    @DELETE
	    @Path("{id}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deleteTeam(@PathParam("id") long id)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete team with this id #" + id);
	        try {
	            return respondDelete(manager.deleteById(workspace, id));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }

}
