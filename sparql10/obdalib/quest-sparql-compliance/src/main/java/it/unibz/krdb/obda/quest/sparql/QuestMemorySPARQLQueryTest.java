/*
 * Copyright Aduna (http://www.aduna-software.com/) (c) 1997-2008.
 *
 * Licensed under the Aduna BSD-style license.
 */
package it.unibz.krdb.obda.quest.sparql;

import junit.framework.Test;

import org.openrdf.query.Dataset;
import org.openrdf.repository.Repository;

import sesameWrapper.SesameClassicInMemoryRepo;

public class QuestMemorySPARQLQueryTest extends SPARQLQueryTest {

	public static Test suite() throws Exception {
		return ManifestTest.suite(new Factory() {

			public QuestMemorySPARQLQueryTest createSPARQLQueryTest(
					String testURI, String name, String queryFileURL,
					String resultFileURL, Dataset dataSet,
					boolean laxCardinality) {
				return createSPARQLQueryTest(testURI, name, queryFileURL,
						resultFileURL, dataSet, laxCardinality, false);
			}

			public QuestMemorySPARQLQueryTest createSPARQLQueryTest(
					String testURI, String name, String queryFileURL,
					String resultFileURL, Dataset dataSet,
					boolean laxCardinality, boolean checkOrder) {
				return new QuestMemorySPARQLQueryTest(testURI, name,
						queryFileURL, resultFileURL, dataSet, laxCardinality,
						checkOrder);
			}
		});
	}

	protected QuestMemorySPARQLQueryTest(String testURI, String name,
			String queryFileURL, String resultFileURL, Dataset dataSet,
			boolean laxCardinality) {
		this(testURI, name, queryFileURL, resultFileURL, dataSet,
				laxCardinality, false);
	}

	protected QuestMemorySPARQLQueryTest(String testURI, String name,
			String queryFileURL, String resultFileURL, Dataset dataSet,
			boolean laxCardinality, boolean checkOrder) {
		super(testURI, name, queryFileURL, resultFileURL, dataSet,
				laxCardinality, checkOrder);
	}

	@Override
	protected Repository newRepository() {
		try {
			SesameClassicInMemoryRepo repo = new SesameClassicInMemoryRepo(
					"test", dataset);
			repo.initialize();
			return repo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
