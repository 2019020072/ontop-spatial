package it.unibz.krdb.obda.owlrefplatform.core.reformulation;

import it.unibz.krdb.obda.model.Atom;
import it.unibz.krdb.obda.model.Term;
import it.unibz.krdb.obda.model.impl.BooleanOperationPredicateImpl;
import it.unibz.krdb.obda.ontology.BasicClassDescription;
import it.unibz.krdb.obda.ontology.Property;
import it.unibz.krdb.obda.owlrefplatform.core.reformulation.QueryConnectedComponent.Edge;
import it.unibz.krdb.obda.owlrefplatform.core.reformulation.QueryConnectedComponent.Loop;
import it.unibz.krdb.obda.owlrefplatform.core.reformulation.TreeWitnessReasonerLite.IntersectionOfConceptSets;
import it.unibz.krdb.obda.owlrefplatform.core.reformulation.TreeWitnessReasonerLite.IntersectionOfProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeWitnessSet {
	private List<TreeWitness> tws = new LinkedList<TreeWitness>();
	private final QueryConnectedComponent cc;
	private final TreeWitnessReasonerLite reasoner;
	private PropertiesCache propertiesCache; 
	
	// working lists (may all be nulls)
	private List<TreeWitness> mergeable;
	private Queue<TreeWitness> delta;
	private Map<TreeWitness.TermCover, TreeWitness> twsCache;

	private static final Logger log = LoggerFactory.getLogger(TreeWitnessSet.class);
	
	private TreeWitnessSet(QueryConnectedComponent cc, TreeWitnessReasonerLite reasoner) {
		this.cc = cc;
		this.reasoner = reasoner;
	}
	
	public Collection<TreeWitness> getTWs() {
		return tws;
	}
	
	public static TreeWitnessSet getTreeWitnesses(QueryConnectedComponent cc, TreeWitnessReasonerLite reasoner) {		
		TreeWitnessSet treewitnesses = new TreeWitnessSet(cc, reasoner);
		
		if (!cc.isDegenerate())
			treewitnesses.computeTreeWitnesses();
				
		return treewitnesses;
	}

	private void computeTreeWitnesses() {		
		propertiesCache = new PropertiesCache(reasoner);
		QueryFolding qf = new QueryFolding(propertiesCache); // in-place query folding, so copying is required when creating a tree witness
		
		for (Loop loop : cc.getQuantifiedVariables()) {
			Term v = loop.getTerm();
			log.debug("QUANTIFIED VARIABLE " + v); 
			qf.newOneStepFolding(v);
			
			for (Edge edge : cc.getEdges()) { // loop.getAdjacentEdges()
				if (edge.getTerm0().equals(v)) {
					if (!qf.extend(edge.getLoop1(), edge, loop))
						break;
				}
				else if (edge.getTerm1().equals(v)) {
					if (!qf.extend(edge.getLoop0(), edge, loop))
						break;
				}
			}
			
			if (qf.isValid()) {
				// tws cannot contain duplicates by construction, so no caching (even negative)
				Collection<TreeWitnessGenerator> twg = getTreeWitnessGenerators(qf); 
				if (twg != null) { 
					// no need to copy the query folding: it creates all temporary objects anyway (including terms)
					tws.add(qf.getTreeWitness(twg));
				}
			}
		}		
		
		if (tws.isEmpty())
			return;
		
		mergeable = new ArrayList<TreeWitness>();
		Queue<TreeWitness> working = new LinkedList<TreeWitness>();

		for (TreeWitness tw : tws) 
			if (tw.isMergeable())  {
				working.add(tw);			
				mergeable.add(tw);
			}
		
		delta = new LinkedList<TreeWitness>();
		twsCache = new HashMap<TreeWitness.TermCover, TreeWitness>();

		while (!working.isEmpty()) {
			while (!working.isEmpty()) {
				TreeWitness tw = working.poll(); 
				qf.newQueryFolding(tw);
				saturateTreeWitnesses(qf); 					
			}
			
			while (!delta.isEmpty()) {
				TreeWitness tw = delta.poll();
				tws.add(tw);
				if (tw.isMergeable())  {
					working.add(tw);			
					mergeable.add(tw);
				}
			}
		}				

		log.debug("TREE WITNESSES FOUND: " + tws.size());
		for (TreeWitness tw : tws) 
			log.debug(" " + tw);
	}
	
	private void saturateTreeWitnesses(QueryFolding qf) { 
		boolean saturated = true; 
		
		for (Edge edge : cc.getEdges()) { 
			Loop rootLoop = edge.getLoop0();
			Loop internalLoop = edge.getLoop1();
			if (qf.canBeAttachedToAnInternalRoot(rootLoop, internalLoop)) {
				// ok
			}
			else if (qf.canBeAttachedToAnInternalRoot(internalLoop, rootLoop)) { 
				rootLoop = internalLoop;
				internalLoop = edge.getLoop0();
			}
			else
				continue;
			
			log.debug("EDGE " + edge + " IS ADJACENT TO THE TREE WITNESS " + qf); 

			saturated = false; 

			Term rootTerm = rootLoop.getTerm();
			Term internalTerm = internalLoop.getTerm();
			for (TreeWitness tw : mergeable)  
				if (tw.getRoots().contains(rootTerm) && tw.getDomain().contains(internalTerm)) {
					log.debug("    ATTACHING A TREE WITNESS " + tw);
					saturateTreeWitnesses(qf.extend(tw)); 
				} 
			
			QueryFolding qf2 = new QueryFolding(qf);
			if (qf2.extend(internalLoop, edge, rootLoop)) {
				log.debug("    ATTACHING A HANDLE " + edge);
				saturateTreeWitnesses(qf2);  
			}	
		}

		if (saturated && qf.hasRoot())  {
			if (!twsCache.containsKey(qf.getTerms())) {
				Collection<TreeWitnessGenerator> twg = getTreeWitnessGenerators(qf); 
				if (twg != null) {
					TreeWitness tw = qf.getTreeWitness(twg); 
					delta.add(tw);
					twsCache.put(tw.getTerms(), tw);
				}
				else
					twsCache.put(qf.getTerms(), null); // cache negative
			}
			else {
				log.debug("TWS CACHE HIT " + qf.getTerms());
			}
		}
	}
	
	// can return null if there are no applicable generators!
	
	private Collection<TreeWitnessGenerator> getTreeWitnessGenerators(QueryFolding qf) {
		Collection<TreeWitnessGenerator> twg = null;
		log.debug("CHECKING WHETHER THE FOLDING " + qf + " CAN BE GENERATED: "); 
		for (TreeWitnessGenerator g : reasoner.getGenerators()) {
			log.debug("      CHECKING " + g);		
			if (qf.getProperties().contains(g.getProperty())) 
				log.debug("        PROPERTIES ARE FINE: " + qf.getProperties() + " FOR " + g.getProperty());
			else {
				log.debug("        PROPERTIES ARE TOO SPECIFIC: " + qf.getProperties() + " FOR " + g.getProperty());
				continue;
			}

			Set<BasicClassDescription> subc = qf.getInternalRootConcepts();
			if ((subc == null) || g.endPointEntailsAnyOf(subc)) 
				 log.debug("        ENDTYPE IS FINE: " + subc + " FOR " + g);
			else {
				 log.debug("        ENDTYPE TOO SPECIFIC: " + subc + " FOR " + g);
				 continue;			
			}

			boolean failed = false;
			for (TreeWitness tw : qf.getInteriorTreeWitnesses()) 
				if (g.endPointEntailsAnyOf(reasoner.getSubConceptsForGenerators(tw.getGenerators()))) 
					log.debug("        ENDTYPE IS FINE: " + tw + " FOR " + g);
				else { 
					log.debug("        ENDTYPE TOO SPECIFIC: " + tw + " FOR " + g);
					failed = true;
					break;
				}
			if (failed)
				continue;
			
			if (twg == null) 
				twg = new LinkedList<TreeWitnessGenerator>();
			twg.add(g);
			log.debug("        GENERATOR: " + g);
		}
		return twg;
	}
	
	
	static class PropertiesCache {
		private Map<TermOrderedPair, Set<Property>> propertiesCache = new HashMap<TermOrderedPair, Set<Property>>();
		private Map<Term, IntersectionOfConceptSets> conceptsCache = new HashMap<Term, IntersectionOfConceptSets>();

		private final TreeWitnessReasonerLite reasoner;
		
		private PropertiesCache(TreeWitnessReasonerLite reasoner) {
			this.reasoner = reasoner;
		}
		
		public IntersectionOfConceptSets getLoopConcepts(Loop loop) {
			Term t = loop.getTerm();
			IntersectionOfConceptSets subconcepts = conceptsCache.get(t); 
			if (subconcepts == null) {				
				subconcepts = reasoner.getSubConcepts(loop.getAtoms());
				conceptsCache.put(t, subconcepts);	
			}
			return subconcepts;
		}
		
		
		public Set<Property> getEdgeProperties(Edge edge, Term root, Term nonroot) {
			TermOrderedPair idx = new TermOrderedPair(root, nonroot);
			Set<Property> properties = propertiesCache.get(idx);			
			if (properties == null) {
				for (Atom a : edge.getBAtoms()) {
					if (a.getPredicate() instanceof BooleanOperationPredicateImpl) {
						log.debug("EDGE " + edge + " HAS PROPERTY " + a + " NO BOOLEAN OPERATION PREDICATES ALLOWED IN PROPERTIES ");
						properties = Collections.EMPTY_SET;
						break;
					}
				}
				if (properties == null) {
					IntersectionOfProperties set = new IntersectionOfProperties();
					for (Atom a : edge.getBAtoms()) {
						log.debug("EDGE " + edge + " HAS PROPERTY " + a);
						if (!set.intersect(reasoner.getSubProperties(a.getPredicate(), !root.equals(a.getTerm(0)))))
							break;
					}
					properties = set.get();
				}	
				propertiesCache.put(idx, properties); // edge.getTerms()
			}
			return properties;
		}
	}
	
	private static class TermOrderedPair {
		private final Term t0, t1;
		private final int hashCode;

		public TermOrderedPair(Term t0, Term t1) {
			this.t0 = t0;
			this.t1 = t1;
			this.hashCode = t0.hashCode() ^ (t1.hashCode() << 4);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof TermOrderedPair) {
				TermOrderedPair other = (TermOrderedPair) o;
				return (this.t0.equals(other.t0) && this.t1.equals(other.t1));
			}
			return false;
		}

		@Override
		public String toString() {
			return "term pair: (" + t0 + ", " + t1 + ")";
		}
		
		@Override
		public int hashCode() {
			return hashCode;
		}
	}	
	

	public Set<TreeWitnessGenerator> getGeneratorsOfDetachedCC() {		
		Set<TreeWitnessGenerator> generators = new HashSet<TreeWitnessGenerator>();
		
		if (cc.isDegenerate()) { // do not remove the curly brackets -- dangling else otherwise
			IntersectionOfConceptSets subc = reasoner.getSubConcepts(cc.getLoop().getAtoms());
			log.debug("DEGENERATE DETACHED COMPONENT: " + cc);
			if (!subc.isEmpty()) // (subc == null) || 
				for (TreeWitnessGenerator twg : reasoner.getGenerators()) {
					if ((subc.get() == null) || twg.endPointEntailsAnyOf(subc.get())) {
						log.debug("        ENDTYPE IS FINE: " + subc + " FOR " + twg);
						generators.add(twg);					
					}
					else 
						 log.debug("        ENDTYPE TOO SPECIFIC: " + subc + " FOR " + twg);
				}
		} 
		else {
			for (TreeWitness tw : tws) 
				if (tw.getDomain().containsAll(cc.getVariables())) {
					log.debug("TREE WITNESS " + tw + " COVERS THE QUERY");
					IntersectionOfConceptSets subc = reasoner.getSubConcepts(tw.getRootAtoms());
					if (!subc.isEmpty())
						for (TreeWitnessGenerator twg : reasoner.getGenerators())
							if ((subc.get() == null) || twg.endPointEntailsAnyOf(subc.get())) {
								log.debug("        ENDTYPE IS FINE: " + subc + " FOR " + twg);
								if (twg.endPointEntailsAnyOf(reasoner.getSubConceptsForGenerators(tw.getGenerators()))) {
									log.debug("        ENDTYPE IS FINE: " + tw + " FOR " + twg);
									generators.add(twg);					
								}
								else  
									log.debug("        ENDTYPE TOO SPECIFIC: " + tw + " FOR " + twg);
							}
							else 
								 log.debug("        ENDTYPE TOO SPECIFIC: " + subc + " FOR " + twg);
				}
		}
		
		if (!generators.isEmpty()) {
			boolean saturated = false;
			while (!saturated) {
				saturated = true;
				Set<BasicClassDescription> subc = reasoner.getSubConceptsForGenerators(generators); 
				for (TreeWitnessGenerator g : reasoner.getGenerators()) 
					if (g.endPointEntailsAnyOf(subc)) {
						if (generators.add(g))
							saturated = false;
					}		 		
			} 									
		}
		
		return generators;
	}
	
	
}
