/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.reformulation.tests;

import java.io.File;
import java.util.Map;

import junit.framework.TestCase;

import org.semanticweb.ontop.model.OBDADataFactory;
import org.semanticweb.ontop.model.Predicate;
import org.semanticweb.ontop.model.impl.OBDADataFactoryImpl;
import org.semanticweb.ontop.ontology.Description;
import org.semanticweb.ontop.ontology.Ontology;
import org.semanticweb.ontop.ontology.OntologyFactory;
import org.semanticweb.ontop.ontology.impl.OntologyFactoryImpl;
import org.semanticweb.ontop.owlapi3.OWLAPI3Translator;
import org.semanticweb.ontop.owlrefplatform.core.tboxprocessing.EquivalenceTBoxOptimizer;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class EquivalenceSimplificationTest extends TestCase {

	final String	path	= "src/test/resources/test/equivalence/";

	public void test_equivalence_namedclasses() throws Exception {

		/*
		 * The ontology contains A1 = A2 = A3, B1 ISA A1, B1 = B2 = B3, this
		 * gives 9 inferences and R1 = R2 = R3, S1 ISA R1, S1 = S2 = S3, this
		 * gives 36 inferences (counting inverse related inferences, and exist
		 * related inferences. Total, 45 inferences
		 */

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File file = new File(path + "test_401.owl");
		OWLOntology owlonto = man.loadOntologyFromOntologyDocument(file);
		OWLAPI3Translator translator = new OWLAPI3Translator();
		Ontology ontology = translator.translate(owlonto);

		EquivalenceTBoxOptimizer optimizer = new EquivalenceTBoxOptimizer(ontology);
		optimizer.optimize();
		Ontology simpleonto = optimizer.getOptimalTBox();
		Map<Predicate, Description> eqMap = optimizer.getEquivalenceMap();

		assertEquals(3, simpleonto.getConcepts().size());
		assertEquals(0, simpleonto.getRoles().size());
		assertEquals(3, simpleonto.getAssertions().size());
		assertEquals(3, simpleonto.getVocabulary().size());

		OntologyFactory ofac = OntologyFactoryImpl.getInstance();
		OBDADataFactory odfac = OBDADataFactoryImpl.getInstance();

		assertEquals(6, eqMap.keySet().size());
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A2")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C2")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
		
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A2")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C2")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));

	}
	
	
	public void test_equivalence_namedproperties() throws Exception {

		/*
		 * The ontology contains A1 = A2 = A3, B1 ISA A1, B1 = B2 = B3, this
		 * gives 9 inferences and R1 = R2 = R3, S1 ISA R1, S1 = S2 = S3, this
		 * gives 36 inferences (counting inverse related inferences, and exist
		 * related inferences. Total, 45 inferences
		 */

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File file = new File(path + "test_402.owl");
		OWLOntology owlonto = man.loadOntologyFromOntologyDocument(file);
		OWLAPI3Translator translator = new OWLAPI3Translator();
		Ontology ontology = translator.translate(owlonto);

		EquivalenceTBoxOptimizer optimizer = new EquivalenceTBoxOptimizer(ontology);
		optimizer.optimize();
		Ontology simpleonto = optimizer.getOptimalTBox();
		Map<Predicate, Description> eqMap = optimizer.getEquivalenceMap();

		assertEquals(0, simpleonto.getConcepts().size());
		assertEquals(3, simpleonto.getRoles().size());
		assertEquals(12, simpleonto.getAssertions().size());
		assertEquals(3, simpleonto.getVocabulary().size());

		OntologyFactory ofac = OntologyFactoryImpl.getInstance();
		OBDADataFactory odfac = OBDADataFactoryImpl.getInstance();

		assertEquals(6, eqMap.keySet().size());
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A2")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C2")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
		
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A2")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C2")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
	}
	
	
	public void test_equivalence_namedclassesandexists() throws Exception {

		/*
		 * The ontology contains A1 = A2 = A3, B1 ISA A1, B1 = B2 = B3, this
		 * gives 9 inferences and R1 = R2 = R3, S1 ISA R1, S1 = S2 = S3, this
		 * gives 36 inferences (counting inverse related inferences, and exist
		 * related inferences. Total, 45 inferences
		 */

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File file = new File(path + "test_403.owl");
		OWLOntology owlonto = man.loadOntologyFromOntologyDocument(file);
		OWLAPI3Translator translator = new OWLAPI3Translator();
		Ontology ontology = translator.translate(owlonto);

		EquivalenceTBoxOptimizer optimizer = new EquivalenceTBoxOptimizer(ontology);
		optimizer.optimize();
		Ontology simpleonto = optimizer.getOptimalTBox();
		Map<Predicate, Description> eqMap = optimizer.getEquivalenceMap();

		assertEquals(simpleonto.getConcepts().toString(), 3, simpleonto.getConcepts().size());
		assertEquals(3, simpleonto.getRoles().size());
		assertEquals(9, simpleonto.getAssertions().size());
		assertEquals(6, simpleonto.getVocabulary().size());

		OntologyFactory ofac = OntologyFactoryImpl.getInstance();
		OBDADataFactory odfac = OBDADataFactoryImpl.getInstance();

		assertEquals(3, eqMap.keySet().size());
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertTrue(eqMap.keySet().contains(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
		
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertEquals(ofac.createClass("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3"),eqMap.get(odfac.getClassPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
		
	}
	
	public void test_equivalence_namedproperties_and_inverses() throws Exception {

		/*
		 * The ontology contains A1 = A2 = A3, B1 ISA A1, B1 = B2 = B3, this
		 * gives 9 inferences and R1 = R2 = R3, S1 ISA R1, S1 = S2 = S3, this
		 * gives 36 inferences (counting inverse related inferences, and exist
		 * related inferences. Total, 45 inferences
		 */

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File file = new File(path + "test_404.owl");
		OWLOntology owlonto = man.loadOntologyFromOntologyDocument(file);
		OWLAPI3Translator translator = new OWLAPI3Translator();
		Ontology ontology = translator.translate(owlonto);

		EquivalenceTBoxOptimizer optimizer = new EquivalenceTBoxOptimizer(ontology);
		optimizer.optimize();
		Ontology simpleonto = optimizer.getOptimalTBox();
		Map<Predicate, Description> eqMap = optimizer.getEquivalenceMap();

		assertEquals(12, simpleonto.getAssertions().size());
		assertEquals(0, simpleonto.getConcepts().size());
		assertEquals(3, simpleonto.getRoles().size());
		assertEquals(3, simpleonto.getVocabulary().size());

		OntologyFactory ofac = OntologyFactoryImpl.getInstance();
		OBDADataFactory odfac = OBDADataFactoryImpl.getInstance();

		assertEquals(6, eqMap.keySet().size());
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A2")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C2")));
		assertTrue(eqMap.keySet().contains(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
		
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3",true),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A2")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A3"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#A1")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1", true),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B1"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B3")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3", true),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C2")));
		assertEquals(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C3"),eqMap.get(odfac.getObjectPropertyPredicate("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#C1")));
	}

}
