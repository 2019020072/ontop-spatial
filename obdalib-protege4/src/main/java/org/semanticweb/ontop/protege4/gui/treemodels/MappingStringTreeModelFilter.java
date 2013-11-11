/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.protege4.gui.treemodels;

import it.unibz.krdb.obda.model.Function;
import it.unibz.krdb.obda.model.CQIE;
import it.unibz.krdb.obda.model.OBDAMappingAxiom;
import it.unibz.krdb.obda.model.OBDASQLQuery;
import it.unibz.krdb.obda.model.impl.CQIEImpl;

import java.util.List;

import org.semanticweb.ontop.protege4.gui.treemodels.MappingHeadVariableTreeModelFilter;
import org.semanticweb.ontop.protege4.gui.treemodels.MappingIDTreeModelFilter;
import org.semanticweb.ontop.protege4.gui.treemodels.MappingSQLStringTreeModelFilter;
import org.semanticweb.ontop.protege4.gui.treemodels.TreeModelFilter;

/**
 * This filter receives a string in the constructor and returns true if accepts
 * any mapping containing the string in the head or body
 */
public class MappingStringTreeModelFilter extends TreeModelFilter<OBDAMappingAxiom> {

	public MappingStringTreeModelFilter() {
		super.bNegation = false;
	}

	@Override
	public boolean match(OBDAMappingAxiom object) {
		boolean isMatch = false;
		for (String keyword : vecKeyword) {
			// Check in the Mapping ID
			final String mappingId = object.getId();
			isMatch = MappingIDTreeModelFilter.match(keyword.trim(), mappingId);
			if (isMatch) {
				break; // end loop if a match is found!
			}

			// Check in the Mapping Target Query
			final CQIE headquery = (CQIEImpl) object.getTargetQuery();
			final List<Function> atoms = headquery.getBody();
			for (int i = 0; i < atoms.size(); i++) {
				Function predicate = (Function) atoms.get(i);
				isMatch = isMatch || MappingHeadVariableTreeModelFilter.match(keyword.trim(), predicate);
			}
			if (isMatch) {
				break; // end loop if a match is found!
			}

			// Check in the Mapping Source Query
			final OBDASQLQuery query = (OBDASQLQuery) object.getSourceQuery();
			isMatch = MappingSQLStringTreeModelFilter.match(keyword.trim(), query.toString());
			if (isMatch) {
				break; // end loop if a match is found!
			}
		}
		// no match found!
		return (bNegation ? !isMatch : isMatch);
	}
}
