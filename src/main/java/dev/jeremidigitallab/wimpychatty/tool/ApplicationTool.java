package dev.jeremidigitallab.wimpychatty.tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.core.context.SecurityContextHolder;

import dev.jeremidigitallab.wimpychatty.config.security.CustomUserDetail;

public class ApplicationTool {

	public static String loadAccountIdFromSecurityContext() {
		CustomUserDetail securityUser = (CustomUserDetail) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		return securityUser.getAccount().getAccountId();
	}
	
	public static void closeDatabasePreparedConnection(Connection con,PreparedStatement ps,ResultSet rs) throws SQLException {
		
		if (rs != null) {
			rs.close();
		}
		
		if (ps != null) {
			ps.close();
		}
		
		if (con != null) {
			con.close();
		}
		
	}
	
}
