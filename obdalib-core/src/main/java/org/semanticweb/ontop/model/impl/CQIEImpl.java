/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.ontop.model.CQIE;
import org.semanticweb.ontop.model.Function;
import org.semanticweb.ontop.model.OBDAQueryModifiers;
import org.semanticweb.ontop.model.Term;
import org.semanticweb.ontop.model.Variable;
import org.semanticweb.ontop.utils.EventGeneratingLinkedList;
import org.semanticweb.ontop.utils.EventGeneratingList;
import org.semanticweb.ontop.utils.ListListener;

/**
 * This is a rule implementation that keeps track of changes in the query by
 * externals. It is also optimized for .equals calls.
 */
public class CQIEImpl implements CQIE, ListListener {

	private static final long serialVersionUID = 5789854661851692098L;
	private Function head = null;
	private EventGeneratingList<Function> body = null;

	private int hash = -1;
	private boolean rehash = true;

	private String string = null;

	private static final String SPACE = " ";
	private static final String COMMA = ",";
	private static final String INV_IMPLIES = ":-";

	private OBDAQueryModifiers modifiers = null;

	// TODO Remove isBoolean from the signature and from any method
	protected CQIEImpl(Function head, List<Function> body) {		
		// The syntax for CQ may contain no body, thus, this condition will
		// check whether the construction of the link list is possible or not.
		if (body != null) {
			EventGeneratingList<Function> eventbody = new EventGeneratingLinkedList<Function>();
			eventbody.addAll(body);				
			this.body = eventbody;

			registerListeners(eventbody);
			// TODO possible memory leak!!! we should also de-register when objects are removed
		}

		// The syntax for CQ may also contain no head, thus, this condition
		// will check whether we can look for the head terms or not.
		if (head != null) {
			this.head = head;
			EventGeneratingList<Term> headterms = (EventGeneratingList<Term>) head.getTerms();
			headterms.addListener(this);
		}
	}
	
	// TODO Remove isBoolean from the signature and from any method
		protected CQIEImpl(Function head, Function[] body) {
			
			

			// The syntax for CQ may contain no body, thus, this condition will
			// check whether the construction of the link list is possible or not.
			if (body != null) {
				EventGeneratingList<Function> eventbody = new EventGeneratingLinkedList<Function>();
				Collections.addAll(eventbody, body);
				this.body = eventbody;

				registerListeners(eventbody);
				// TODO possible memory leak!!! we should also de-register when objects are removed
			}

			// The syntax for CQ may also contain no head, thus, this condition
			// will check whether we can look for the head terms or not.
			if (head != null) {
				this.head = head;
				EventGeneratingList<Term> headterms = (EventGeneratingList<Term>) head.getTerms();
				headterms.addListener(this);
			}
		}
		
		

	private void registerListeners(EventGeneratingList<? extends Term> functions) {

		functions.addListener(this);

		for (Object o : functions) {
			if (!(o instanceof Function)) {
				continue;
			}
			Function f = (Function) o;
			EventGeneratingList<Term> list = (EventGeneratingList<Term>) f.getTerms();
			list.addListener(this);
			registerListeners(list);
		}
	}

	public List<Function> getBody() {
		return body;
	}

	public Function getHead() {
		return head;
	}

	public void updateHead(Function head) {
		this.head = head;

		EventGeneratingList<Term> headterms = (EventGeneratingLinkedList<Term>) head.getTerms();
		headterms.removeListener(this);
		headterms.addListener(this);
		listChanged();
	}

	public void updateBody(List<Function> body) {
		this.body.clear();
		this.body.addAll(body);
		listChanged();
	}

	@Override
	public int hashCode() {
		if (rehash) {
			string = toString();
			hash = string.hashCode();
			rehash = false;
		}
		return hash;
	}

	@Override
	public String toString() {
		/* expensive, so only compute the string if necessary */
		if (string == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(head.toString());
			sb.append(SPACE);
			sb.append(INV_IMPLIES);
			sb.append(SPACE);
			
			

			Iterator<Function> bit = body.iterator();
			while (bit.hasNext()) {
				Function atom = bit.next();
				sb.append(atom.toString());

				if (bit.hasNext()) { // if there is a next atom.
					sb.append(COMMA);
					sb.append(SPACE); // print ", "
				}
			}
			string = sb.toString();
		}
		return string;
	}

	@Override
	public CQIEImpl clone() {
		Function copyHead = (Function)head.clone();
		List<Function> copyBody = new ArrayList<Function>(body.size() + 10);

		for (Function atom : body) {
			if (atom != null) {
				copyBody.add((Function) atom.clone());
			}
		}
		
		CQIEImpl newquery = new CQIEImpl(copyHead, copyBody);
		newquery.rehash = this.rehash;
		newquery.string = this.string;
		newquery.hash = this.hash;

		return newquery;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CQIEImpl)) {
			return false;
		}
		CQIEImpl q2 = (CQIEImpl) obj;
		return hashCode() == q2.hashCode();
	}

	@Override
	public void listChanged() {
		rehash = true;
		string = null;
	}

	@Override
	public OBDAQueryModifiers getQueryModifiers() {
		return modifiers;
	}

	@Override
	public void setQueryModifiers(OBDAQueryModifiers modifiers) {
		this.modifiers = modifiers;
		listChanged();
	}

	@Override
	public Set<Variable> getReferencedVariables() {
		Set<Variable> vars = new LinkedHashSet<Variable>();
		for (Function atom : body)
			for (Term t : atom.getTerms()) {
				for (Variable v : t.getReferencedVariables())
					vars.add(v);
			}
		return vars;
	}

	@Override
	public Map<Variable, Integer> getVariableCount() {
		Map<Variable, Integer> vars = new HashMap<Variable, Integer>();
		for (Function atom : body) {
			Map<Variable, Integer> atomCount = atom.getVariableCount();
			for (Variable var : atomCount.keySet()) {
				Integer count = vars.get(var);
				if (count != null) {
					vars.put(var, count + atomCount.get(var));
				} else {
					vars.put(var, new Integer(atomCount.get(var)));
				}
			}
		}

		Map<Variable, Integer> atomCount = head.getVariableCount();
		for (Variable var : atomCount.keySet()) {
			Integer count = vars.get(var);
			if (count != null) {
				vars.put(var, count + atomCount.get(var));
			} else {
				vars.put(var, new Integer(atomCount.get(var)));
			}
		}
		return vars;
	}

	@Override
	public boolean hasModifiers() {
		return modifiers.hasModifiers();
	}
}
