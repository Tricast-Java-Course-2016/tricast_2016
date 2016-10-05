package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Account;
import com.tricast.database.Workspace;

public interface AccountDao {

	List<Account> getAll(Workspace workspace) throws SQLException, IOException;

	Account getById(Workspace workspace, long id) throws SQLException, IOException;

	Account login(Workspace workspace, String username, String password) throws SQLException, IOException;

	Long create(Workspace workspace, Account newItem) throws SQLException, IOException;

	Long update(Workspace workspace, Account updateItem) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;

}
