package it.unibz.krdb.obda.model.impl;

import it.unibz.krdb.obda.model.Atom;
import it.unibz.krdb.obda.model.Function;
import it.unibz.krdb.obda.model.NewLiteral;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.Variable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AtomWrapperImpl implements Atom {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1036381325035396595L;
	final Function f;

	public AtomWrapperImpl(Function f) {
		this.f = f;
	}

	@Override
	public List<NewLiteral> getTerms() {
		return f.getTerms();
	}

	@Override
	public Predicate getFunctionSymbol() {
		return f.getFunctionSymbol();
	}

	@Override
	public Predicate getPredicate() {
		return f.getPredicate();
	}

	@Override
	public int getArity() {
		return f.getArity();
	}

	@Override
	public int getFirstOcurrance(NewLiteral t, int i) {
		return f.getFirstOcurrance(t, i);
	}

	@Override
	public NewLiteral getTerm(int index) {
		return f.getTerm(index);
	}

	@Override
	public void setTerm(int index, NewLiteral term) {
		f.setTerm(index, term);
	}

	@Override
	public Set<Variable> getVariables() {
		return f.getVariables();
	}

	@Override
	public void updateTerms(List<NewLiteral> literals) {
		f.updateTerms(literals);

	}

	@Override
	public void setPredicate(Predicate p) {
		f.setPredicate(p);

	}

	@Override
	public Set<Variable> getReferencedVariables() {
		return f.getReferencedVariables();
	}

	@Override
	public Map<Variable, Integer> getVariableCount() {
		return f.getVariableCount();
	}

	@Override
	public NewLiteral getParent() {
		return f.getParent();
	}

	@Override
	public void setParent(NewLiteral parent) {
		f.setParent(parent);
	}

	@Override
	public Atom asAtom() {
		return this;
	}

	@Override
	public Atom clone() {
		Function fclone = (Function) f.clone();
		return new AtomWrapperImpl(fclone);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AtomWrapperImpl)
			return (f.equals(((AtomWrapperImpl) o).f));
		if (o instanceof FunctionalTermImpl)
			return (f.equals(o));
		return f.equals(o);
	}
	
	@Override 
	public int hashCode() {
		return f.hashCode();
	}
	
	@Override
	public String toString() {
		return f.toString();
	}

}
