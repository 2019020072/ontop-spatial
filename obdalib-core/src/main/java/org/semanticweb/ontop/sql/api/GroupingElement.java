/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.sql.api;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupingElement implements Serializable {
	
	private static final long serialVersionUID = -6857110873541531968L;

	/**
	 * Collection of columns that is used for grouping.
	 */
	private ArrayList<ColumnReference> columnList;
	
	private boolean bAsGroup = false; // a single element or a group?
	
	public GroupingElement() {
		columnList = new ArrayList<ColumnReference>();
	}
	
	/**
	 * Inserts a column to this grouping element.
	 * 
	 * @param column
	 * 			A single column.
	 */
	public void add(ColumnReference column) {
		columnList.add(column);
		if (columnList.size() > 1) {
			bAsGroup = true;
		}
	}
	
	/**
	 * Updates the column list in this grouping element. Any 
	 * existing columns are going to be replaced by the given
	 * new list.
	 * 
	 * @param columnList
	 * 			The new column list.
	 */
	public void update(ArrayList<ColumnReference> columnList) {
		columnList.clear(); // remove any existing columns.
		columnList.addAll(columnList);
	}
	
	@Override
	public String toString() {
		String str = "";
		
		boolean bNeedComma = false;
		for (ColumnReference col : columnList) {
			if (bNeedComma) {
				str += ", ";
			}
			str += col.toString();
			bNeedComma = true;
		}
		
		if (bAsGroup) {
			str = String.format("(%s)", str);
		}
		return str;
	}
}
