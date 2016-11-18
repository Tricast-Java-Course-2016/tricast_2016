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

import com.tricast.beans.Transaction;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
//import com.tricast.web.manager.BetManager;
import com.tricast.web.manager.TransactionManager;

@Path("/transactions")
public class TransactionService extends LVSResource {
    private static final Logger log = LogManager.getLogger(TransactionService.class);

    private final TransactionManager manager;

    private Workspace workspace;

    @Inject
    public TransactionService(TransactionManager manager, Workspace workspace) {
        this.manager = manager;
        this.workspace = workspace;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get all transactions");
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
        log.trace("Requested to get transaction ID = " + id);
        try {
            return respondGet(manager.getById(workspace, id));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }
    
    @GET
    @Path("balance/{id}")
    @Produces(APPLICATION_JSON)
    public Response getAmountByAccountId(@PathParam("id") long accountId) throws SQLException, OutOfTransactionException, IOException {
        log.trace("Requested to get current balance for accountId = " + accountId);
        try {
            return respondGet(manager.getAmountByAccountId(workspace, accountId));
        } catch (SQLException ex) {

            return respondGet(ex.getMessage(), 500);
        }
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response createTransaction(Transaction newTransaction) throws OutOfTransactionException, IOException {
        log.trace("Trying to create new transaction #" + newTransaction.getId());
        try {
            return respondPost(manager.create(workspace, newTransaction), "\\transactions");
        } catch (SQLException ex) {
            return respondPost(ex.getMessage(), "\\transactions", 500);
        }
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response updateTransaction(Transaction updateTransaction)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to update transaction #" + updateTransaction.getId());
        try {
            return respondPut(manager.update(workspace, updateTransaction));
        } catch (SQLException ex) {
            return respondPut(ex.getMessage(), 500);
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response deleteTransaction(@PathParam("id") long id)
            throws SQLException, OutOfTransactionException, IOException {
        log.trace("Trying to delete transaction #" + id);
        try {
            return respondDelete(manager.deleteById(workspace, id));
        } catch (SQLException ex) {
            return respondDeleteNotOK(ex.getMessage(), null, 500);
        }
    }
}
