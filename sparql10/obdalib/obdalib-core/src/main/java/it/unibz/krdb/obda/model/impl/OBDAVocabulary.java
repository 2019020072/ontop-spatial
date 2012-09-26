package it.unibz.krdb.obda.model.impl;

import it.unibz.krdb.obda.model.Constant;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.Predicate.COL_TYPE;

import java.net.URI;

public class OBDAVocabulary {
	
	/* Constants */
	
	public static final Constant NULL = new ValueConstantImpl("null", COL_TYPE.STRING);
	
	public static final Constant TRUE = new ValueConstantImpl("t", COL_TYPE.BOOLEAN);
	
	public static final Constant FALSE = new ValueConstantImpl("f", COL_TYPE.BOOLEAN);

	/* Boolean predicate URIs */

	public static final String strAND = "AND";

	public static final String strEQ = "EQ";

	public static final String strGTE = "GTE";

	public static final String strGT = "GT";

	public static final String strLTE = "LTE";

	public static final String strLT = "LT";

	public static final String strNEQ = "NEQ";

	public static final String strNOT = "NOT";

	public static final String strOR = "OR";

	public static final String strIS_NULL = "IS_NULL";

	public static final String strIS_NOT_NULL = "IS_NOT_NULL";

	/* Boolean predicates */

	public static final Predicate AND = new BooleanOperationPredicateImpl(URI.create(strAND), 2);

	public static final Predicate EQ = new BooleanOperationPredicateImpl(URI.create(strEQ), 2);

	public static final Predicate GTE = new BooleanOperationPredicateImpl(URI.create(strGTE), 2);

	public static final Predicate GT = new BooleanOperationPredicateImpl(URI.create(strGT), 2);

	public static final Predicate LTE = new BooleanOperationPredicateImpl(URI.create(strLTE), 2);

	public static final Predicate LT = new BooleanOperationPredicateImpl(URI.create(strLT), 2);

	public static final Predicate NEQ = new BooleanOperationPredicateImpl(URI.create(strNEQ), 2);

	public static final Predicate NOT = new BooleanOperationPredicateImpl(URI.create(strNOT), 1);

	public static final Predicate OR = new BooleanOperationPredicateImpl(URI.create(strOR), 2);

	public static final Predicate IS_NULL = new BooleanOperationPredicateImpl(URI.create(strIS_NULL), 1);

	public static final Predicate IS_NOT_NULL = new BooleanOperationPredicateImpl(URI.create(strIS_NOT_NULL), 1);

	public static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	
	/* Data type predicate URIs */

	public static final String RDFS_LITERAL_URI = "http://www.w3.org/2000/01/rdf-schema#Literal";

	public static final String XSD_STRING_URI = "http://www.w3.org/2001/XMLSchema#string";

	public static final String XSD_INT_URI = "http://www.w3.org/2001/XMLSchema#int";

	public static final String XSD_INTEGER_URI = "http://www.w3.org/2001/XMLSchema#integer";

	public static final String XSD_DECIMAL_URI = "http://www.w3.org/2001/XMLSchema#decimal";

	public static final String XSD_FLOAT_URI = "http://www.w3.org/2001/XMLSchema#float";

	public static final String XSD_DOUBLE_URI = "http://www.w3.org/2001/XMLSchema#double";

	public static final String XSD_DATETIME_URI = "http://www.w3.org/2001/XMLSchema#dateTime";

	public static final String XSD_BOOLEAN_URI = "http://www.w3.org/2001/XMLSchema#boolean";

	/* Data type predicates */

	public static final Predicate RDFS_LITERAL = new DataTypePredicateImpl(URI.create(RDFS_LITERAL_URI), new COL_TYPE[] { COL_TYPE.LITERAL,
			COL_TYPE.LITERAL });

	public static final Predicate XSD_STRING = new DataTypePredicateImpl(URI.create(XSD_STRING_URI), COL_TYPE.STRING);

	public static final Predicate XSD_INTEGER = new DataTypePredicateImpl(URI.create(XSD_INTEGER_URI), COL_TYPE.INTEGER);

	public static final Predicate XSD_DECIMAL = new DataTypePredicateImpl(URI.create(XSD_DECIMAL_URI), COL_TYPE.DECIMAL);

	public static final Predicate XSD_DOUBLE = new DataTypePredicateImpl(URI.create(XSD_DOUBLE_URI), COL_TYPE.DOUBLE);

	public static final Predicate XSD_DATETIME = new DataTypePredicateImpl(URI.create(XSD_DATETIME_URI), COL_TYPE.DATETIME);

	public static final Predicate XSD_BOOLEAN = new DataTypePredicateImpl(URI.create(XSD_BOOLEAN_URI), COL_TYPE.BOOLEAN);

	public static final Predicate[] QUEST_DATATYPE_PREDICATES = new Predicate[] { RDFS_LITERAL, XSD_STRING, XSD_INTEGER, XSD_DECIMAL,
			XSD_DOUBLE, XSD_DATETIME, XSD_BOOLEAN };

	/* Common namespaces and prefixes */

	public static final String NS_XSD = "http://www.w3.org/2001/XMLSchema#";

	public static final String NS_RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

	public static final String NS_RDFS = "http://www.w3.org/2000/01/rdf-schema#";

	public static final String NS_OWL = "http://www.w3.org/2002/07/owl#";

	public static final String NS_QUEST = "http://obda.org/quest#";

	public static final String PREFIX_XSD = "xsd:";

	public static final String PREFIX_RDF = "rdf:";

	public static final String PREFIX_RDFS = "rdfs:";

	public static final String PREFIX_OWL = "owl:";

	public static final String PREFIX_QUEST = "quest:";
	
	
	
	
	

	/* Built-in function URIs */

	// The name of the function that creates URI's in Quest
	public static final String QUEST_URI = "URI";

	// The name of the function that creates URI's in Quest
	public static final String QUEST_BNODE = "BNODE";
	
	public static final String QUEST_TRIPLE_STR = "triple";
	
	public static final URI QUEST_TRIPLE_URI = URI.create("triple");
	
	public static final Predicate QUEST_TRIPLE_PRED = new PredicateImpl(QUEST_TRIPLE_URI, 3, new COL_TYPE[3]);
	
	/* SPARQL Algebra vocabulary */
	
	public static final String SPARQL_JOIN_URI = "Join";
	
	public static final String SPARQL_LEFTJOIN_URI = "LeftJoin";
	
	public static final String SPARQL_LANGMATCHES_URI = "LangMatches";
	
	public static final Predicate SPARQL_JOIN = new AlgebraOperatorPredicateImpl(URI.create(SPARQL_JOIN_URI), COL_TYPE.STRING);
	
	public static final Predicate SPARQL_LEFTJOIN = new AlgebraOperatorPredicateImpl(URI.create(SPARQL_LEFTJOIN_URI), COL_TYPE.STRING);

	public static final Predicate SPARQL_LANGMATCHES = new BooleanOperationPredicateImpl(URI.create(SPARQL_LANGMATCHES_URI), 2);

	
	
}
