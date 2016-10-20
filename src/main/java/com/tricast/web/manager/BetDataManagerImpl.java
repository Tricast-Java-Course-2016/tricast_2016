package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.BetData;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.BetDataDao;

public class BetDataManagerImpl implements BetDataManager {

    private final BetDataDao betDataDao;

    @Inject
    public BetDataManagerImpl(BetDataDao betDataDao) {
        this.betDataDao = betDataDao;
    }

    @Override
    @JdbcTransaction
    public List<BetData> getAll(Workspace workspace) throws SQLException, IOException {
        return betDataDao.getAll(workspace);
    }

    @Override
    @JdbcTransaction
    public BetData getById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException {
        return betDataDao.getById(workspace, betId, outcomeId);
    }

    @Override
    @JdbcTransaction
    public BetData create(Workspace workspace, BetData newBetData) throws SQLException, IOException {
        Long[] id = betDataDao.create(workspace, newBetData);
        if (id != null) {
            return betDataDao.getById(workspace, id[0], id[1]);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public BetData update(Workspace workspace, BetData updateBetData) throws SQLException, IOException {
        Long[] id = betDataDao.update(workspace, updateBetData);
        if (id != null) {
            return betDataDao.getById(workspace, id[0], id[1]);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException {
        return betDataDao.deleteById(workspace, betId, outcomeId);
    }

}
