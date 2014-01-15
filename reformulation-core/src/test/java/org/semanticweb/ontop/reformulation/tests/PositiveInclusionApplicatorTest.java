package org.semanticweb.ontop.reformulation.tests;

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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.semanticweb.ontop.model.CQIE;
import org.semanticweb.ontop.model.Function;
import org.semanticweb.ontop.model.OBDADataFactory;
import org.semanticweb.ontop.model.Predicate;
import org.semanticweb.ontop.model.Term;
import org.semanticweb.ontop.model.impl.OBDADataFactoryImpl;
import org.semanticweb.ontop.owlrefplatform.core.basicoperations.PositiveInclusionApplicator;

import junit.framework.TestCase;

public class PositiveInclusionApplicatorTest extends TestCase {

	CQIE						initialquery1	= null;

	CQIE						initialquery2	= null;

	CQIE						initialquery3	= null;

	CQIE						initialquery4	= null;

	CQIE						initialquery5	= null;

	PositiveInclusionApplicator	piapplicator	= new PositiveInclusionApplicator();

	OBDADataFactory				pfac			= OBDADataFactoryImpl.getInstance();
	OBDADataFactory				tfac			= OBDADataFactoryImpl.getInstance();

	Predicate					r				= pfac.getPredicate("R", 2);
	Predicate					s				= pfac.getPredicate("S", 2);
	Predicate					q				= pfac.getPredicate("q", 1);

	Term						x				= tfac.getVariable("x");
	Term						y				= tfac.getVariable("y");
	Term						z				= tfac.getVariable("z");
	Term						m				= tfac.getVariable("m");

	Term						u1				= tfac.getVariableNondistinguished();
	Term						u2				= tfac.getVariableNondistinguished();

	
	public void setUp() throws Exception {

		// q(y) :- R(x, y), R(x, z), S(y, m), S(z, m),

		List<Term> terms1 = new LinkedList<Term>();
		terms1.add(x);
		terms1.add(y);
		Function a1 = tfac.getFunction(r, terms1);

		List<Term> terms2 = new LinkedList<Term>();
		terms2.add(x);
		terms2.add(z);
		Function a2 = tfac.getFunction(r, terms2);

		List<Term> terms3 = new LinkedList<Term>();
		terms3.add(y);
		terms3.add(m);
		Function a3 = tfac.getFunction(s, terms3);

		List<Term> terms4 = new LinkedList<Term>();
		terms4.add(z);
		terms4.add(m);
		Function a4 = tfac.getFunction(s, terms4);

		List<Term> termshead = new LinkedList<Term>();
		termshead.add(x);
		Function head = tfac.getFunction(q, termshead);

		LinkedList<Function> body = new LinkedList<Function>();
		body.add(a1);
		body.add(a2);
		body.add(a3);
		body.add(a4);

		initialquery1 = tfac.getCQIE(head, body);

		terms1 = new LinkedList<Term>();
		terms1.add(x);
		terms1.add(u1);
		a1 = tfac.getFunction(r, terms1);

		terms2 = new LinkedList<Term>();
		terms2.add(x);
		terms2.add(u2);
		a2 = tfac.getFunction(r, terms2);

		body = new LinkedList<Function>();
		body.add(a1);
		body.add(a2);

		termshead = new LinkedList<Term>();
		termshead.add(x);
		head = tfac.getFunction(q, termshead);

		initialquery2 = tfac.getCQIE(head, body);

		terms1 = new LinkedList<Term>();
		terms1.add(u1);
		terms1.add(x);
		a1 = tfac.getFunction(r, terms1);

		terms2 = new LinkedList<Term>();
		terms2.add(u2);
		terms2.add(x);
		a2 = tfac.getFunction(r, terms2);

		body = new LinkedList<Function>();
		body.add(a1);
		body.add(a2);

		termshead = new LinkedList<Term>();
		termshead.add(x);
		head = tfac.getFunction(q, termshead);

		initialquery3 = tfac.getCQIE(head, body);

		terms1 = new LinkedList<Term>();
		terms1.add(x);
		terms1.add(u1);
		a1 = tfac.getFunction(r, terms1);

		terms2 = new LinkedList<Term>();
		terms2.add(x);
		terms2.add(y);
		a2 = tfac.getFunction(r, terms2);

		body = new LinkedList<Function>();
		body.add(a1);
		body.add(a2);

		termshead = new LinkedList<Term>();
		termshead.add(x);
		head = tfac.getFunction(q, termshead);

		initialquery4 = tfac.getCQIE(head, body);

		terms1 = new LinkedList<Term>();
		terms1.add(u1);
		terms1.add(x);
		a1 = tfac.getFunction(r, terms1);

		terms2 = new LinkedList<Term>();
		terms2.add(y);
		terms2.add(x);
		a2 = tfac.getFunction(r, terms2);

		body = new LinkedList<Function>();
		body.add(a1);
		body.add(a2);

		termshead = new LinkedList<Term>();
		termshead.add(x);
		head = tfac.getFunction(q, termshead);

		initialquery5 = tfac.getCQIE(head, body);

	}

	
	public void testApplyExistentialInclusions() {
		// TODO
	}

	
	public void testSaturateByUnification1() throws Exception {
		HashSet<CQIE> initialset = new HashSet<CQIE>();
		initialset.add(initialquery1);
		Set<CQIE> saturatedset = piapplicator.saturateByUnification(initialset, s, false);
		assertTrue(saturatedset.size() == 2);
	}

	/***
	 * In this case, no unification happens because the we are trying to unify
	 * only atoms that share the left term and that have the S predicate.
	 * 
	 * @throws Exception
	 */
	
	public void testSaturateByUnification2() throws Exception {
		HashSet<CQIE> initialset = new HashSet<CQIE>();
		initialset.add(initialquery1);
		Set<CQIE> saturatedset = piapplicator.saturateByUnification(initialset, s, true);
		assertTrue(saturatedset.size() == 1);
	}

	/***
	 * Checking that non distinguisehd variables are also unified
	 * 
	 * q(x) :- R(#, x), R(#, x)
	 * 
	 * should produce
	 * 
	 * q(x) :- R(#, x), R(#, x) q(x) :- R(#, x)
	 * 
	 * @throws Exception
	 */
	
	public void testSaturateByUnification3() throws Exception {
		HashSet<CQIE> initialset = new HashSet<CQIE>();
		initialset.add(initialquery2);
		Set<CQIE> saturatedset = piapplicator.saturateByUnification(initialset, r, true);
		assertTrue(saturatedset.size() == 2);
	}

	/***
	 * Checking that non distinguisehd variables are also unified
	 * 
	 * @throws Exception
	 */
	
	public void testSaturateByUnification4() throws Exception {
		HashSet<CQIE> initialset = new HashSet<CQIE>();
		initialset.add(initialquery3);
		Set<CQIE> saturatedset = piapplicator.saturateByUnification(initialset, r, false);
		assertTrue(saturatedset.size() == 2);
	}

	/***
	 * Checking that non distinguisehd variables are also unified
	 * 
	 * q(x) :- R(#, x), R(y, x)
	 * 
	 * should produce
	 * 
	 * q(x) :- R(#, x), R(y, x) q(x) :- R(#, x)
	 * 
	 * @throws Exception
	 */
	
	public void testSaturateByUnification5() throws Exception {
		HashSet<CQIE> initialset = new HashSet<CQIE>();
		initialset.add(initialquery4);
		Set<CQIE> saturatedset = piapplicator.saturateByUnification(initialset, r, true);
		assertTrue(saturatedset.size() == 2);
	}

	/***
	 * Checking that non distinguisehd variables are also unified
	 * 
	 * @throws Exception
	 */
	
	public void testSaturateByUnification6() throws Exception {
		HashSet<CQIE> initialset = new HashSet<CQIE>();
		initialset.add(initialquery5);
		Set<CQIE> saturatedset = piapplicator.saturateByUnification(initialset, r, false);
		assertTrue(saturatedset.size() == 2);
	}

	
	public void testApplyExistentialInclusion() {
		// TODO
	}

}
