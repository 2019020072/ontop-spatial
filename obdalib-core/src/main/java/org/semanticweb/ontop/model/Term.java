/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * This class defines the basic component of the proposition. A proposition is a
 * particular kind of sentence, in which the subject and predicate are combined.
 * In this scenario, term means the subject (or sometimes can be the object) of
 * a preposition.
 */
public interface Term extends Serializable {

	public Term clone();

	public Set<Variable> getReferencedVariables();

	public Map<Variable, Integer> getVariableCount();

}
