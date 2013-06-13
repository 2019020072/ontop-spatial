package it.unibz.krdb.obda.owlrefplatform.core;

import it.unibz.krdb.obda.model.OBDAConnection;
import it.unibz.krdb.obda.model.OBDAException;

import java.sql.Connection;
import java.sql.SQLException;

/***
 * Quest connection is responsible for wrapping a JDBC connection to the data
 * source. It will translate calls to OBDAConnection into JDBC Connection calls
 * (in most cases directly).
 * 
 * @author mariano
 * 
 */
public class S_QuestConnection implements OBDAConnection {

	protected Connection conn;

	private S_Quest questinstance;
	
	private boolean isClosed;

	public S_QuestConnection(S_Quest questisntance, Connection connection) {
		this.questinstance = questisntance;
		this.conn = connection;
		isClosed = false;
	}

	@Override
	public void close() throws OBDAException {
		try {
			isClosed = true;
			this.conn.close();
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}

	}

	@Override
	public S_QuestStatement createStatement() throws OBDAException {
		try {
			if (!isClosed) {
				S_QuestStatement st = new S_QuestStatement(this.questinstance,
						this, conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
								java.sql.ResultSet.CONCUR_READ_ONLY));
				return st;
			} else
				throw new OBDAException("Connection was closed.");
		} catch (SQLException e) {
			// statement was closed, connection broken, recreate
			try {
				if (conn.isClosed()) {
					//recreate sql connection
					conn = questinstance.getSQLConnection();
					isClosed = false;
					//create statement again once
					S_QuestStatement st = new S_QuestStatement(this.questinstance,
							this, conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
									java.sql.ResultSet.CONCUR_READ_ONLY));
					return st;
				} else {
					OBDAException obdaException = new OBDAException(e.getMessage());
					obdaException.setStackTrace(e.getStackTrace());
					throw obdaException;
				}
			} catch (SQLException e1) {
				OBDAException obdaException = new OBDAException(e1.getMessage());
				obdaException.setStackTrace(e1.getStackTrace());
				throw obdaException;
			}
		}

	}

	@Override
	public void commit() throws OBDAException {
		try {
			conn.commit();
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}
	}

	@Override
	public void setAutoCommit(boolean autocommit) throws OBDAException {
		try {
			conn.setAutoCommit(autocommit);
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}

	}

	@Override
	public boolean getAutoCommit() throws OBDAException {
		try {
			return conn.getAutoCommit();
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}
	}

	@Override
	public boolean isClosed() throws OBDAException {
		try {
			isClosed = conn.isClosed();
			return isClosed;
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}
	}

	@Override
	public boolean isReadOnly() throws OBDAException {
		if (this.questinstance.dataRepository == null)
			return true;
		try {
			return conn.isReadOnly();
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}
	}

	@Override
	public void rollBack() throws OBDAException {
		try {
			conn.rollback();
		} catch (Exception e) {
			OBDAException obdaException = new OBDAException(e.getMessage());
			obdaException.setStackTrace(e.getStackTrace());
			throw obdaException;
		}
	}

}
