/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.model.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.ontop.model.BNode;
import org.semanticweb.ontop.model.Variable;
import org.semanticweb.ontop.model.Predicate.COL_TYPE;

/**
 * Implementation for BNodes.
 */
public class BNodeConstantImpl extends AbstractLiteral implements BNode {

	private static final long serialVersionUID = 214867118996974157L;

	private final String name;

	private final int identifier;

	/**
	 * The default constructor.
	 * 
	 * @param uri
	 *            URI from a term.
	 */
	protected BNodeConstantImpl(String name) {
		this.name = name;
		this.identifier = name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BNodeConstantImpl)) {
			return false;
		}
		BNodeConstantImpl uri2 = (BNodeConstantImpl) obj;
		return this.identifier == uri2.identifier;
	}

	@Override
	public int hashCode() {
		return identifier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BNode clone() {
		return this;
	}

	@Override
	public String toString() {
		return TermUtil.toString(this);
	}

	@Override
	public Set<Variable> getReferencedVariables() {
		return new LinkedHashSet<Variable>();
	}

	@Override
	public Map<Variable, Integer> getVariableCount() {
		return new HashMap<Variable, Integer>();
	}

	@Override
	public COL_TYPE getType() {
		return COL_TYPE.BNODE;
	}

	@Override
	public String getValue() {
		return name;
	}

	@Override
	public String getLanguage() {
		return null;
	}
}
