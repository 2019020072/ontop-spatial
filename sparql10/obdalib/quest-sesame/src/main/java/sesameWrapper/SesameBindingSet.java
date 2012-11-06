package sesameWrapper;

import it.unibz.krdb.obda.model.BNode;
import it.unibz.krdb.obda.model.Constant;
import it.unibz.krdb.obda.model.OBDAResultSet;
import it.unibz.krdb.obda.model.Predicate.COL_TYPE;
import it.unibz.krdb.obda.model.URIConstant;
import it.unibz.krdb.obda.model.ValueConstant;
import it.unibz.krdb.obda.model.impl.OBDAVocabulary;
import it.unibz.krdb.obda.owlrefplatform.core.resultset.QuestResultset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.impl.BindingImpl;

public class SesameBindingSet implements BindingSet {

	private static final long serialVersionUID = -8455466574395305166L;
	private QuestResultset set = null;
	private int count = 0;
	private ValueFactory fact = new ValueFactoryImpl();

	public SesameBindingSet(OBDAResultSet set) {
		this.set = (QuestResultset) set;
		try {
			this.count = set.getColumCount();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Binding getBinding(String bindingName) {
		// return the Binding with bindingName
		return createBinding(bindingName);
	}

	public Set<String> getBindingNames() {
		try {

			List<String> sign = set.getSignature();
			Set<String> bnames = new HashSet<String>(sign.size());
			for (String s : sign)
				bnames.add(s);
			return bnames;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Value getValue(String bindingName) {
		return createBinding(bindingName).getValue();
	}

	private Binding createBinding(String bindingName) {
		Value value = null;
		try {

			if (hasBinding(bindingName)) {
				int column = set.getSignature().indexOf(bindingName) + 1;
				Constant c = set.getConstant(bindingName);
				if (c instanceof BNode)
					value = fact.createBNode(((BNode) c).getName());
				else if (c instanceof URIConstant)
					value = fact.createURI(((URIConstant) c).getURI().toString());
				else if (c instanceof ValueConstant) {
					ValueConstant literal = set.getLiteral(column);
					URI datatype = null;
					COL_TYPE col_type = literal.getType();
					String obdavoc = "";
					if (col_type == COL_TYPE.BOOLEAN)
						obdavoc = (OBDAVocabulary.XSD_BOOLEAN_URI);
					else if (col_type == COL_TYPE.DATETIME)
						obdavoc = (OBDAVocabulary.XSD_DATETIME_URI);
					else if (col_type == COL_TYPE.DECIMAL)
						obdavoc = (OBDAVocabulary.XSD_DECIMAL_URI);
					else if (col_type == COL_TYPE.DOUBLE)
						obdavoc = (OBDAVocabulary.XSD_DOUBLE_URI);
					else if (col_type == COL_TYPE.INTEGER)
						obdavoc = (OBDAVocabulary.XSD_INTEGER_URI);
					else if (col_type == COL_TYPE.LITERAL)
						obdavoc = (OBDAVocabulary.RDFS_LITERAL_URI);
					else if (col_type == COL_TYPE.OBJECT)
						obdavoc = (OBDAVocabulary.XSD_STRING_URI);
					else if (col_type == COL_TYPE.STRING)
						obdavoc = OBDAVocabulary.XSD_STRING_URI;
						
					datatype = fact.createURI(obdavoc);
					value = fact.createLiteral(literal.getValue(), datatype);
				}
			}

			return new BindingImpl(bindingName, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean hasBinding(String bindingName) {

		try {
			return set.getSignature().contains(bindingName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Iterator<Binding> iterator() {

		List<Binding> allBindings = new LinkedList<Binding>();
		List<String> bindings;
		try {
			bindings = set.getSignature();
			for (String s : bindings)
				allBindings.add(createBinding(s));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allBindings.iterator();
	}

	public int size() {
		return count;
	}

}
