package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Transaction;
import com.tricast.database.Workspace;

public interface TransactionManager {

    List<Transaction> getAll(Workspace workspace) throws SQLException, IOException;

    Transaction getById(Workspace workspace, long id) throws SQLException, IOException;

    Transaction create(Workspace workspace, Transaction newTransaction) throws SQLException, IOException;

    Transaction update(Workspace workspace, Transaction updateTransaction) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long transactionId) throws SQLException, IOException;
    
    double getAmountByAccountId(Workspace workspace, long accountId) throws SQLException, IOException;
}
