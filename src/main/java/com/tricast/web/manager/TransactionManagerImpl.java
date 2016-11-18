package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Transaction;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.TransactionDao;

public class TransactionManagerImpl implements TransactionManager {

    private final TransactionDao transactionDao;

    @Inject
    public TransactionManagerImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    @JdbcTransaction
    public List<Transaction> getAll(Workspace workspace) throws SQLException, IOException {
        return transactionDao.getAll(workspace);
    }

    @Override
    @JdbcTransaction
    public Transaction getById(Workspace workspace, long id) throws SQLException, IOException {
        return transactionDao.getById(workspace, id);
    }

    @Override
    @JdbcTransaction
    public Transaction create(Workspace workspace, Transaction newTransaction) throws SQLException, IOException {
        Long id = transactionDao.create(workspace, newTransaction);
        if (id != null) {
            return transactionDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public Transaction update(Workspace workspace, Transaction updateTransaction) throws SQLException, IOException {
        Long id = transactionDao.update(workspace, updateTransaction);
        if (id != null) {
            return transactionDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long transactionId) throws SQLException, IOException {
        return transactionDao.deleteById(workspace, transactionId);
    }
    
    @Override
    @JdbcTransaction
    public double getAmountByAccountId(Workspace workspace, long accountId) throws SQLException, IOException {
        return transactionDao.getAmountByAccountId(workspace, accountId);
    }

}
