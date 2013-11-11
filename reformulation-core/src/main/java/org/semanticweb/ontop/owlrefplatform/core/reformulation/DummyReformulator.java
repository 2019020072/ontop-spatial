/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.owlrefplatform.core.reformulation;

import org.semanticweb.ontop.model.OBDAException;
import org.semanticweb.ontop.model.OBDAQuery;
import org.semanticweb.ontop.ontology.Ontology;

/***
 * A query reformulator that does nothing on the given query. 
 * 
 * @author mariano
 *
 */
public class DummyReformulator implements QueryRewriter {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8989177354924893482L;

	@Override
	public OBDAQuery rewrite(OBDAQuery input) throws OBDAException {
		return input;
	}

	@Override
	public void setTBox(Ontology ontology) {
		// NO-OP
		
	}

	@Override
	public void setCBox(Ontology sigma) {
		// NO-OP
		
	}

	@Override
	public void initialize() {
		// NO-OP
		
	}

}
