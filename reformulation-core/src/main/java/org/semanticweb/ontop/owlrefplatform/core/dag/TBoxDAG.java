/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.owlrefplatform.core.dag;

import java.util.Set;

import org.semanticweb.ontop.ontology.Description;

/***
 * A class that provides TBox entailment computation based on a DAG
 * 
 * @author mariano
 * 
 */
public interface TBoxDAG {

	public Set<Set<Description>> getDirectChildren(Description desc);

	public Set<Set<Description>> getDirectParents(Description desc);

	public Set<Set<Description>> getDescendants(Description desc);

	public Set<Set<Description>> getAncestors(Description desc);

	public Set<Description> getEquiavlences(Description description);

	public void getNode();

}
