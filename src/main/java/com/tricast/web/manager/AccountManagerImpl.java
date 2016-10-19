package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Account;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.AccountDao;

public class AccountManagerImpl implements AccountManager {
	private final AccountDao accountDao;

	@Inject
    public AccountManagerImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

    @Override
    @JdbcTransaction
    public List<Account> getAll(Workspace workspace) throws SQLException, IOException {
        return accountDao.getAll(workspace);
    }

    @Override
    @JdbcTransaction
    public Account getById(Workspace workspace, long id) throws SQLException, IOException {
        Account account = accountDao.getById(workspace, id);
        return account;
    }

    @Override
    @JdbcTransaction
    public Account create(Workspace workspace, Account newAccount) throws SQLException, IOException {
    	Long id = accountDao.create(workspace, newAccount);
		if (id != null) {
			return accountDao.getById(workspace, id);
		} else {
			return null;
		}
    }

    @Override
    @JdbcTransaction
    public Account update(Workspace workspace, Account updateAccount) throws SQLException, IOException {
        Long id = accountDao.update(workspace, updateAccount);
        if (id != null) {
            return accountDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        return accountDao.deleteById(workspace, Id);
    }

    @Override
    @JdbcTransaction
    public Account login(Workspace workspace, String username, String password) throws SQLException, IOException {
        Account account = accountDao.login(workspace, username, password);
        if (account == null) {
            throw new SQLException("No account exists with the specified username:" + username);
        }
        return account;
    }
}
