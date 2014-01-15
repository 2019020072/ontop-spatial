package org.semanaticweb.ontop.owlrefplatform.core.dagjgrapht;

/*
 * #%L
 * ontop-reformulation-core
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


import java.util.Collection;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.semanaticweb.ontop.model.Predicate;
import org.semanaticweb.ontop.ontology.Axiom;
import org.semanaticweb.ontop.ontology.ClassDescription;
import org.semanaticweb.ontop.ontology.Ontology;
import org.semanaticweb.ontop.ontology.OntologyFactory;
import org.semanaticweb.ontop.ontology.Property;
import org.semanaticweb.ontop.ontology.PropertySomeRestriction;
import org.semanaticweb.ontop.ontology.impl.OntologyFactoryImpl;
import org.semanaticweb.ontop.ontology.impl.SubClassAxiomImpl;
import org.semanaticweb.ontop.ontology.impl.SubPropertyAxiomImpl;

/**
 * Builds a graph starting from the axioms of a TBox.

 */


public class GraphBuilderImpl implements GraphBuilder{
	

	private final GraphImpl graph = new  GraphImpl(DefaultEdge.class);
	private final OntologyFactory descFactory = new OntologyFactoryImpl();
	

	/**
	 * Build the graph from the TBox axioms of the ontology
	 * 
	 * @param ontology TBox containing the axioms
	 */
	public GraphBuilderImpl (Ontology ontology){
		
		for (Predicate conceptp : ontology.getConcepts()) {
			ClassDescription concept = descFactory.createClass(conceptp);
			graph.addVertex(concept);
		}

		/*
		 * For each role we add nodes for its inverse, its domain and its range
		 */
		for (Predicate rolep : ontology.getRoles()) {
			Property role = descFactory.createProperty(rolep);
			Property roleInv = descFactory.createProperty(role.getPredicate(), !role.isInverse());
			PropertySomeRestriction existsRole = descFactory.getPropertySomeRestriction(role.getPredicate(), role.isInverse());
			PropertySomeRestriction existsRoleInv = descFactory.getPropertySomeRestriction(role.getPredicate(), !role.isInverse());
			graph.addVertex(role);
			graph.addVertex(roleInv);
			graph.addVertex(existsRole);
			graph.addVertex(existsRoleInv);
		}

		for (Axiom assertion : ontology.getAssertions()) {

			if (assertion instanceof SubClassAxiomImpl) {
				SubClassAxiomImpl clsIncl = (SubClassAxiomImpl) assertion;
				ClassDescription parent = clsIncl.getSuper();
				ClassDescription child = clsIncl.getSub();
				graph.addVertex(child);
				graph.addVertex(parent);
				graph.addEdge(child, parent);
			} else if (assertion instanceof SubPropertyAxiomImpl) {
				SubPropertyAxiomImpl roleIncl = (SubPropertyAxiomImpl) assertion;
				Property parent = roleIncl.getSuper();
				Property child = roleIncl.getSub();
				Property parentInv = descFactory.createProperty(parent.getPredicate(), !parent.isInverse());
				Property childInv = descFactory.createProperty(child.getPredicate(), !child.isInverse());

				// This adds the direct edge and the inverse, e.g., R ISA S and
				// R- ISA S-,
				// R- ISA S and R ISA S-
				graph.addVertex(child);
				graph.addVertex(parent);
				graph.addVertex(childInv);
				graph.addVertex(parentInv);
				graph.addEdge(child, parent);
				graph.addEdge(childInv, parentInv);
				
				//add also edges between the existential
				ClassDescription existsParent = descFactory.getPropertySomeRestriction(parent.getPredicate(), parent.isInverse());
				ClassDescription existChild = descFactory.getPropertySomeRestriction(child.getPredicate(), child.isInverse());
				ClassDescription existsParentInv = descFactory.getPropertySomeRestriction(parent.getPredicate(), !parent.isInverse());
				ClassDescription existChildInv = descFactory.getPropertySomeRestriction(child.getPredicate(), !child.isInverse());
				if(!graph.containsVertex(existChild)){
					
						graph.addVertex(existChild);
				}
				if(!graph.containsVertex(existsParent)){
					
					 graph.addVertex(existsParent);
					
					
				}
				graph.addEdge(existChild, existsParent);
				
				if(!graph.containsVertex(existChildInv)){
					
					graph.addVertex(existChildInv);
			}
			if(!graph.containsVertex(existsParentInv)){
				
				 graph.addVertex(existsParentInv);
				
				
			}
				graph.addEdge(existChildInv, existsParentInv);
				
			}
		}


	


	}
	/**
	 * Build the graph starting from assertion of a TBox 
	 * @param assertions assertions of an ontology
	 * @param concepts concepts of an ontology
	 * @param roles roles of an ontology
	 * 
	 */
	
	public GraphBuilderImpl (Collection<Axiom> assertions, Set<Predicate> concepts, Set<Predicate> roles){
		for (Predicate conceptp : concepts) {
			ClassDescription concept = descFactory.createClass(conceptp);
			graph.addVertex(concept);
		}

		/*
		 * For each role we add nodes for its inverse, its domain and its range
		 */
		for (Predicate rolep : roles) {
			Property role = descFactory.createProperty(rolep);
			Property roleInv = descFactory.createProperty(role.getPredicate(), !role.isInverse());
			PropertySomeRestriction existsRole = descFactory.getPropertySomeRestriction(role.getPredicate(), role.isInverse());
			PropertySomeRestriction existsRoleInv = descFactory.getPropertySomeRestriction(role.getPredicate(), !role.isInverse());
			graph.addVertex(role);
			graph.addVertex(roleInv);
			graph.addVertex(existsRole);
			graph.addVertex(existsRoleInv);
		}

		for (Axiom assertion : assertions) {

			if (assertion instanceof SubClassAxiomImpl) {
				SubClassAxiomImpl clsIncl = (SubClassAxiomImpl) assertion;
				ClassDescription parent = clsIncl.getSuper();
				ClassDescription child = clsIncl.getSub();
				graph.addVertex(child);
				graph.addVertex(parent);
				graph.addEdge(child, parent);
			} else if (assertion instanceof SubPropertyAxiomImpl) {
				SubPropertyAxiomImpl roleIncl = (SubPropertyAxiomImpl) assertion;
				Property parent = roleIncl.getSuper();
				Property child = roleIncl.getSub();
				Property parentInv = descFactory.createProperty(parent.getPredicate(), !parent.isInverse());
				Property childInv = descFactory.createProperty(child.getPredicate(), !child.isInverse());

				// This adds the direct edge and the inverse, e.g., R ISA S and
				// R- ISA S-,
				// R- ISA S and R ISA S-
				graph.addVertex(child);
				graph.addVertex(parent);
				graph.addVertex(childInv);
				graph.addVertex(parentInv);
				graph.addEdge(child, parent);
				graph.addEdge(childInv, parentInv);
				
			}
		}

	}

@Override
	/**
	 * Give the graph
	 * @return graph return the created graph
	 */
	public  Graph  getGraph(){

		return graph;
	}





}
