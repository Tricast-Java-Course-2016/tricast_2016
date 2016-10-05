package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Account;
import com.tricast.database.Workspace;

public interface AccountManager {

	List<Account> getAll(Workspace workspace) throws SQLException, IOException;

	Account getById(Workspace workspace, long id) throws SQLException, IOException;

	Account create(Workspace workspace, Account newAccount) throws SQLException, IOException;

	Account update(Workspace workspace, Account updateAccount) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;

	public Account login(Workspace workspace, String username, String password) throws SQLException, IOException;

}
