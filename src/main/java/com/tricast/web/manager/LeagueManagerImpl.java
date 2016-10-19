package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.League;
import com.tricast.database.Workspace;
import com.tricast.web.dao.LeagueDao;

public class LeagueManagerImpl implements LeagueManager {

	private final LeagueDao leagueDao;
	@Inject
	public LeagueManagerImpl(LeagueDao leaguedao) {
		this.leagueDao = leaguedao;
	}
	@Override
	public List<League> getAll(Workspace workspace) throws SQLException, IOException {
		List<League> list;
		list = this.leagueDao.getAll(workspace);
		return list;
	}

	@Override
	public League getById(Workspace workspace, long id) throws SQLException, IOException {
		League league;
		league = this.leagueDao.getById(workspace, id);
		return league;
	}

	@Override
	public Long create(Workspace workspace, League event) throws SQLException, IOException {
		Long l = this.leagueDao.create(workspace, event);
		return l;
	}

	@Override
	public Long update(Workspace workspace, League event) throws SQLException, IOException {
		long l = this.leagueDao.update(workspace, event);
		return l;
	}

	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		boolean b = this.leagueDao.deleteById(workspace, Id);
		return b;
	}

}
