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
import com.tricast.beans.Outcome;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.filters.AuthenticationFilter;
import com.tricast.web.manager.OutcomeManager;

@Path("/outcomes")
@ResourceFilters(AuthenticationFilter.class)
public class OutcomeService extends LVSResource {
    private static final Logger log = LogManager.getLogger(OutcomeService.class);

    private final OutcomeManager manager;

    private Workspace workspace;

    @Inject
    public OutcomeService(OutcomeManager manager, Workspace workspace) {
        this.manager = manager;
        this.workspace = workspace;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get All");
        try {
            return respondGet(manager.getAll(workspace));
        } catch (SQLException ex) {
            return respondGet(ex.getMessage(), 500);
        }
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get ID = " + id);
        try {
            return respondGet(manager.getById(workspace, id));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response createOutcome(Outcome newOutcome) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Creating Outcome with the following code: " + newOutcome.getOutcomecode());
        try {
            return respondPost(manager.create(workspace, newOutcome), "\\outcomes");
        } catch (SQLException ex) {
            return respondPost(ex.getMessage(), "\\outcomes", 500);
        }
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response updateOutcome(Outcome updateOutcome) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to update outcome for " + updateOutcome.getId());
        try {
            return respondPut(manager.update(workspace, updateOutcome));
        } catch (SQLException ex) {
            return respondPut(ex.getMessage(), 500);
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response deleteOutcome(@PathParam("id") long id)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to delete outcome with id #" + id);
        try {
            return respondDelete(manager.deleteById(workspace,id));
        } catch (SQLException ex) {
            return respondDeleteNotOK(ex.getMessage(), null, 500);
        }
    }
    
    @GET
    @Path("/markets/{id}")
    @Produces(APPLICATION_JSON)
    public Response getByMarketId(@PathParam("id") long marketId) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get outcomes with this marketId = " + marketId);
        try {
            return respondGet(manager.getByMarketId(workspace, marketId));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }
}
