/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.sql.api;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class StringValueExpression extends AbstractValueExpression {

	private static final long serialVersionUID = 4563093796759540610L;

	public static final String CONCAT_OP = "||";
	
	/**
	 * Collection of custom string expressions.
	 */
	private Queue<Object> cache = new LinkedList<Object>();
	
	@Override
	public void putSpecification(Object obj) {
		cache.add(obj);
		if (obj instanceof ColumnReference) {
			super.add((ColumnReference)obj);
		}
	}

	public Queue<Object> getSpecification() {
		return cache;
	}
	
	/**
	 * Retrieves all the {@link ColumnReference} objects that
	 * are used in this expression.
	 */
	public ArrayList<ColumnReference> getColumns() {
		ArrayList<ColumnReference> columns = new ArrayList<ColumnReference>();
		for (Object obj : cache) {
			if (obj instanceof ColumnReference) {
				columns.add((ColumnReference)obj);
			}
		}
		return columns;
	}

	@Override
	public String toString() {
		String str = "(";
		for (Object obj : cache) {
			if (obj instanceof String) {
				if (!((String)obj).equals(CONCAT_OP)) {
					str += "'" + obj.toString() + "'"; // to delimit the string
				}
			}
			str += obj.toString();
		}
		str += ")";
			
		return str;
	}
}
