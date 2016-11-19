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

import com.tricast.beans.Bet;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.BetManager;

@Path("/bets")
public class BetService extends LVSResource {

    private static final Logger log = LogManager.getLogger(BetService.class);

    private final BetManager manager;

    private Workspace workspace;

    @Inject
    public BetService(BetManager manager, Workspace workspace) {
        this.manager = manager;
        this.workspace = workspace;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get all bets");
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
        log.trace("Requested to get bet ID = " + id);
        try {
            return respondGet(manager.getById(workspace, id));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }
    
    @GET
    @Path("details/{eventId}/{accountId}")
    @Produces(APPLICATION_JSON)
    public Response getBetInformation(@PathParam("eventId") long eventId, @PathParam("accountId") long accountId) 
    		throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get details for betting page with event ID = " + eventId +" account ID = "+ accountId);
        try {
            return respondGet(manager.getBetInformation(workspace, eventId, accountId));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response createBet(Bet newBet) throws OutOfTransactionException, IOException {
        log.trace("Trying to create new bet #" + newBet.getId());
        try {
            return respondPost(manager.create(workspace, newBet), "\\bets");
        } catch (SQLException ex) {
            return respondPost(ex.getMessage(), "\\bets", 500);
        }
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response updateBet(Bet updateBet) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to update bet #" + updateBet.getId());
        try {
            return respondPut(manager.update(workspace, updateBet));
        } catch (SQLException ex) {
            return respondPut(ex.getMessage(), 500);
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response deleteBet(@PathParam("id") long id)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to delete bet #" + id);
        try {
            return respondDelete(manager.deleteById(workspace, id));
        } catch (SQLException ex) {
            return respondDeleteNotOK(ex.getMessage(), null, 500);
        }
    }
}
