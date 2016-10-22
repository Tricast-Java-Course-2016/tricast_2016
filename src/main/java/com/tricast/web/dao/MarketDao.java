package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Market;
import com.tricast.database.Workspace;

public interface MarketDao {
	List<Market> getAll(Workspace workspace) throws SQLException, IOException;

	Market getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, Market newItem) throws SQLException, IOException;

	Long update(Workspace workspace, Market updateItem) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;


}
