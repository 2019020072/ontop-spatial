/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.sesame.completeness.test;

import junit.framework.Test;

/**
 * Test the sound and completeness of Quest reasoner with respect to DL-Lite
 * semantic.
 * 
 * Setting: Without Optimizing Equivalences and Without Using TBox Sigma. 
 */
public class DLLiteCompletenessNoEqNoSigTest extends CompletenessParent {

	public DLLiteCompletenessNoEqNoSigTest(String tid, String name, String resf,
			String propf, String owlf, String sparqlf) throws Exception {
		super(tid, name, resf, propf, owlf, sparqlf);
	}

	public static Test suite() throws Exception {
		return CompletenessTestUtils.suite(new Factory() {
			@Override
			public CompletenessParent createCompletenessTest(String testId,
					String testName, String testResultPath,
					String testParameterPath, String testOntologyPath,
					String testQueryPath) throws Exception {
				return new DLLiteCompletenessNoEqNoSigTest(testId, testName,
						testResultPath, testParameterPath, testOntologyPath,
						testQueryPath);
			}

			@Override
			public String getMainManifestFile() {
				return "/completeness/manifest-noeq-nosig.ttl";
			}
		});
	}
}
