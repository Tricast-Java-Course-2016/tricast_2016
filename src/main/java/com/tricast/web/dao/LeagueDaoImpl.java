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

import com.tricast.beans.League;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class LeagueDaoImpl implements LeagueDao {
	private static final Logger log = LogManager.getLogger(LeagueDaoImpl.class);
    private static final SqlManager sqlManager = SqlManager.getInstance();


	@Override
	public List<League> getAll(Workspace workspace) throws SQLException, IOException {
		List<League> result = new ArrayList<League>();
		String sql = sqlManager.get("leagueGetAll.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql);ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				result.add(buildLeague(rs));
			}
		} catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

	@Override
    @JdbcTransaction
	public League getById(Workspace workspace, long id) throws SQLException, IOException {
		League result = null;
		ResultSet rs = null;
		String sql = sqlManager.get("leagueGetById.sql");
		
		try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
			ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildLeague(rs);
            }
        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

	private League buildLeague(ResultSet rs) throws SQLException {
		int c = 1;
		League league = new League();
		league.setId(rs.getLong(c++));
		league.setDescription(rs.getString(c++));
		return league;
	}

	@JdbcTransaction
	@Override
	public Long create(Workspace workspace, League newItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;
        String sql = sqlManager.get("leagueCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setString(i++, newItem.getDescription());
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
	public Long update(Workspace workspace, League updateItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;
        String sql = sqlManager.get("leagueUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            
			ps.setString(i++, updateItem.getDescription());
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
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		boolean result = false;
        String sql = sqlManager.get("leagueDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            int i = 1;
            ps.setLong(i++, Id);
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
