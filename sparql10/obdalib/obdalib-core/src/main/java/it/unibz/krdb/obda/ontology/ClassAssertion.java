package it.unibz.krdb.obda.ontology;

import it.unibz.krdb.obda.model.ObjectConstant;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.URIConstant;

/***
 * A data assertion for a class, e.g., Person(mariano), where mariano is an
 * object identifier.
 * 
 * These also correspond to rdf:type assertions, e.g., :mariano rdf:type
 * :Person.
 * 
 * @author Mariano Rodriguez Muro
 * 
 */
public interface ClassAssertion extends Assertion {

	public ObjectConstant getObject();

	/***
	 * Use get predicate instead
	 * 
	 * @return
	 */
	@Deprecated
	public Predicate getConcept();
}
