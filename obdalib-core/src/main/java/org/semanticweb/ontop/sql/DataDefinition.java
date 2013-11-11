/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.semanticweb.ontop.sql.api.Attribute;

public abstract class DataDefinition implements Serializable {

	private static final long serialVersionUID = 212770563440334334L;

	protected String name;

	protected HashMap<Integer, Attribute> attributes = new HashMap<Integer, Attribute>();

	public DataDefinition() { // TODO Remove later! The attribute name should be mandatory and cannot be changed!
		// NO-OP
	}
	
	public DataDefinition(String name) {
		this.name = name;
	}

	public void setName(String name) { // TODO Remove later! The attribute name should be mandatory and cannot be changed!
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAttribute(int pos, Attribute value) {
		attributes.put(pos, value);
	}

	public String getAttributeName(int pos) {
		Attribute attribute = attributes.get(pos);
		return attribute.getName();
	}

	public Attribute getAttribute(int pos) {
		Attribute attribute = attributes.get(pos);
		return attribute;
	}

	public ArrayList<Attribute> getAttributes() {
		ArrayList<Attribute> list = new ArrayList<Attribute>();
		for (Attribute value : attributes.values()) {
			list.add(value);
		}
		return list;
	}

	public int getAttributePosition(String attributeName) {
		int index = 0;
		for (Attribute value : attributes.values()) {
			if (value.hasName(attributeName)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int countAttribute() {
		return attributes.size();
	}
}
