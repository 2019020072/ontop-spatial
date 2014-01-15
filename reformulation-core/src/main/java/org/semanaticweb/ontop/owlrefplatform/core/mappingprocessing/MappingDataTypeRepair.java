package org.semanaticweb.ontop.owlrefplatform.core.mappingprocessing;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.semanaticweb.ontop.model.BNodePredicate;
import org.semanaticweb.ontop.model.CQIE;
import org.semanaticweb.ontop.model.DataTypePredicate;
import org.semanaticweb.ontop.model.DatalogProgram;
import org.semanaticweb.ontop.model.Function;
import org.semanaticweb.ontop.model.OBDADataFactory;
import org.semanaticweb.ontop.model.OBDAException;
import org.semanaticweb.ontop.model.Predicate;
import org.semanaticweb.ontop.model.Term;
import org.semanaticweb.ontop.model.URIConstant;
import org.semanaticweb.ontop.model.URITemplatePredicate;
import org.semanaticweb.ontop.model.ValueConstant;
import org.semanaticweb.ontop.model.Variable;
import org.semanaticweb.ontop.model.impl.AnonymousVariable;
import org.semanaticweb.ontop.model.impl.FunctionalTermImpl;
import org.semanaticweb.ontop.model.impl.OBDADataFactoryImpl;
import org.semanaticweb.ontop.model.impl.VariableImpl;
import org.semanaticweb.ontop.sql.DBMetadata;
import org.semanaticweb.ontop.sql.DataDefinition;
import org.semanaticweb.ontop.sql.api.Attribute;
import org.semanaticweb.ontop.utils.TypeMapper;

public class MappingDataTypeRepair {

	private DBMetadata metadata;

	private Map<String, List<Object[]>> termOccurenceIndex;

	private static OBDADataFactory dfac = OBDADataFactoryImpl.getInstance();

	/**
	 * Constructs a new mapping data type resolution. The object requires a
	 * database metadata for obtaining the table column definition as the
	 * default data-type.
	 * 
	 * @param metadata
	 *            The database metadata.
	 */
	public MappingDataTypeRepair(DBMetadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * This method wraps the variable that holds data property values with a
	 * data type predicate. It will replace the variable with a new function
	 * symbol and update the rule atom. However, if the users already defined
	 * the data-type in the mapping, this method simply accepts the function
	 * symbol.
	 * 
	 * @param mappingDatalog
	 *            The set of mapping axioms.
	 * @throws OBDAException
	 */
	public void insertDataTyping(DatalogProgram mappingDatalog)
			throws OBDAException {
		List<CQIE> mappingRules = mappingDatalog.getRules();
		for (CQIE rule : mappingRules) {
			prepareIndex(rule);
			Function atom = rule.getHead();
			Predicate predicate = atom.getPredicate();
			if (!isDataProperty(predicate)) {
				continue;
			}
			// If the predicate is a data property
			Term term = atom.getTerm(1);

			if (term instanceof Function) {
				Function function = (Function) term;

				if (function.getFunctionSymbol() instanceof URITemplatePredicate
						|| function.getFunctionSymbol() instanceof BNodePredicate) {
					// NO-OP for object properties
					continue;
				}

				Predicate functionSymbol = function.getFunctionSymbol();
				if (functionSymbol instanceof DataTypePredicate) {
					// NO-OP
					// If the term is already a data-type predicate then the
					// program
					// accepts this user-defined data type.
				} else {
					throw new OBDAException("Unknown data type predicate: "
							+ functionSymbol.getName());
				}
			} else if (term instanceof Variable) {
				// If the term has no data-type predicate then by default the
				// predicate is created following the database metadata of
				// column type.
				Variable variable = (Variable) term;
				Predicate functor = getDataTypeFunctor(variable);
				Term newTerm = dfac.getFunction(functor, variable);
				atom.setTerm(1, newTerm);
			}
		}
	}

	private boolean isDataProperty(Predicate predicate) {
		return predicate.getArity() == 2;
				//&& predicate.getType(1) == COL_TYPE.LITERAL;
	}

	private Predicate getDataTypeFunctor(Variable variable)
			throws OBDAException {
		List<Object[]> list = termOccurenceIndex.get(variable.getName());
		if (list == null) {
			throw new OBDAException("Unknown term in head");
		}
		Object[] o = list.get(0);
		Function atom = (Function) o[0];
		Integer pos = (Integer) o[1];

		Predicate functionSymbol = atom.getPredicate();
		String tableName = functionSymbol.toString();
		DataDefinition tableMetadata = metadata.getDefinition(tableName);

		Attribute attribute = tableMetadata.getAttribute(pos);

		return TypeMapper.getInstance().getPredicate(attribute.getType());		
	}

	private void prepareIndex(CQIE rule) {
		termOccurenceIndex = new HashMap<String, List<Object[]>>();
		List<Function> body = rule.getBody();
		Iterator<Function> it = body.iterator();
		while (it.hasNext()) {
			Function a = (Function) it.next();
			List<Term> terms = a.getTerms();
			int i = 1; // position index
			for (Term t : terms) {
				if (t instanceof AnonymousVariable) {
					i++; // increase the position index to evaluate the next
							// variable
				} else if (t instanceof VariableImpl) {
					Object[] o = new Object[2];
					o[0] = a; // atom
					o[1] = i; // position index
					List<Object[]> aux = termOccurenceIndex
							.get(((VariableImpl) t).getName());
					if (aux == null) {
						aux = new LinkedList<Object[]>();
					}
					aux.add(o);
					termOccurenceIndex.put(((VariableImpl) t).getName(), aux);
					i++; // increase the position index to evaluate the next
							// variable
				} else if (t instanceof FunctionalTermImpl) {
					// NO-OP
				} else if (t instanceof ValueConstant) {
					// NO-OP
				} else if (t instanceof URIConstant) {
					// NO-OP
				}
			}
		}
	}
}
