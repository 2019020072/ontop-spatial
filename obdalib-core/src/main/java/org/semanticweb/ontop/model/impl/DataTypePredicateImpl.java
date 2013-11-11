/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.model.impl;

import org.semanticweb.ontop.model.DataTypePredicate;

//import com.hp.hpl.jena.iri.IRI;

public class DataTypePredicateImpl extends PredicateImpl implements DataTypePredicate {

	private static final long serialVersionUID = -6678449661465775977L;

	/**
	 * Constructs a datatype predicate with one term. This is a usual construct
	 * where the type of the term represents the datatype itself.
	 * 
	 * @param name
	 * 			The predicate name.
	 * @param type
	 * 			The datatype that the term holds.
	 */
	public DataTypePredicateImpl(String name, COL_TYPE type) {
		super(name, 1, new COL_TYPE[] { type });
	}
	
	/**
	 * Construct a datatype predicate with two or more terms. The first term
	 * used to hold the value and the others are for any additional information.
	 * An example for using this constructor is the rdfs:Literal(value, lang).
	 * The predicate uses the second term to put the language tag.
	 * 
	 * @param name
	 * 			The predicate name.
	 * @param types
	 * 			The datatypes that each term holds.
	 */
	public DataTypePredicateImpl(String name, COL_TYPE[] types) {
		super(name, types.length, types);
	}
	
	@Override
	public DataTypePredicateImpl clone() {
		return this;
	}
}
