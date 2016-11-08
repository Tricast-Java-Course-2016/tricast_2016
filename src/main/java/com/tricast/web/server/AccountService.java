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

import com.tricast.beans.Account;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.AccountManager;
import com.tricast.web.request.LoginRequest;

@Path("accounts")
public class AccountService extends LVSResource {

	private static final Logger log = LogManager.getLogger(AccountService.class);

	private final AccountManager manager;

	private Workspace workspace;

	@Inject
	public AccountService(AccountManager manager, Workspace workspace) {
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
	public Response createAccount(Account newAccount) throws SQLException, OutOfTransactionException, IOException {
		log.trace("Trying to create new account for " + newAccount.getFirstName() + ' ' + newAccount.getLastName());
		try {
			return respondPost(manager.create(workspace, newAccount), "\\accounts");
		} catch (SQLException ex) {
			return respondPost(ex.getMessage(), "\\accounts", 500);
		}
	}
	
	@PUT
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response updateAccount(Account updateAccount) throws SQLException, OutOfTransactionException, IOException {
		log.trace("Trying to update account for " + updateAccount.getFirstName() + ' ' + updateAccount.getLastName());
		try {
			return respondPut(manager.update(workspace, updateAccount));
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
        log.trace("Trying to delete user with id #" + id);
        try {
            return respondDelete(manager.deleteById(workspace,id));
        } catch (SQLException ex) {
            return respondDeleteNotOK(ex.getMessage(), null, 500);
        }
    }

	@POST
	@Path("/login")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response login(LoginRequest loginRequest) throws SQLException, OutOfTransactionException, IOException {
		log.trace("Trying to login with account for " + loginRequest.getUserName());
		try {
			return respondPost(manager.login(workspace, loginRequest.getUserName(), loginRequest.getPassword()),
					"\\accounts\\login");
		} catch (SQLException ex) {
			return respondPost(ex.getMessage(), "\\accounts\\login", 500);
		}
	}

}
