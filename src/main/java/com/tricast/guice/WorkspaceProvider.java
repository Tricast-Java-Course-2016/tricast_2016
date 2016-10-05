package com.tricast.guice;

import javax.sql.DataSource;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.tricast.database.WorkspaceImpl;

public class WorkspaceProvider implements Provider<WorkspaceImpl> {

	private DataSource ds;

	@Inject
	public WorkspaceProvider(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public WorkspaceImpl get() {
		WorkspaceImpl workspace = new WorkspaceImpl(ds);
		return workspace;
	}

}
