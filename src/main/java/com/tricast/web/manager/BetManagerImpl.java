package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Bet;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.BetDao;

public class BetManagerImpl implements BetManager {

    private final BetDao betDao;

    @Inject
    public BetManagerImpl(BetDao betDao) {
        this.betDao = betDao;
    }

    @Override
    @JdbcTransaction
    public List<Bet> getAll(Workspace workspace) throws SQLException, IOException {
        return betDao.getAll(workspace);
    }

    @Override
    @JdbcTransaction
    public Bet getById(Workspace workspace, long id) throws SQLException, IOException {
        return betDao.getById(workspace, id);
    }

    @Override
    @JdbcTransaction
    public Bet create(Workspace workspace, Bet newBet) throws SQLException, IOException {
        Long id = betDao.create(workspace, newBet);
        if (id != null) {
            return betDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public Bet update(Workspace workspace, Bet updateBet) throws SQLException, IOException {
        Long id = betDao.update(workspace, updateBet);
        if (id != null) {
            return betDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long betId) throws SQLException, IOException {
        return betDao.deleteById(workspace, betId);
    }

}
