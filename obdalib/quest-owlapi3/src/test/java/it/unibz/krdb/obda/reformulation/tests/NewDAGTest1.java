package it.unibz.krdb.obda.reformulation.tests;

import it.unibz.krdb.obda.ontology.Description;
import it.unibz.krdb.obda.ontology.Ontology;
import it.unibz.krdb.obda.owlapi3.OWLAPI3Translator;
import it.unibz.krdb.obda.owlrefplatform.core.dag.TBoxDAGImpl;

import java.io.File;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewDAGTest1 extends TestCase {

	Logger log = LoggerFactory.getLogger(NewDAGTest1.class);

	public void testCreation() throws Exception {
		String owlfile = "src/test/resources/dag-tests-1.owl";
		// String owlfile =
		// "src/test/resources/test/stockexchange-unittest.owl";
		log.info("Loading ontology");

		// Loading the OWL file
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument((new File(owlfile)));

		log.info("Translating");

		OWLAPI3Translator translator = new OWLAPI3Translator();
		Ontology o = translator.translate(ontology);

		log.info("Generating dag");
		TBoxDAGImpl dag = new TBoxDAGImpl(o);
		
		System.out.println(dag.getDag().edgeSet());
		for (Description d: dag.getDag().vertexSet()){
		System.out.println("parents "+d+" "+dag.getDirectParents(d));
		System.out.println("children "+d+" "+dag.getDirectChildren(d));
		}

	}
}
