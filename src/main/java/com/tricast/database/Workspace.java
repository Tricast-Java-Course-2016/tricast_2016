package com.tricast.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Workspace {

	void startSession();

	public void commit() throws SQLException;

	public void rollback() throws SQLException;

	public Connection getDatabaseConnection();

	public void setDatabaseConnection(Connection databaseConnection);

	PreparedStatement getPreparedStatement(String sql) throws SQLException;

	PreparedStatement getPreparedStatement(String sql, int mode) throws SQLException;
}
