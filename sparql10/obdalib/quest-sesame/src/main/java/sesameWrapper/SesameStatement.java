package sesameWrapper;

import it.unibz.krdb.obda.model.BNode;
import it.unibz.krdb.obda.model.Constant;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.URIConstant;
import it.unibz.krdb.obda.model.ValueConstant;
import it.unibz.krdb.obda.model.Predicate.COL_TYPE;
import it.unibz.krdb.obda.model.impl.OBDAVocabulary;
import it.unibz.krdb.obda.ontology.Assertion;
import it.unibz.krdb.obda.ontology.BinaryAssertion;
import it.unibz.krdb.obda.ontology.ClassAssertion;
import it.unibz.krdb.obda.ontology.UnaryAssertion;

import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;

public class SesameStatement implements Statement {

	private Resource subject = null;
	private URI predicate = null;
	private Value object = null;
	private Resource context = null;
	private ValueFactory fact = new ValueFactoryImpl();

	public SesameStatement(Assertion assertion) {
		
		Constant subj;

		if (assertion instanceof BinaryAssertion) {
			//object or data property assertion
			BinaryAssertion ba = (BinaryAssertion) assertion;
			subj = ba.getValue1();
			Predicate pred = ba.getPredicate();
			Constant obj = ba.getValue2();
			
			// convert string into respective type
			if (subj instanceof BNode)
				subject = fact.createBNode(((BNode) subj).getName());
			else if (subj instanceof URIConstant)
				subject = fact.createURI(subj.getValue());
			else if (subj instanceof ValueConstant)
				throw new RuntimeException("Invalid ValueConstant as subject!");
			
			predicate = fact.createURI(pred.getName().toString()); // URI
			
			if (obj instanceof BNode)
				object = fact.createBNode(((BNode) obj).getName());
			else if (obj instanceof URIConstant)
				object = fact.createURI(obj.getValue());
			else if (obj instanceof ValueConstant)
				object = getLiteral((ValueConstant)obj);
			
			
		} else if (assertion instanceof UnaryAssertion) { 
			//class assertion
			UnaryAssertion ua = (UnaryAssertion) assertion;
			subj = ua.getValue();
			String pred = OBDAVocabulary.RDF_TYPE;
			Predicate obj = ua.getPredicate();
			
			// convert string into respective type
			if (subj instanceof BNode)
				subject = fact.createBNode(((BNode) subj).getName());
			else if (subj instanceof URIConstant)
				subject = fact.createURI(subj.getValue());
			else if (subj instanceof ValueConstant)
				throw new RuntimeException("Invalid ValueConstant as subject!");
			
			predicate = fact.createURI(pred); // URI
		
			object = fact.createURI(obj.getName().toString());
			
		}
	}
	
	private Literal getLiteral(ValueConstant literal)
	{
		URI datatype = null;
		if (literal.getType() == COL_TYPE.BOOLEAN)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_BOOLEAN_URI);
		else if (literal.getType() == COL_TYPE.DATETIME)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_DATETIME_URI);
		else if (literal.getType() == COL_TYPE.DECIMAL)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_DECIMAL_URI);
		else if (literal.getType() == COL_TYPE.DOUBLE)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_DOUBLE_URI);
		else if (literal.getType() == COL_TYPE.INTEGER)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_INTEGER_URI);
		else if (literal.getType() == COL_TYPE.LITERAL)
			datatype = fact
					.createURI(OBDAVocabulary.RDFS_LITERAL_URI);
		else if (literal.getType() == COL_TYPE.OBJECT)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_STRING_URI);
		else if (literal.getType() == COL_TYPE.STRING)
			datatype = fact
					.createURI(OBDAVocabulary.XSD_STRING_URI);
		Literal value = fact.createLiteral(literal.getValue(), datatype);
		return value;
	}

	public Resource getSubject() {
		return subject;
	}

	public URI getPredicate() {
		return predicate;
	}

	public Value getObject() {
		return object;
	}

	public Resource getContext() {
		// TODO Auto-generated method stub
		return context;
	}

	@Override
	public String toString()
	{
		return "("+subject+", "+predicate+", "+object+")";
	}
}
