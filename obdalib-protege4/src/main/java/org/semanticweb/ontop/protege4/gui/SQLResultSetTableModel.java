/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.protege4.gui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class SQLResultSetTableModel implements TableModel {

	private ResultSet results;
	private ResultSetMetaData rsMetadata;
	private int numcols;
	private int numrows;
	private int fetchSizeLimit;

	// Vector data for presenting result to table GUI
	private Vector<String[]> resultsTable;
	
	private Vector<TableModelListener> listener;

	private final int INITIAL_FETCH_SIZE = 100;
	private final int NEXT_FETCH_SIZE = 100;
	
	public SQLResultSetTableModel(ResultSet results, int fetchSizeLimit) throws SQLException {
		this.results = results;
		this.fetchSizeLimit = fetchSizeLimit;

		rsMetadata = results.getMetaData();
		numcols = rsMetadata.getColumnCount();
		numrows = 0;
		
		resultsTable = new Vector<String[]>();
		listener = new Vector<TableModelListener>();
		
		int fetchSize = fetchSizeLimit;
		if (needFetchMore()) {
			fetchSize = INITIAL_FETCH_SIZE;
		}
		fetchRows(fetchSize);
	}
	
	private void fetchRows(int size) throws SQLException {
		if (results == null) {
			return;
		}
		if (size != 0) {
			int counter = 0;
			while (results.next()) {
				String[] field = new String[numcols];
				for (int j = 0; j < numcols; j++) {
					field[j] = results.getString(j+1);
				}
				resultsTable.add(field);
				counter++;
				updateRowCount();
				
				// Determine if the loop should stop now
				if (counter == size) {
					break;
				}
			}
		}
	}

	private void updateRowCount() {
		numrows++;
	}

	/** Automatically close when we're garbage collected */
	@Override
	protected void finalize() {
		try {
			results.close();
		} catch (SQLException e) {
			// NO-OP
		}
	}

	@Override
	public Class<String> getColumnClass(int column) {
		return String.class;
	}
	
	@Override
	public int getColumnCount() {
		return numcols;
	}

	@Override
	public String getColumnName(int columnIndex) {
		try {
			return rsMetadata.getColumnLabel(columnIndex + 1);
		} catch (SQLException e) {
			// NO-OP
		}
		return "<UNKNOWN>";
	}

	@Override
	public int getRowCount() {
		return numrows;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (needFetchMore()) {
			checkNextRowAvailability(row);
		}
		String value = resultsTable.get(row)[column];
		if (value == null) {
			return "";
		}
		return value;
	}

	/**
	 * Determine if the table need to fetch more tuples.
	 */
	public boolean needFetchMore() {
		return fetchSizeLimit > INITIAL_FETCH_SIZE;
	}
	
	private void checkNextRowAvailability(int currentRowNumber) {
		try {
			int nextRowNumber = currentRowNumber + getRowCount() / 4;
			if (nextRowNumber >= getRowCount()) {
				int remainder = fetchSizeLimit - getRowCount();
				int c = remainder / NEXT_FETCH_SIZE;
				if (c != 0) {
					fetchRows(NEXT_FETCH_SIZE);
				} else {
					fetchRows(remainder);
				}
				fireModelChangedEvent();
			}
		} catch (SQLException e) {
			// NO-OP
		}
	}
	
	// The table cannot be edited
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void setValueAt(Object value, int row, int column) {
		// NO-OP
	}

	public void addTableModelListener(TableModelListener l) {
		listener.add(l);
	}

	public void removeTableModelListener(TableModelListener l) {
		listener.remove(l);
	}

	private void fireModelChangedEvent() {
		Iterator<TableModelListener> it = listener.iterator();
		while (it.hasNext()) {
			it.next().tableChanged(new TableModelEvent(this));
		}
	}
}
