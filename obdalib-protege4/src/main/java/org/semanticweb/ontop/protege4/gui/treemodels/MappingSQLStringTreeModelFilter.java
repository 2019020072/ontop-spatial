/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.protege4.gui.treemodels;

import org.semanticweb.ontop.protege4.gui.treemodels.TreeModelFilter;

import org.semanticweb.ontop.model.OBDAMappingAxiom;
import org.semanticweb.ontop.model.OBDASQLQuery;

/**
 * This filter receives a string in the constructor and returns true if any mapping contains the string in the body.
 */
public class MappingSQLStringTreeModelFilter extends TreeModelFilter<OBDAMappingAxiom> {

	public MappingSQLStringTreeModelFilter() {
		super.bNegation = false;
	}

	@Override
	public boolean match(OBDAMappingAxiom object) {
		final OBDASQLQuery bodyquery = (OBDASQLQuery) object.getSourceQuery();

		boolean isMatch = false;
		for (String keyword : vecKeyword) {
			isMatch = match(keyword.trim(), bodyquery.toString());
			if (isMatch) {
				break; // end loop if a match is found!
			}
		}
		// no match found!
		return (bNegation ? !isMatch : isMatch);
	}

	/** A helper method to check a match */
	public static boolean match(String keyword, String query) {
		if (query.indexOf(keyword) != -1) { // match found!
			return true;
		}
		return false;
	}
}
