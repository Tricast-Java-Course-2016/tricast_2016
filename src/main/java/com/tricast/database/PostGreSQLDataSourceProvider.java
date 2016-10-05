package com.tricast.database;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class PostGreSQLDataSourceProvider implements Provider<DataSource> {

	private final String datasourceName;
	private final String serverName;
	private final String portNumber;
	private final String databaseName;
	private final String user;
	private final String password;

	@Inject
	public PostGreSQLDataSourceProvider(@Named("datasourceName") final String datasourceName,
			@Named("serverName") final String serverName, @Named("portNumber") final String portNumber,
			@Named("databaseName") final String databaseName, @Named("user") final String user,
			@Named("password") final String password) {
		this.datasourceName = datasourceName;
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.databaseName = databaseName;
		this.user = user;
		this.password = password;
	}

	@Override
	public DataSource get() {
		PGPoolingDataSource dataSource = null;
		dataSource = new PGPoolingDataSource();
		dataSource.setDataSourceName(datasourceName);
		dataSource.setServerName(serverName);
		dataSource.setPortNumber(Integer.valueOf(portNumber));
		dataSource.setDatabaseName(databaseName);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		return dataSource;
	}
}
