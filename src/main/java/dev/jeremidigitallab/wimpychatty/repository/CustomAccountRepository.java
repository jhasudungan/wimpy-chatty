package dev.jeremidigitallab.wimpychatty.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import dev.jeremidigitallab.wimpychatty.entity.Account;
import dev.jeremidigitallab.wimpychatty.model.ConnectedAccount;
import dev.jeremidigitallab.wimpychatty.tool.ApplicationTool;

@Repository
public class CustomAccountRepository {
	
	@Value("${pagination.limit}")
	private int paginationLimit;
	
	@Autowired
	DataSource dataSource;

	public Integer countAccountFriend(String accountId) throws SQLException {
		
		Integer result = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT COUNT(account_id) AS total_friend "
				+ "FROM (SELECT ac2.account_id "
				+ "FROM account ac1 "
				+ "INNER JOIN account_friend af "
				+ "ON ac1.account_id = af.target_id "
				+ "INNER JOIN account ac2 "
				+ "ON af.source_id = ac2.account_id "
				+ "WHERE af.target_id = ? "
				+ "UNION ALL "
				+ "SELECT ac2.account_id "
				+ "FROM account ac1 "
				+ "INNER JOIN account_friend af "
				+ "ON ac1.account_id = af.source_id "
				+ "INNER JOIN account ac2 "
				+ "ON af.target_id = ac2.account_id "
				+ "WHERE af.source_id = ? ) AS account_friend";
		
		try {
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);
			ps.setString(2, accountId);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				result = rs.getInt("total_friend");
			}
			
		} finally {
			ApplicationTool.closeDatabasePreparedConnection(con, ps, rs);
		}
		
		
		return result;
	}
	
	public List<ConnectedAccount> getAccountFriend(String accountId,int page) throws SQLException {
				
		List<ConnectedAccount> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
			
		page = (page-1)*paginationLimit;
		
		String sql = "SELECT * "
				+ "FROM ( "
				+ "SElECT af.created_date AS connection_created_date, af.connected_id AS connection_id, ac2.account_id, ac2.account_display_name, '1' AS connection_status "
				+ "FROM account ac1 "
				+ "INNER JOIN account_friend af ON ac1.account_id = af.target_id "
				+ "INNER JOIN account ac2 ON af.source_id = ac2.account_id "
				+ "WHERE af.target_id = ? "
				+ "UNION ALL "
				+ "SELECT af.created_date AS connection_created_date, af.connected_id AS connection_id, ac2.account_id, ac2.account_display_name, '2' AS connection_status "
				+ "FROM account ac1 "
				+ "INNER JOIN account_friend af ON ac1.account_id = af.source_id "
				+ "INNER JOIN account ac2 ON af.target_id = ac2.account_id "
				+ "WHERE af.source_id = ? "
				+ ") AS account_friend "
				+ "ORDER BY connection_created_date DESC "
				+ "LIMIT ? "
				+ "OFFSET ?";
		
		try {
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);
			ps.setString(2, accountId);
			ps.setInt(3, paginationLimit);
			ps.setInt(4, page);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				ConnectedAccount connectedAccount = new ConnectedAccount();
				connectedAccount.setConnectionId(rs.getString("connection_id"));
				connectedAccount.setConnectionDate(rs.getString("connection_created_date"));
				connectedAccount.setAccountId(rs.getString("account_id"));
				connectedAccount.setAccountDisplayName(rs.getString("account_display_name"));
				connectedAccount.setConnectionStatus(rs.getString("connection_status"));
				
				result.add(connectedAccount);
			}
			
		} finally {
			ApplicationTool.closeDatabasePreparedConnection(con, ps, rs);
		}
						
		return result;
	}
	
	public List<ConnectedAccount> findConnectedAccountByName(String accountId,String query) throws SQLException {
		
		List<ConnectedAccount> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		query = "%"+query+"%";
		
		try {
			
			String sql = "SELECT * "
					+ "FROM ( "
					+ "SElECT af.created_date AS connection_created_date, af.connected_id AS connection_id, ac2.account_id, ac2.account_display_name, '1' AS connection_status "
					+ "FROM account ac1 "
					+ "INNER JOIN account_friend af ON ac1.account_id = af.target_id "
					+ "INNER JOIN account ac2 ON af.source_id = ac2.account_id "
					+ "WHERE af.target_id = ? "
					+ "UNION ALL "
					+ "SELECT af.created_date AS connection_created_date, af.connected_id AS connection_id, ac2.account_id, ac2.account_display_name, '2' AS connection_status "
					+ "FROM account ac1 "
					+ "INNER JOIN account_friend af ON ac1.account_id = af.source_id "
					+ "INNER JOIN account ac2 ON af.target_id = ac2.account_id "
					+ "WHERE af.source_id = ?) AS account_friend WHERE lower(account_display_name) LIKE lower(?)";
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);
			ps.setString(2, accountId);
			ps.setString(3, query);			
			rs = ps.executeQuery();
						
			while (rs.next()) {
				
				ConnectedAccount connectedAccount = new ConnectedAccount();
				connectedAccount.setConnectionId(rs.getString("connection_id"));
				connectedAccount.setConnectionDate(rs.getString("connection_created_date"));
				connectedAccount.setAccountId(rs.getString("account_id"));
				connectedAccount.setAccountDisplayName(rs.getString("account_display_name"));
				connectedAccount.setConnectionStatus(rs.getString("connection_status"));
				
				result.add(connectedAccount);
			}
			
		} finally {
			ApplicationTool.closeDatabasePreparedConnection(con, ps, rs);
		}
		
		result.forEach(data -> System.out.println(data.toString()));
		
		return result;
	}
	
	public List<Account> findNotConnectedAccountByName(String accountId,String query) throws SQLException {
		
		List<Account> result = new ArrayList<>();
				
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		query = "%"+query+"%";
		
		String sql = "SELECT * FROM account "
				+ "WHERE account_name LIKE ? "
				+ "AND account_id NOT IN "
				+ "(SELECT * FROM ( SELECT ? "
				+ "UNION ALL "
				+ "SELECT ac2.account_id "
				+ "FROM account ac1 "
				+ "INNER JOIN account_friend af "
				+ "ON ac1.account_id = af.target_id "
				+ "INNER JOIN account ac2 "
				+ "ON af.source_id = ac2.account_id "
				+ "WHERE af.target_id = ? "
				+ "UNION ALL "
				+ "SELECT ac2.account_id "
				+ "FROM account ac1 "
				+ "INNER JOIN account_friend af "
				+ "ON ac1.account_id = af.source_id "
				+ "INNER JOIN account ac2 "
				+ "ON af.target_id = ac2.account_id "
				+ "WHERE af.source_id = ?) AS ALREADY_CONNECTED_FRIEND )";
		
		try {
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, query);
			ps.setString(2, accountId);
			ps.setString(3, accountId);
			ps.setString(4, accountId);
		
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				Account account = new Account();
				account.setAccountId(rs.getString("ACCOUNT_ID"));
				account.setAccountDisplayName(rs.getString("ACCOUNT_DISPLAY_NAME"));
				account.setAccountLoginPassword("N/A");
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setCreatedDate(rs.getDate("CREATED_DATE"));
				account.setCreatedDate(rs.getDate("LAST_MODIFIED"));
				
				result.add(account);
			
			}
			
		} finally {
			ApplicationTool.closeDatabasePreparedConnection(con, ps, rs);
		}
		
		return result;
		
	}
}
