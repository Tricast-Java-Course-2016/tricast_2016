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

//import com.tricast.beans.Bet;
import com.tricast.beans.BetData;
import com.tricast.database.Workspace;
import com.tricast.database.WorkspaceImpl;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.BetDataManager;

@Path("/betdata")
public class BetDataService extends LVSResource {

    private static final Logger log = LogManager.getLogger(BetDataService.class);

    private final BetDataManager manager;

    private Workspace workspace;

    @Inject
    public BetDataService(BetDataManager manager, WorkspaceImpl workspace) {
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
    @Path("/{betId}/{outcomeId}")
    @Produces(APPLICATION_JSON)
    public Response getById(@PathParam("betId") long betId, @PathParam("outcomeId") long outcomeId)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get betData betId = " + betId + "and outComeId = " + outcomeId);
        try {
            return respondGet(manager.getById(workspace, betId, outcomeId));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response createBetData(BetData newBetData) throws OutOfTransactionException, IOException {
        log.trace("Trying to create new betdata with this betId #" + newBetData.getBetId() + "and this outcomeId #"
                + newBetData.getOutcomeId());
        try {
            return respondPost(manager.create(workspace, newBetData), "\\betdata");
        } catch (SQLException ex) {
            return respondPost(ex.getMessage(), "\\betdata", 500);
        }
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response updateBetData(BetData updateBetData) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to update betdata with this betId #" + updateBetData.getBetId() + "and this outcomeId #"
                + updateBetData.getOutcomeId());
        try {
            return respondPut(manager.update(workspace, updateBetData));
        } catch (SQLException ex) {
            return respondPut(ex.getMessage(), 500);
        }
    }

    @DELETE
    @Path("/{betId}/{outcomeId}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response deleteBetData(@PathParam("betId") long betId, @PathParam("outcomeId") long outcomeId)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to delete betdata with this betId #" + betId + "and this outcomeId #" + outcomeId);
        try {
            return respondDelete(manager.deleteById(workspace, betId, outcomeId));
        } catch (SQLException ex) {
            return respondDeleteNotOK(ex.getMessage(), null, 500);
        }
    }

}
