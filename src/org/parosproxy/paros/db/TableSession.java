/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.parosproxy.paros.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableSession extends AbstractTable {

	private static final String SESSIONID = "SESSIONID";
	private static final String SESSIONNAME = "SESSIONNAME";
	private static final String LASTACCESS = "LASTACCESS";

	private PreparedStatement psReadDate = null;
	private PreparedStatement psReadAll = null;
	private PreparedStatement psInsert = null;
	private PreparedStatement psUpdate = null;

	public TableSession() {
		super();
	}

	protected void reconnect(Connection conn) throws SQLException {
		psReadDate = conn.prepareStatement("SELECT * FROM SESSION WHERE " + LASTACCESS + " < ?");
		psReadAll = conn.prepareStatement("SELECT * FROM SESSION");
		psInsert = conn.prepareStatement("INSERT INTO SESSION (" + SESSIONID + "," + SESSIONNAME + ") VALUES (?, ?)");
		psUpdate = conn.prepareStatement("UPDATE SESSION SET " + SESSIONNAME + " = ?," + LASTACCESS + " = NOW " + "WHERE " + SESSIONID + " = ?");
	}

	public synchronized void insert(long sessionId, String sessionName)
			throws SQLException {
		psInsert.setLong(1, sessionId);
		psInsert.setString(2, sessionName);
		psInsert.executeUpdate();
	}

	public synchronized void update(long sessionId, String sessionName)
			throws SQLException {

		psUpdate.setLong(2, sessionId);
		psUpdate.setString(1, sessionName);
		psUpdate.executeUpdate();

	}
}
