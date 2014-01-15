package org.semanaticweb.ontop.owlrefplatform.core.tboxprocessing;

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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.EdgeReversedGraph;
import org.jgrapht.traverse.AbstractGraphIterator;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.semanaticweb.ontop.model.OBDADataFactory;
import org.semanaticweb.ontop.model.impl.OBDADataFactoryImpl;
import org.semanaticweb.ontop.ontology.Axiom;
import org.semanaticweb.ontop.ontology.ClassDescription;
import org.semanaticweb.ontop.ontology.Description;
import org.semanaticweb.ontop.ontology.Ontology;
import org.semanaticweb.ontop.ontology.OntologyFactory;
import org.semanaticweb.ontop.ontology.Property;
import org.semanaticweb.ontop.ontology.PropertySomeRestriction;
import org.semanaticweb.ontop.ontology.impl.OntologyFactoryImpl;
import org.semanaticweb.ontop.owlrefplatform.core.dagjgrapht.DAGImpl;
import org.semanaticweb.ontop.owlrefplatform.core.dagjgrapht.TBoxReasonerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prune Ontology for redundant assertions based on dependencies
 */
public class SigmaTBoxOptimizer {

	private static final Logger		log					= LoggerFactory.getLogger(SigmaTBoxOptimizer.class);
	private final DAGImpl				isa;
	private final DAGImpl				sigma;
	private final DAGImpl				isaChain;
	private final DAGImpl				sigmaChain;
	private final TBoxReasonerImpl reasonerIsa;
	private final TBoxReasonerImpl reasonerSigmaChain;
	private final TBoxReasonerImpl reasonerIsaChain;

	private static final OBDADataFactory	predicateFactory = OBDADataFactoryImpl.getInstance();
	private static final OntologyFactory	descFactory = OntologyFactoryImpl.getInstance();

	private Ontology				originalOntology	= null;

	private Ontology				originalSigma		= null;

	public SigmaTBoxOptimizer(Ontology isat, Ontology sigmat) {
		this.originalOntology = isat;

		this.originalSigma = sigmat;
		reasonerIsa= new TBoxReasonerImpl(isat,false);
		this.isa = reasonerIsa.getDAG();
		TBoxReasonerImpl reasonerSigma= new TBoxReasonerImpl(sigmat,false);
		this.sigma = reasonerSigma.getDAG();
		reasonerIsaChain= new TBoxReasonerImpl(isat,false);
		this.isaChain = reasonerIsaChain.getDAG();
		reasonerIsaChain.getChainDAG();
		
		reasonerSigmaChain= new TBoxReasonerImpl(sigmat,false);
		this.sigmaChain =  reasonerSigmaChain.getDAG();
		reasonerSigmaChain.getChainDAG();

	}

	public Ontology getReducedOntology() {
		Ontology reformulationOntology = descFactory.createOntology("http://it.unibz.krdb/obda/auxontology");
		reformulationOntology.addEntities(originalOntology.getVocabulary());

		reformulationOntology.addAssertions(reduce());
		return reformulationOntology;
	}

	public List<Axiom> reduce() {
		log.debug("Starting semantic-reduction");
		List<Axiom> rv = new LinkedList<Axiom>();

		for(Description node:isa.vertexSet()){
			for (Set<Description> descendants: reasonerIsa.getDescendants(node, false)){
					Description firstDescendant=descendants.iterator().next();
					Description descendant= isa.getReplacements().get(firstDescendant);
					if(descendant==null)
						descendant=firstDescendant;
					Axiom axiom = null;
					if(!descendant.equals(node)){
					/*
					 * Creating subClassOf or subPropertyOf axioms
					 */
					if (descendant instanceof ClassDescription) {
				if (!check_redundant(node, descendant)) {
					rv.add(descFactory.createSubClassAxiom((ClassDescription) descendant, (ClassDescription) node));
				}
			} else {
				if (!check_redundant_role(node,descendant)) {
					rv.add(descFactory.createSubPropertyAxiom((Property) descendant, (Property) node));
				}

			}
					}
			}
			Set<Description> equivalents = reasonerIsa.getEquivalences(node, false);

			for(Description equivalent:equivalents){
				if(!equivalent.equals(node)){
					if (node instanceof ClassDescription) {
						if (!check_redundant(equivalent, node)) {
							rv.add(descFactory.createSubClassAxiom((ClassDescription) node, (ClassDescription) equivalent));
						}
					} else {
						if (!check_redundant_role(equivalent,node)) {
							rv.add(descFactory.createSubPropertyAxiom((Property) node, (Property) equivalent));
						}

					}
					
					if (equivalent instanceof ClassDescription) {
						if (!check_redundant(node, equivalent)) {
							rv.add(descFactory.createSubClassAxiom((ClassDescription) equivalent, (ClassDescription) node));
						}
					} else {
						if (!check_redundant_role(node,equivalent)) {
							rv.add(descFactory.createSubPropertyAxiom((Property) equivalent, (Property) node));
						}
					}
					}
				}
		}
//		log.debug("Finished semantic-reduction.");
		return rv;
	}

	private boolean check_redundant_role(Description parent, Description child) {

		if (check_directly_redundant_role(parent, child))
			return true;
		else {
//			log.debug("Not directly redundant role {} {}", parent, child);
			for (Set<Description> children_prime : reasonerIsa.getDirectChildren(parent, false)) {
				Description child_prime=children_prime.iterator().next();

				if (!child_prime.equals(child) && check_directly_redundant_role(child_prime, child)
						&& !check_redundant(child_prime, parent)) {
					return true;
				}
//				}
			}
		}
//		log.debug("Not redundant role {} {}", parent, child);

		return false;
	}

	private boolean check_directly_redundant_role(Description parent, Description child) {
		Property parentDesc = (Property) parent;
		Property childDesc = (Property) child;

		PropertySomeRestriction existParentDesc = descFactory.getPropertySomeRestriction(parentDesc.getPredicate(), parentDesc.isInverse());
		PropertySomeRestriction existChildDesc = descFactory.getPropertySomeRestriction(childDesc.getPredicate(), childDesc.isInverse());

		Description exists_parent = isa.getNode(existParentDesc);
		Description exists_child = isa.getNode(existChildDesc);

		return check_directly_redundant(parent, child) && check_directly_redundant(exists_parent, exists_child);
	}

	private boolean check_redundant(Description parent, Description child) {
		if (check_directly_redundant(parent, child))
			return true;
		else {
			for (Set<Description> children_prime : reasonerIsa.getDirectChildren(parent, false)) {
			Description child_prime=children_prime.iterator().next();

				if (!child_prime.equals(child) && check_directly_redundant(child_prime, child) && !check_redundant(child_prime, parent)) {
					return true;
				}
				}
		}
		return false;
	}

	private boolean check_directly_redundant(Description parent, Description child) {
		Description sp = sigmaChain.getNode(parent);
		Description sc = sigmaChain.getNode(child);
		Description tc = isaChain.getNode(child);

		if (sp == null || sc == null || tc == null) {
			return false;
		}
		
		Set<Set<Description>> spChildren= reasonerSigmaChain.getDirectChildren(sp, false);
		Set<Description> scEquivalent=reasonerSigmaChain.getEquivalences(sc, false);
		Set<Set<Description>> scChildren= reasonerSigmaChain.getDescendants(sc, false);
		Set<Set<Description>> tcChildren= reasonerIsaChain.getDescendants(tc,false);
		
		if (sigmaChain.getReplacements().get(parent) != null){
			sp = sigmaChain.getNode(sigmaChain.getReplacements().get(parent));
			spChildren= reasonerSigmaChain.getDirectChildren(sp, false);
		}
		if (sigmaChain.getReplacements().get(child) != null){
			sc = sigmaChain.getNode(sigmaChain.getReplacements().get(child));
			scEquivalent=reasonerSigmaChain.getEquivalences(sc, false);
			scChildren=reasonerSigmaChain.getDescendants(sc, false); 
			
		}
		if (isaChain.getReplacements().get(child) != null){
			tc = sigmaChain.getNode(isaChain.getReplacements().get(child));
			reasonerSigmaChain.getDescendants(tc,false);
		}

		boolean redundant =spChildren.contains(scEquivalent) && scChildren.containsAll(tcChildren);
		return (redundant);

	}

}
