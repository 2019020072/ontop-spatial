package org.semanticweb.ontop.obda.quest.dag;

/*
 * #%L
 * ontop-quest-owlapi3
 * %%
 * Copyright (C) 2009 - 2013 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import junit.framework.TestCase;

import org.jgrapht.Graphs;
import org.semanticweb.ontop.ontology.Description;
import org.semanticweb.ontop.ontology.Ontology;
import org.semanticweb.ontop.ontology.OntologyFactory;
import org.semanticweb.ontop.ontology.Property;
import org.semanticweb.ontop.ontology.impl.OntologyFactoryImpl;
import org.semanticweb.ontop.owlapi3.OWLAPI3Translator;
import org.semanticweb.ontop.owlrefplatform.core.dag.DAG;
import org.semanticweb.ontop.owlrefplatform.core.dag.DAGConstructor;
import org.semanticweb.ontop.owlrefplatform.core.dag.DAGNode;
import org.semanticweb.ontop.owlrefplatform.core.dag.DAGOperations;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.DAGImpl;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.NamedDAGBuilderImpl;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.SemanticIndexEngineImpl;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.SemanticIndexRange;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.TBoxReasonerImpl;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S_Indexes_Compare extends TestCase {
	
	ArrayList<String> input= new ArrayList<String>();

	Logger log = LoggerFactory.getLogger(S_HierarchyTestNewDAG.class);

	public S_Indexes_Compare (String name){
		super(name);
	}
	
public void setUp(){
		
	
	input.add("src/test/resources/test/equivalence/test_404.owl");

}

public void testIndexes() throws Exception{
	//for each file in the input
	for (int i=0; i<input.size(); i++){
		String fileInput=input.get(i);

		DAGImpl dag= S_InputOWL.createDAG(fileInput);


		//add input named graph
		NamedDAGBuilderImpl transform = new NamedDAGBuilderImpl(dag);
		DAGImpl namedDag= transform.getDAG();

		
		log.debug("Input number {}", i+1 );
		log.info("named graph {}", namedDag);
		
		
		testIndexes(namedDag);

		OWLAPI3Translator t = new OWLAPI3Translator();
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology owlonto = man.loadOntologyFromOntologyDocument(new File(fileInput));
		Ontology onto = t.translate(owlonto);
		DAG dag2 = DAGConstructor.getISADAG(onto);
		dag2.clean();
        DAGOperations.buildDescendants(dag2);
        DAGOperations.buildAncestors(dag2);
		DAG pureIsa = DAGConstructor.filterPureISA(dag2);
		 pureIsa.clean();
			pureIsa.index();
			 DAGOperations.buildDescendants(pureIsa);
		        DAGOperations.buildAncestors(pureIsa);
		 testOldIndexes(pureIsa, namedDag);
		
	}
}

private void testOldIndexes(DAG d1, DAGImpl d2){
	
	
	
	for(DAGNode d: d1.getClasses()){
		System.out.println(d + "\n "+ d.getEquivalents());
		System.out.println(d1.equi_mappings.values());
		
	}
	
	
	for(DAGNode d: d1.getRoles()){
		System.out.println(d );
		for(DAGNode dd: d.getEquivalents()){
		System.out.println(d1.getRoleNode(((Property)dd.getDescription())));
		;
		}
		OntologyFactory ofac = OntologyFactoryImpl.getInstance();
		System.out.println(d1.getRoleNode(ofac.createObjectProperty("http://obda.inf.unibz.it/ontologies/tests/dllitef/test.owl#B2")));
		;
	}
	

	

	
	
}
private boolean testIndexes( DAGImpl dag){
	TBoxReasonerImpl reasoner= new TBoxReasonerImpl(dag);
	boolean result=false;
	
	
	//create semantic index
	SemanticIndexEngineImpl engine= new SemanticIndexEngineImpl(reasoner);
	Map<Description, Integer> indexes=engine.getIndexes();
	Map<Description, SemanticIndexRange> ranges=engine.getIntervals();
	
	//check that the index of the node is contained in the intervals of the parent node
	for(Description vertex: dag.vertexSet()){
		int index= indexes.get(vertex);
		for(Description parent: Graphs.successorListOf(dag, vertex)){
			result=ranges.get(parent).contained(new SemanticIndexRange(index,index));
			
			if(result)
				break;
		}
		
		if(!result)
			break;
	}
	
	log.info("indexes {}", indexes);
	log.info("ranges {}", ranges);
	
	
	return result;
	

}






}
