package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Account;
import com.tricast.database.Workspace;
import com.tricast.web.dao.AccountDao;

public class AccountManagerImpl implements AccountManager {

	private final AccountDao accountDao;
	@Inject
    public AccountManagerImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

    @Override
    public List<Account> getAll(Workspace workspace) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getById(Workspace workspace, long id) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account create(Workspace workspace, Account newAccount) throws SQLException, IOException {
    	Long id = accountDao.create(workspace, newAccount);
		if (id != null) {
			return accountDao.getById(workspace, id);
		} else {
			return null;
		}
    }

    @Override
    public Account update(Workspace workspace, Account updateAccount) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Account login(Workspace workspace, String username, String password) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }



}
