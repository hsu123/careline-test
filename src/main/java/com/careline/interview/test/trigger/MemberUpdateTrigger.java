package com.careline.interview.test.trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.api.Trigger;
import org.h2.util.StringUtils;

public class MemberUpdateTrigger implements Trigger {

	@Override
	public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type)
			throws SQLException {
	}

	@Override
	public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
		try (PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO member_log (content, modify_time) " + "VALUES (?, NOW())")) {

			String oldName = "";
			String newName = "";
			String oldPassword = "";
			String newPassword = "";

			if (newRow != null && oldRow != null) {
				oldName = (String) oldRow[2];
				newName = (String) newRow[2];
				oldPassword = (String) oldRow[3];
				newPassword = (String) newRow[3];
			}

			if(!StringUtils.equals(oldName, newName)) {
				stmt.setObject(1, "update name");
			}else if(!StringUtils.equals(oldPassword, newPassword)) {
				stmt.setObject(1, "update password");
			}else {
				stmt.setObject(1, "other updates");
			}
			
			stmt.executeUpdate();
		}
	}

	@Override
	public void close() throws SQLException {
	}

	@Override
	public void remove() throws SQLException {
	}
}