package com.tricast.web.server;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tricast.database.Workspace;
import com.tricast.database.WorkspaceImpl;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.AccountManager;

@Path("accounts")
public class AccountService extends LVSResource {

	private static final Logger log = LogManager.getLogger(AccountService.class);

	private final AccountManager manager;

	private Workspace workspace;

	@Inject
	public AccountService(AccountManager manager, WorkspaceImpl workspace) {
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



}
