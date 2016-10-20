package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tricast.beans.Transaction;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class TransactionDaoImpl implements TransactionDao {

    private static final Logger log = LogManager.getLogger(TransactionDaoImpl.class);
    private static final SqlManager sqlManager = SqlManager.getInstance();

    @Override
    @JdbcTransaction
    public List<Transaction> getAll(Workspace workspace) throws SQLException, IOException {

        List<Transaction> result = new ArrayList<Transaction>();

        String sql = sqlManager.get("transactionsGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(buildTransaction(rs));
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

    private Transaction buildTransaction(ResultSet rs) throws SQLException {
        Transaction trans = new Transaction();
        int i = 1;
        trans.setId(rs.getLong(i++));
        trans.setAccountId(rs.getLong(i++));
        trans.setBetId(rs.getLong(i++));
        trans.setCreatedDate(rs.getDate(i++));
        trans.setDescription(rs.getString(i++));
        trans.setAmount(rs.getDouble(i++));
        return trans;
    }

    @Override
    @JdbcTransaction
    public Transaction getById(Workspace workspace, long id) throws SQLException, IOException {
        Transaction result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("transactionsGetById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildTransaction(rs);
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Long create(Workspace workspace, Transaction newItem) throws SQLException, IOException {

        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("transactionsCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getAccountId());
            ps.setLong(i++, newItem.getBetId());

            ps.setDate(i++, newItem.getCreatedDate());
            // System.out.print("" + newItem.getCreatedDate() + newItem.getCreatedDate());
            ps.setString(i++, newItem.getDescription());
            ps.setDouble(i++, newItem.getAmount());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Long update(Workspace workspace, Transaction updateItem) throws SQLException, IOException {

        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("transactionsUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setLong(i++, updateItem.getAccountId());
            ps.setLong(i++, updateItem.getBetId());
            ps.setDate(i++, updateItem.getCreatedDate());
            ps.setString(i++, updateItem.getDescription());
            ps.setDouble(i++, updateItem.getAmount());
            ps.setLong(i++, updateItem.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long id) throws SQLException, IOException {

        boolean result = false;

        String sql = sqlManager.get("transactionsDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

}
