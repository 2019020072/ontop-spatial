/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.ontology.impl;

import org.semanticweb.ontop.model.Predicate;
import org.semanticweb.ontop.ontology.OClass;
import org.semanticweb.ontop.ontology.PropertySomeClassRestriction;

public class PropertySomeClassRestrictionImpl implements PropertySomeClassRestriction{

	private static final long serialVersionUID = -8242919752579569694L;
	
	private final Predicate predicate;
	private final boolean isInverse;
	private final OClass filler;

	PropertySomeClassRestrictionImpl(Predicate p, boolean isInverse, OClass filler) {
		this.predicate = p;
		this.isInverse = isInverse;
		this.filler = filler;
	}

	@Override
	public boolean isInverse() {
		return isInverse;
	}

	@Override
	public Predicate getPredicate() {
		return predicate;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PropertySomeClassRestrictionImpl)) {
			return false;
		}
		PropertySomeClassRestrictionImpl concept2 = (PropertySomeClassRestrictionImpl) obj;
		if (isInverse != concept2.isInverse) {
			return false;
		}
		if (!predicate.equals(concept2.getPredicate())) {
			return false;
		}
		return (filler.equals(concept2.filler));
	}

	public String toString() {
		StringBuilder bf = new StringBuilder();
		bf.append("E");
		bf.append(predicate.toString());
		if (isInverse) {
			bf.append("^-");
		}
		bf.append(".");
		bf.append(filler.toString());
		return bf.toString();
	}

	@Override
	public OClass getFiller() {
		return filler;
	}
}
