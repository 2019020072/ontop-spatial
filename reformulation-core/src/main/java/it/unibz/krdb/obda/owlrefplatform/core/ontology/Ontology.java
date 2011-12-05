package it.unibz.krdb.obda.owlrefplatform.core.ontology;

import it.unibz.krdb.obda.model.Predicate;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

public interface Ontology extends Cloneable, Serializable {

	public void addAssertion(Axiom assertion);

	public void addAssertions(Collection<Axiom> assertion);

	public void addConcept(Predicate c);

	public void addRole(Predicate role);

	public void addConcepts(Collection<Predicate> cd);

	public void addRoles(Collection<Predicate> rd);

	public Set<Predicate> getRoles();

	public Set<Predicate> getConcepts();
	
	public Set<Predicate> getVocabulary();

	public Set<Axiom> getAssertions();

	
	public boolean referencesPredicate(Predicate pred);

	
	public boolean referencesPredicates(Collection<Predicate> preds);

	/***
	 * This will retrun all the assertions whose right side concept description
	 * refers to the predicate 'pred'
	 * 
	 * @param pred
	 * @return
	 */
	public Set<SubDescriptionAxiom> getByIncluding(Predicate pred);

	/***
	 * As before but it will only return assetions where the right side is an
	 * existential role concept description
	 * 
	 * @param pred
	 * @param onlyAtomic
	 * @return
	 */
	public Set<SubDescriptionAxiom> getByIncludingExistOnly(Predicate pred);

	public Set<SubDescriptionAxiom> getByIncludingNoExist(Predicate pred);

	public Set<SubDescriptionAxiom> getByIncluded(Predicate pred);

	//
	// public Set<PositiveInclusion> getByIncludedExistOnly(Predicate pred);
	//
	// public Set<PositiveInclusion> getByIncludedNoExist(Predicate pred);

	public URI getUri();

	/***
	 * This will saturate the ontology, i.e. it will make sure that all axioms
	 * implied by this ontology are asserted in the ontology and accessible
	 * through the methods of the ontology.
	 */
	public void saturate();

	public Ontology clone();

	public void addEntities(Set<Predicate> referencedEntities);
}
