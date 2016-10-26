package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tricast.beans.Event;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class EventDaoImpl implements EventDao {

	private static final SqlManager sqlManager = SqlManager.getInstance();
	private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(CountryDao.class);


	@Override
	public List<Event> getAll(Workspace workspace) throws SQLException, IOException {
		// TODO Auto-generated method stub
		List<Event> result = new ArrayList<Event>();
		String sql = sqlManager.get("eventGetAll.sql");
		try (PreparedStatement preparedStatement = workspace.getPreparedStatement(sql);ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				result.add(buildEvent(rs));
			}
		} catch(SQLException ex) {
	    	logger.error(ex,ex);
	    }
	    return result;
	}

	private Event buildEvent(ResultSet rs) throws SQLException {
		int c = 1;
		Event result = new Event();
		result.setId(rs.getLong(c++));
		result.setLeagueId(rs.getLong(c++));
		result.setCountryId(rs.getLong(c++));
		result.setHomeTeamId(rs.getLong(c++));
		result.setAwayTeamId(rs.getLong(c++));
		result.setDescription(rs.getString(c++));
		result.setStatus(rs.getString(c++));
		return result;
	}

	@JdbcTransaction
	@Override
	public Event getById(Workspace workspace, long id) throws SQLException, IOException {
		Event result = null;

		String sql = sqlManager.get("eventGetById.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql) ; ResultSet rs = ps.executeQuery()) {
			result = buildEvent(rs);
		}
		catch(SQLException ex) {
		   logger.error(ex,ex);
		}
		return result ;
	}


	@JdbcTransaction
	@Override
	public Long create(Workspace workspace, Event event) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("eventCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, event.getId());
            ps.setLong(i++, event.getLeagueId());
            ps.setLong(i++, event.getHomeTeamId());
            ps.setLong(i++, event.getAwayTeamId());
            ps.setString(i++, event.getDescription());
            ps.setString(i++, event.getStatus());
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

	@JdbcTransaction
	@Override
	public Long update(Workspace workspace, Event event) throws SQLException, IOException {

		 Long result = null;
	        ResultSet rs = null;

	        String sql = sqlManager.get("eventUpdate.sql");

	        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            int i = 1;
	            ps.setLong(i++, event.getId());
	            ps.setLong(i++, event.getLeagueId());
	            ps.setLong(i++, event.getHomeTeamId());
	            ps.setLong(i++, event.getAwayTeamId());
	            ps.setString(i++,event.getDescription());
	            ps.setString(i++,event.getStatus());
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

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		boolean result = false;

        String sql = sqlManager.get("eventDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, id);

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
