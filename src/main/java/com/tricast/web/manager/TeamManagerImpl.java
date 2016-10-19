package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Team;
import com.tricast.database.Workspace;
import com.tricast.web.dao.TeamDao;

public class TeamManagerImpl implements TeamManager {

	private final TeamDao teamDao;
	@Inject
	public TeamManagerImpl(TeamDao teamdao) {
		this.teamDao = teamdao ;
	}
	@Override
	public List<Team> getAll(Workspace workspace) throws SQLException, IOException {
		List<Team> list;
		list = this.teamDao.getAll(workspace);
		return list;
	}

	@Override
	public Team getById(Workspace workspace, long id) throws SQLException, IOException {
		Team team;
		team = this.teamDao.getById(workspace, id);
		return team;
	}

	@Override
	public Long create(Workspace workspace,Team team) throws SQLException, IOException {
		Long l = this.teamDao.create(workspace, team);
		return l;
	}

	@Override
	public Long update(Workspace workspace,Team team) throws SQLException, IOException {
		long l = this.teamDao.update(workspace, team);
		return l;
	}

	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		boolean b = this.teamDao.deleteById(workspace, Id);
		return b;
	}
}
