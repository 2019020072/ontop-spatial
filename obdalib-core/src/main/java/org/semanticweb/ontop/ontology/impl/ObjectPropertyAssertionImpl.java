/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.ontology.impl;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.ontop.model.Constant;
import org.semanticweb.ontop.model.ObjectConstant;
import org.semanticweb.ontop.model.Predicate;
import org.semanticweb.ontop.ontology.BinaryAssertion;
import org.semanticweb.ontop.ontology.ObjectPropertyAssertion;

public class ObjectPropertyAssertionImpl implements ObjectPropertyAssertion, BinaryAssertion {

	private static final long serialVersionUID = -8834975903851540150L;
	
	private Predicate role;
	private ObjectConstant o2;
	private ObjectConstant o1;

	ObjectPropertyAssertionImpl(Predicate role, ObjectConstant o1, ObjectConstant o2) {
		this.role = role;
		this.o1 = o1;
		this.o2 = o2;
	}

	@Override
	public ObjectConstant getFirstObject() {
		return o1;
	}

	@Override
	public ObjectConstant getSecondObject() {
		return o2;
	}

	@Override
	public Predicate getRole() {
		return role;
	}

	public String toString() {
		return role.toString() + "(" + o1.toString() + ", " + o2.toString() + ")";
	}

	@Override
	public Set<Predicate> getReferencedEntities() {
		Set<Predicate> res = new HashSet<Predicate>();
		res.add(role);
		return res;
	}

	@Override
	public Constant getValue1() {
		return getFirstObject();
	}

	@Override
	public Constant getValue2() {
		return getSecondObject();
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public Predicate getPredicate() {
		return role;
	}
}
