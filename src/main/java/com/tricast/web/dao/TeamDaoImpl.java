package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.tricast.beans.Team;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class TeamDaoImpl implements TeamDao {

	private static final SqlManager sqlManager = SqlManager.getInstance();
	private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(CountryDao.class);


	@Override
	public List<Team> getAll(Workspace workspace) throws SQLException, IOException {
		List<Team> result = new ArrayList<Team>();
		String sql = sqlManager.get("teamGetAll.sql");
		try (PreparedStatement preparedStatement = workspace.getPreparedStatement(sql);ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				result.add(buildTeam(rs));
			}
		} catch(SQLException ex) {
	    	logger.error(ex,ex);
	    }
	    return result;
	}

	private Team buildTeam(ResultSet rs) throws SQLException {
		Team result = new Team();
		int c = 1;
		result.setId(rs.getLong(c++));
		result.setDescription(rs.getString(c++));
		return result;
	}


	@Override
	public Team getById(Workspace workspace, long id) throws SQLException, IOException {
		Team result = null;	
		String sql = sqlManager.get("teamGetById.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql) ; ResultSet rs = ps.executeQuery()) {
			result = buildTeam(rs);
		}
		catch(SQLException ex) {
		   logger.error(ex,ex);
		}
		return result ;
	}

	@JdbcTransaction
	@Override
	public Long create(Workspace workspace, Team team) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("teamCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, team.getId());
            ps.setString(i++, team.getDescription());
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
	public Long update(Workspace workspace, Team team) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("teamUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setLong(i++, team.getId());
            ps.setString(i++,team.getDescription()); 
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
	@JdbcTransaction
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		boolean result = false;
        String sql = sqlManager.get("teamDelete.sql");
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
