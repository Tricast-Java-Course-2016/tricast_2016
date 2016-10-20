package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tricast.beans.League;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;

public class LeagueDaoImpl implements LeagueDao {
	private static final SqlManager sqlManager = SqlManager.getInstance();
	private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(CountryDao.class);


	@Override
	public List<League> getAll(Workspace workspace) throws SQLException, IOException {
		// TODO Auto-generated method stub
		List<League> result = new ArrayList<League>();
		String sql = sqlManager.get("leaguegetall.sql");
		try (PreparedStatement preparedStatement = workspace.getPreparedStatement(sql);ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				result.add(buildLeague(rs));
			}
		} catch(SQLException ex) {
	    	logger.error(ex,ex);
	    }
	    return result;
	}

	@Override
	public League getById(Workspace workspace, long id) throws SQLException, IOException {

		League result = null;

		String sql = sqlManager.get("eventgetbyid.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql) ; ResultSet rs = ps.executeQuery()) {
			result = buildLeague(rs);
		}
		catch(SQLException ex) {
		   logger.error(ex,ex);
		}
		return result ;
	}

	private League buildLeague(ResultSet rs) throws SQLException {
		int c = 1;
		League league = new League();
		league.setId(rs.getLong(c++));
		league.setDescription(rs.getString(c++));
		return league;
	}

	@Override
	public Long create(Workspace workspace, League league) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("eventcreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, league.getId());
            ps.setString(i++, league.getDescription());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;

	}

	@Override
    // TODO Bemeneti változó nevét javitani
	public Long update(Workspace workspace, League leauge) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("leaguecreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, leauge.getId());
			ps.setString(i++, leauge.getDescription());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;

	}

	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		boolean result = false;

        String sql = sqlManager.get("eventdelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, Id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        }
        return result;
	}

}
