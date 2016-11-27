package com.tricast.web.server;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

import com.auth0.jwt.JWTSigner;
import com.sun.jersey.spi.container.ResourceFilters;
import com.tricast.beans.Account;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.filters.AuthenticationFilter;
import com.tricast.web.filters.AuthenticationSettings;
import com.tricast.web.manager.AccountManager;
import com.tricast.web.request.LoginRequest;


@Path("/accounts")
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
	@ResourceFilters(AuthenticationFilter.class)
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
	@ResourceFilters(AuthenticationFilter.class)
	public Response getById(@PathParam("id") long id) throws SQLException, OutOfTransactionException, IOException {
		log.trace("Requested to get ID = " + id);
		try {
			return respondGet(manager.getById(workspace, id));
		} catch (SQLException ex) {

			return respondGet(ex.getMessage(), 500);
		}
	}
	
	@PUT
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	@ResourceFilters(AuthenticationFilter.class)
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
	@ResourceFilters(AuthenticationFilter.class)
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

	@POST
	@Path("/login")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response login(LoginRequest loginRequest) throws SQLException, OutOfTransactionException, IOException {
		log.trace("Trying to login with account for " + loginRequest.getUserName());
		try {
			Account account = manager.login(workspace, loginRequest.getUserName(), loginRequest.getPassword());
			String token = issueToken(account.getUserName());
			
			Map<String, Object> header = new HashMap<>();
			header.put("Authorization", token);	
				
			return respondPost(account, "\\accounts\\login", header);
		} catch (SQLException ex) {
			return respondPost(ex.getMessage(), "\\accounts\\login", 500);
		}
	}
	
    private String issueToken(String username) {   	
    	// issued at claim 
    	final long iat = System.currentTimeMillis() / 1000L; 
    	// expires claim. In this case the token expires in 10 min
    	final long exp = iat + AuthenticationSettings.EXPIRY_TIME_IN_SEC; 
    	
    	final JWTSigner signer = new JWTSigner(AuthenticationSettings.SECRET_KEY);
    	final HashMap<String, Object> claims = new HashMap<String, Object>();
    	claims.put("iss", AuthenticationSettings.ISSUER);
    	claims.put("exp", exp);
    	claims.put("iat", iat);
    	claims.put("aud", username);

    	return signer.sign(claims);
    }
}
