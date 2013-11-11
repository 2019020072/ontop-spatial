/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.quest.scenarios;
import junit.framework.Test;

public class MergeVirtualScenarioTest extends QuestVirtualScenarioParent{

		public MergeVirtualScenarioTest(String testURI, String name, String queryFileURL, String resultFileURL, 
				String owlFileURL, String obdaFileURL, String parameterFileURL) {
			super(testURI, name, queryFileURL, resultFileURL, owlFileURL, obdaFileURL, parameterFileURL);
		}

		public static Test suite() throws Exception {
			return ScenarioManifestTestUtils.suite(new Factory() {
				@Override
				public QuestVirtualScenarioParent createQuestScenarioTest(String testURI, String name, String queryFileURL, 
						String resultFileURL, String owlFileURL, String obdaFileURL) {
					return new Db2VirtualScenarioTest(testURI, name, queryFileURL, resultFileURL, owlFileURL, 
							obdaFileURL, "");
				}
				@Override
				public QuestVirtualScenarioParent createQuestScenarioTest(String testURI, String name, String queryFileURL, 
						String resultFileURL, String owlFileURL, String obdaFileURL, String parameterFileURL) {
					return new Db2VirtualScenarioTest(testURI, name, queryFileURL, resultFileURL, owlFileURL, 
							obdaFileURL, parameterFileURL);
				}
				@Override
				public String getMainManifestFile() {
					return "/testcases-scenarios/virtual-mode/manifest-scenario-merge.ttl";
				}
			});
		}
	
}
