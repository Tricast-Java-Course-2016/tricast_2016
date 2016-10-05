package com.tricast.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class WorkspaceImpl implements Workspace {

	private final Logger logger = LogManager.getLogger(WorkspaceImpl.class);

	private DataSource ds;

	private Connection databaseConnection;
	private int commitCounter = 0;

	@Inject
	public WorkspaceImpl(DataSource dataSource) {
		try {
			ds = dataSource;
			databaseConnection = ds.getConnection();
			databaseConnection.setAutoCommit(false);
		} catch (SQLException ex) {
			logger.error("Could not get database connection: " + ex.getMessage());
		}
	}

	@Override
	public void startSession() {
		commitCounter++;
	}

	@Override
	public void commit() throws SQLException {
		commitCounter--;
		if (commitCounter == 0) {
			databaseConnection.commit();
			databaseConnection.close();
		}
	}

	@Override
	public void rollback() throws SQLException {
		if (commitCounter > 0) {
			commitCounter = 0;
			databaseConnection.rollback();
			databaseConnection.close();
		}
	}

	@Override
	public Connection getDatabaseConnection() {
		return databaseConnection;
	}

	@Override
	public void setDatabaseConnection(Connection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	@Override
	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return databaseConnection.prepareStatement(sql);
	}

	@Override
	public PreparedStatement getPreparedStatement(String sql, int mode) throws SQLException {
		return databaseConnection.prepareStatement(sql, mode);
	}
}
