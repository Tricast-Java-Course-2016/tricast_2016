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

import com.tricast.beans.Market;
import com.tricast.database.Workspace;
import com.tricast.database.WorkspaceImpl;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.MarketManager;

@Path("/markets")
public class MarketService extends LVSResource{
	
	private static final Logger log = LogManager.getLogger(MarketService.class);

    private final MarketManager manager;

    private Workspace workspace;

    @Inject
    public MarketService(MarketManager manager, WorkspaceImpl workspace) {
        this.manager = manager;
        this.workspace = workspace;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get all market");
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
        log.trace("Requested to get market ID = " + id);
        try {
            return respondGet(manager.getById(workspace, id));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response createMarket(Market newMarket) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to create new Market with the following ID: " + newMarket.getId());
        try {
            return respondPost(manager.create(workspace, newMarket), "\\markets");
        } catch (SQLException ex) {
            return respondPost(ex.getMessage(), "\\markets", 500);
        }
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response updateMarket(Market updateMarket) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to update Market #" + updateMarket.getId());
        try {
            return respondPut(manager.update(workspace, updateMarket));
        } catch (SQLException ex) {
            return respondPut(ex.getMessage(), 500);
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response deleteMarket(@PathParam("id") long id)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to delete market with id #" + id);
        try {
            return respondDelete(manager.deleteById(workspace,id));
        } catch (SQLException ex) {
            return respondDeleteNotOK(ex.getMessage(), null, 500);
        }
    }

}
