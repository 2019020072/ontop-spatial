package org.semanticweb.ontop.owlrefplatform.core.tboxprocessing;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.semanticweb.ontop.model.Predicate;
import org.semanticweb.ontop.ontology.Axiom;
import org.semanticweb.ontop.ontology.ClassDescription;
import org.semanticweb.ontop.ontology.Description;
import org.semanticweb.ontop.ontology.OClass;
import org.semanticweb.ontop.ontology.Ontology;
import org.semanticweb.ontop.ontology.OntologyFactory;
import org.semanticweb.ontop.ontology.Property;
import org.semanticweb.ontop.ontology.PropertySomeRestriction;
import org.semanticweb.ontop.ontology.impl.OntologyFactoryImpl;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.DAGImpl;
import org.semanticweb.ontop.owlrefplatform.core.dagjgrapht.TBoxReasonerImpl;

/***
 * An optimizer that will eliminate equivalences implied by the ontology,
 * simplifying the vocabulary of the ontology. This allows to reduce the number
 * of inferences implied by the onotlogy and eliminating redundancy. The output
 * is two components: a "equivalence map" that functional mapping from class
 * (property) expression to class (property) that can be used to retrieve the
 * class (property) of the optimized ontology that should be used instead of one
 * class (property) that has been removed; a TBox T' that has a simpler
 * vocabulary.
 * 
 * 
 * 
 * @author Mariano Rodriguez Muro
 * 
 */
public class EquivalenceTBoxOptimizer {

	private Ontology optimalTBox = null;
	private Map<Predicate, Description> equivalenceMap;
	private Ontology tbox;

	private static final OntologyFactory ofac = OntologyFactoryImpl.getInstance();

	public EquivalenceTBoxOptimizer(Ontology tbox) {
		this.tbox = tbox;
		equivalenceMap = new HashMap<Predicate, Description>();
	}

	/***
	 * Optimize will compute the implied hierarchy of the given ontology and 
	 * remove any cycles (compute equivalence
	 * classes). Then for each equivalent set (of classes/roles) it will keep
	 * only one representative and replace reference to any other node in the
	 * equivalence set with reference to the representative. The equivalences
	 * will be kept in an equivalence map, that relates classes/properties with
	 * its equivalent. Note that the equivalent of a class can only be another
	 * class, an the equivalent of a property can be another property, or the
	 * inverse of a property.
	 * 
	 *
	 */
	public void optimize() {
		TBoxReasonerImpl reasoner= new TBoxReasonerImpl(tbox, false);
		DAGImpl impliedDAG = reasoner.getDAG();

//		try {
//			GraphGenerator.dumpISA(impliedDAG, "input");
//		} catch (IOException e) {
////			e.printStackTrace();
//		}

		/*
		 * Processing all properties
		 */
		// Collection<Predicate> removedProperties = new HashSet<Predicate>();

		Collection<Description> props = new HashSet<Description>(impliedDAG.vertexSet());
		HashSet<Property> removedNodes = new HashSet<Property>();

		for (Description desc : props) {
			if (!(desc instanceof Property))
				continue;

			if (removedNodes.contains((Property) desc))
				continue;

//			Description rep=impliedDAG.getReplacements().get(desc); //check that the node is still the representative node
//			if(rep!=null)
//				desc=rep;
			Property prop = (Property) desc;	
			
		/*
		 * Clearing the equivalences of the domain and ranges of the cycle's
		 * head
		 */
		
		Description propNodeDomain =ofac.createPropertySomeRestriction(prop.getPredicate(), prop.isInverse());

		Description propNodeRange = ofac.createPropertySomeRestriction(prop.getPredicate(), !prop.isInverse());
		
		if (impliedDAG.containsVertex(propNodeDomain) & impliedDAG.getMapEquivalences().get(propNodeDomain)!=null )
			impliedDAG.getMapEquivalences().remove(propNodeDomain);
		if (impliedDAG.containsVertex(propNodeRange) & impliedDAG.getMapEquivalences().get(propNodeRange)!=null )
			impliedDAG.getMapEquivalences().remove(propNodeRange);
		

			Collection<Description> equivalents = reasoner.getEquivalences(prop, false);
			if(equivalents.size()>1)
			for (Description equivalent : equivalents) {
				if (removedNodes.contains(equivalent)|| equivalent.equals(prop)) {
					
					continue;
				}

				/*
				 * each of the equivalents is redundant, we need to deal with
				 * them and with their inverses!
				 */

				Property equiProp = (Property) equivalent;
				Property inverseProp = ofac.createProperty(prop.getPredicate(), !prop.isInverse());
				if(equiProp.equals(inverseProp))
					continue;

				if (equiProp.isInverse()) {
					/*
					 * We need to invert the equivalent and the good property
					 */			
					
					equivalenceMap.put(equiProp.getPredicate(), inverseProp);
				} else {
					equivalenceMap.put(equiProp.getPredicate(), prop);
				}

				
				/*
				 * Dealing with the inverses
				 * 
				 * We need to get the inverse node of the redundant one and
				 * remove it, replacing pointers to the node of the inverse
				 * current non redundant predicate
				 */

				Description nonredundandPropNodeInv = ofac.createProperty(prop.getPredicate(), !prop.isInverse());

				Description redundandEquivPropNodeInv = 
						ofac.createProperty(equiProp.getPredicate(), !equiProp.isInverse());
				

				if (impliedDAG.containsVertex(redundandEquivPropNodeInv)) {
					impliedDAG.addVertex(nonredundandPropNodeInv); //choose the representative inverse
					
//					for (Description child : redundandEquivPropNodeInv.getChildren()) {
					for (Set<Description> children : reasoner.getDirectChildren(redundandEquivPropNodeInv, false)) {
//						for(Description child:children){
						Description firstChild=children.iterator().next();
						Description child= impliedDAG.getReplacements().get(firstChild);
							if(child==null)
								child=firstChild;
							impliedDAG.removeAllEdges(child, redundandEquivPropNodeInv);
							impliedDAG.addEdge(child, nonredundandPropNodeInv);
						
					}

					for (Set<Description> parents : reasoner.getDirectParents(redundandEquivPropNodeInv, false)) {
//						for(Description parent:parents){
							Description firstParent=parents.iterator().next();
							Description parent= impliedDAG.getReplacements().get(firstParent);
							if(parent==null)
								parent=firstParent;
							impliedDAG.removeAllEdges(parent, redundandEquivPropNodeInv);
							impliedDAG.addEdge(nonredundandPropNodeInv, parent);
						
					}
					
					
					
					impliedDAG.removeVertex(redundandEquivPropNodeInv);
					
					removedNodes.add((Property) redundandEquivPropNodeInv);
					
					//assign the new representative to the equivalent nodes
					for(Description equivInverse: impliedDAG.getMapEquivalences().get(nonredundandPropNodeInv)){
						if(equivInverse.equals(nonredundandPropNodeInv)){
							impliedDAG.getReplacements().remove(nonredundandPropNodeInv);
							continue;
						}
						impliedDAG.getReplacements().put(equivInverse, nonredundandPropNodeInv);
					}
					
					impliedDAG.getMapEquivalences().remove(redundandEquivPropNodeInv);

					
//					removedNodes.add((Property) redundandEquivPropNodeInv);
//					impliedDAG.removeVertex(redundandEquivPropNodeInv);
				}

				removedNodes.add((Property) equivalent);

				
				
				impliedDAG.removeVertex(equivalent);

			}
			// We clear all equivalents!
			

			impliedDAG.getMapEquivalences().remove(prop);

		}

		/*
		 * Replacing any \exists R for \exists S, in case R was replaced by S
		 * due to an equivalence
		 */

		for (Property prop : removedNodes) {
			if (prop.isInverse())
				continue;
			Predicate redundantProp = prop.getPredicate();
			Property equivalent = (Property) equivalenceMap.get(redundantProp);

			/* first for the domain */
			Description directRedundantNode = ofac.getPropertySomeRestriction(redundantProp, false);
			boolean containsNodeDomain=  impliedDAG.containsVertex(directRedundantNode);
			if (containsNodeDomain) {
				Description equivalentNode = ofac.getPropertySomeRestriction(equivalent.getPredicate(),
						equivalent.isInverse());
				impliedDAG.addVertex(equivalentNode); 
				if(impliedDAG.getMapEquivalences().get(equivalentNode)!= null)
				{
					Set<Description> equivalences = impliedDAG.getMapEquivalences().get(equivalentNode);
					equivalences.remove(directRedundantNode);
					
					impliedDAG.getMapEquivalences().put(equivalentNode, equivalences);
				}

				
					for (Set<Description> children : reasoner.getDirectChildren(directRedundantNode, false)) {
						Description firstChild=children.iterator().next();
						Description child= impliedDAG.getReplacements().get(firstChild);
							if(child==null)
								child=firstChild;
						if (!reasoner.getDirectChildren(equivalentNode, false).contains(child)) {
							impliedDAG.addEdge(child, equivalentNode);
						}
						
					}

					for (Set<Description> parents : reasoner.getDirectParents(directRedundantNode,false)) {
						Description firstParent=parents.iterator().next();
						Description parent= impliedDAG.getReplacements().get(firstParent);
						if(parent==null)
							parent=firstParent;
						if (!reasoner.getDirectParents(equivalentNode,false).contains(parent)) {
							impliedDAG.addEdge(equivalentNode, parent);
						}
						
					}
				
				
				
				
				for(Description equivInverse: impliedDAG.getMapEquivalences().get(directRedundantNode)){
					if(equivInverse.equals(equivalentNode)){
							impliedDAG.getReplacements().remove(equivalentNode);
						continue;
					}
					impliedDAG.getReplacements().put(equivInverse,equivalentNode);
					
				}
				impliedDAG.getReplacements().remove(directRedundantNode);
				impliedDAG.removeVertex(directRedundantNode);
								

//				equivalentNode.getDescendants().remove(directRedundantNode);
			}

			/* Now for the range */

			directRedundantNode = ofac.getPropertySomeRestriction(redundantProp, true);
			boolean  containsNodeRange = impliedDAG.containsVertex(directRedundantNode);
			if (containsNodeRange) {
				Description equivalentNode = ofac.getPropertySomeRestriction(equivalent.getPredicate(),
						!equivalent.isInverse());
				impliedDAG.addVertex(equivalentNode);
				
				if(impliedDAG.getMapEquivalences().get(equivalentNode)!= null){
					Set<Description> equivalences = impliedDAG.getMapEquivalences().get(equivalentNode);
					equivalences.remove(directRedundantNode);
					
					impliedDAG.getMapEquivalences().put(equivalentNode, equivalences);
				}
				

				
					for (Set<Description> children : reasoner.getDirectChildren(directRedundantNode, false)) {
					Description firstChild=children.iterator().next();
						Description child= impliedDAG.getReplacements().get(firstChild);
						if(child==null)
							child=firstChild;
						if (!reasoner.getDirectChildren(equivalentNode, false).contains(child)) {
							impliedDAG.addEdge(child, equivalentNode);
						}
						
					}

					for (Set<Description> parents : reasoner.getDirectParents(directRedundantNode,false)) {
						Description firstParent=parents.iterator().next();
						Description parent= impliedDAG.getReplacements().get(firstParent);
						if(parent==null)
							parent=firstParent;
						if (!reasoner.getDirectParents(equivalentNode,false).contains(parent)) {
							impliedDAG.addEdge(equivalentNode, parent);
						}
						
					}
				
		

				
				for(Description equivInverse: impliedDAG.getMapEquivalences().get(directRedundantNode)){
					if(equivInverse.equals(equivalentNode)){
						impliedDAG.getReplacements().remove(equivalentNode);
						continue;}
					impliedDAG.getReplacements().put(equivInverse, equivalentNode);
				}
				impliedDAG.getReplacements().remove(directRedundantNode);
				impliedDAG.removeVertex(directRedundantNode);

//				equivalentNode.getDescendants().remove(directRedundantNode);
			}
		}
		
	
		/*
		 * Processing all classes
		 */
		Collection<OClass> classNodes = impliedDAG.getClasses();
		for (OClass classNode : classNodes) {
			if(impliedDAG.getReplacements().containsKey(classNode))
				continue;
			OClass classDescription = (OClass) classNode;

			Collection<Description> redundantClasses = new LinkedList<Description>();
			Collection<Description> replacements = new HashSet<Description>();

			for (Description equivalentNode : reasoner.getEquivalences(classNode, false)) {
				if(equivalentNode.equals(classNode))
					continue;
				Description descEquivalent = equivalentNode;
				if (descEquivalent instanceof OClass) {
					/*
					 * Its a named class, we need to remove it
					 */
					OClass equiClass = (OClass) descEquivalent;
					equivalenceMap.put(equiClass.getPredicate(), classDescription);
					redundantClasses.add(equivalentNode);
				} else {

					/*
					 * Its an \exists R, we need to make sure that it references
					 * to the proper vocabulary
					 */
					PropertySomeRestriction existsR = (PropertySomeRestriction) descEquivalent;
					Property prop = ofac.createProperty(existsR.getPredicate());
					Property equiProp = (Property) equivalenceMap.get(prop);
					if (equiProp == null)
						continue;

					/*
					 * This \exists R indeed referes to a property that was
					 * removed in the previous step and replace for some other
					 * property S. we need to eliminate the \exists R, and add
					 * an \exists S
					 */
					PropertySomeRestriction replacement = ofac.createPropertySomeRestriction(equiProp.getPredicate(), equiProp.isInverse());
					redundantClasses.add(equivalentNode);

					replacements.add(replacement);
				}
			}

			if(impliedDAG.getMapEquivalences().get(classNode)!=null)
			{
				Set<Description> equivalences = impliedDAG.getMapEquivalences().get(classNode);
				equivalences.removeAll(redundantClasses);
				impliedDAG.getMapEquivalences().put(classNode, equivalences);
			}
			

			
			for (Description replacement : replacements){
				
				Set<Description> equivalences = impliedDAG.getMapEquivalences().get(classNode);
				equivalences.add(replacement);
				impliedDAG.getMapEquivalences().put(classNode, equivalences);
				impliedDAG.getReplacements().put(replacement, classNode);
			}

		}

//		try {
//			GraphGenerator.dumpISA(impliedDAG, "output");
//		} catch (IOException e) {
////			e.printStackTrace();
//		}
		/*
		 * Done with the simplificatino of the vocabulary, now we create the
		 * optimized ontology
		 */

		optimalTBox = ofac.createOntology();
		

		for(Description node:impliedDAG.vertexSet()){
			for (Set<Description> descendants: reasoner.getDescendants(node, false)){
					Description firstDescendant=descendants.iterator().next();
					Description descendant= impliedDAG.getReplacements().get(firstDescendant);
					if(descendant==null)
						descendant=firstDescendant;
					Axiom axiom = null;
					if(!descendant.equals(node)){
					/*
					 * Creating subClassOf or subPropertyOf axioms
					 */
					if (descendant instanceof ClassDescription) {
						axiom = ofac.createSubClassAxiom((ClassDescription) descendant, (ClassDescription) node
								);
					} else {
;
						axiom = ofac
								.createSubPropertyAxiom((Property) descendant, (Property) node);
					}
					optimalTBox.addEntities(axiom.getReferencedEntities());
					optimalTBox.addAssertion(axiom);
					}
				
				
			}
			for(Description equivalent:reasoner.getEquivalences(node, false)){
				if(!equivalent.equals(node)){
					Axiom axiom = null;
					if (node instanceof ClassDescription) {
						axiom = ofac.createSubClassAxiom((ClassDescription) node, (ClassDescription) equivalent);
						
					} else {
						
						axiom = ofac.createSubPropertyAxiom((Property) node, (Property) equivalent);
						

					}
					optimalTBox.addEntities(axiom.getReferencedEntities());
					optimalTBox.addAssertion(axiom);
					
					if (equivalent instanceof ClassDescription) {
						axiom = ofac.createSubClassAxiom((ClassDescription) equivalent, (ClassDescription) node);
						
					} else{
						axiom = ofac.createSubPropertyAxiom((Property) equivalent, (Property) node);
						
					}
					optimalTBox.addEntities(axiom.getReferencedEntities());
					optimalTBox.addAssertion(axiom);
					}
				}
			
			
		}

		//
		/*
		 * Last, we add references to all the vocabulary of the previous TBox
		 */
		Set<Predicate> extraVocabulary = new HashSet<Predicate>();
		extraVocabulary.addAll(tbox.getVocabulary());
		extraVocabulary.removeAll(equivalenceMap.keySet());
		optimalTBox.addEntities(extraVocabulary);

	}

	public Ontology getOptimalTBox() {
		return this.optimalTBox;
	}

	public Map<Predicate, Description> getEquivalenceMap() {
		return this.equivalenceMap;
	}
}
