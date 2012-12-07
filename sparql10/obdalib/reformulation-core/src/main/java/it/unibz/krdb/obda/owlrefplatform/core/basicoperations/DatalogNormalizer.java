package it.unibz.krdb.obda.owlrefplatform.core.basicoperations;

import it.unibz.krdb.obda.model.AlgebraOperatorPredicate;
import it.unibz.krdb.obda.model.Atom;
import it.unibz.krdb.obda.model.BooleanOperationPredicate;
import it.unibz.krdb.obda.model.CQIE;
import it.unibz.krdb.obda.model.Constant;
import it.unibz.krdb.obda.model.DatalogProgram;
import it.unibz.krdb.obda.model.Function;
import it.unibz.krdb.obda.model.NewLiteral;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.Variable;
import it.unibz.krdb.obda.model.Predicate.COL_TYPE;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.model.impl.OBDAVocabulary;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatalogNormalizer {

	private final static OBDADataFactory fac = OBDADataFactoryImpl
			.getInstance();

	/***
	 * Normalizes all the rules in a Datalog program, pushing equalities into
	 * the atoms of the queries, when possible
	 * 
	 * @param dp
	 */
	public static DatalogProgram normalizeDatalogProgram(DatalogProgram dp) {
		DatalogProgram clone = fac.getDatalogProgram();
		clone.setQueryModifiers(dp.getQueryModifiers());
		for (CQIE cq : dp.getRules()) {
			CQIE normalized = normalizeCQIE(cq);
			if (normalized != null) {
				clone.appendRule(normalized);
			}
		}
		return clone;
	}

	public static CQIE normalizeCQIE(CQIE query) {
		CQIE result = unfoldANDTrees(query);
		// result = normalizeEQ(result);
		result = unfoldJoinTrees(result);
		result = pullUpNestedReferences(result, true);
		if (result == null)
			return null;
		return result;
	}

	/***
	 * This expands all AND trees into individual comparison atoms in the body
	 * of the query. Nested AND trees inside Join or LeftJoin atoms are not
	 * touched.
	 * 
	 * @param query
	 * @return
	 */
	public static CQIE unfoldANDTrees(CQIE query) {
		CQIE result = query.clone();
		List<Atom> body = result.getBody();
		/* Collecting all necessary conditions */
		for (int i = 0; i < body.size(); i++) {
			Atom currentAtom = body.get(i);
			if (currentAtom.getPredicate() == OBDAVocabulary.AND) {
				body.remove(i);
				body.addAll(getUnfolderAtomList(currentAtom));
			}
		}
		return result;
	}

	/***
	 * This expands all Join that can be directly added as conjuncts to a
	 * query's body. Nested Join trees inside left joins are not touched.
	 * 
	 * @param query
	 * @return
	 */
	public static CQIE unfoldJoinTrees(CQIE query) {
		return unfoldJoinTrees(query, true);
	}

	/***
	 * This expands all Join that can be directly added as conjuncts to a
	 * query's body. Nested Join trees inside left joins are not touched.
	 * 
	 * @param query
	 * @return
	 */
	public static CQIE unfoldJoinTrees(CQIE query, boolean clone) {
		if (clone)
			query = query.clone();
		List body = query.getBody();
		unfoldJoinTrees(body, true);
		return query;
	}

	/***
	 * This expands all Join that can be directly added as conjuncts to a
	 * query's body. Nested Join trees inside left joins are not touched.
	 * <p>
	 * In addition, we will remove any Join atoms that only contain one single
	 * data atom, i.e., the join is not a join, but a table reference with
	 * conditions. These kind of atoms can result from the partial evaluation
	 * process and should be eliminated. The elimination takes all the atoms in
	 * the join (the single data atom plus possibly extra boolean conditions and
	 * adds them to the node that is the parent of the join).
	 * 
	 * @param query
	 * @return
	 */
	public static void unfoldJoinTrees(List body, boolean isJoin) {
		/* Collecting all necessary conditions */
		for (int i = 0; i < body.size(); i++) {
			Function currentAtom = (Function) body.get(i);
			if (!currentAtom.isAlgebraFunction())
				continue;
			if (currentAtom.getFunctionSymbol() == OBDAVocabulary.SPARQL_LEFTJOIN)
				unfoldJoinTrees(currentAtom.getTerms(), false);
			if (currentAtom.getFunctionSymbol() == OBDAVocabulary.SPARQL_JOIN) {
				unfoldJoinTrees(currentAtom.getTerms(), true);
				int dataAtoms = countDataItems(currentAtom.getTerms());
				if (isJoin || dataAtoms == 1) {
					body.remove(i);
					for (int j = currentAtom.getTerms().size() - 1; j >= 0; j--) {
						NewLiteral term = currentAtom.getTerm(j);
						Atom asAtom = term.asAtom();
						if (!body.contains(asAtom))
							body.add(i, asAtom);
					}
					i -= 1;
				}
			}
		}
	}

	public static CQIE foldJoinTrees(CQIE query, boolean clone) {
		if (clone)
			query = query.clone();
		List body = query.getBody();
		foldJoinTrees(body, false);
		return query;
	}

	public static void foldJoinTrees(List atoms, boolean isJoin) {
		List<Function> dataAtoms = new LinkedList<Function>();
		List<Function> booleanAtoms = new LinkedList<Function>();

		/*
		 * Collecting all data and boolean atoms for later processing. Calling
		 * recursively fold Join trees on any algebra function.
		 */
		for (Object o : atoms) {
			Function atom = (Function) o;
			if (atom.isBooleanFunction()) {
				booleanAtoms.add(atom);
			} else {
				dataAtoms.add(atom);
				if (atom.getFunctionSymbol() == OBDAVocabulary.SPARQL_LEFTJOIN)
					foldJoinTrees(atom.getTerms(), false);
				if (atom.getFunctionSymbol() == OBDAVocabulary.SPARQL_JOIN)
					foldJoinTrees(atom.getTerms(), true);
			}

		}

		if (!isJoin || dataAtoms.size() <= 2)
			return;

		/*
		 * We process all atoms in dataAtoms to make only BINARY joins. Taking
		 * two at a time and replacing them for JOINs, until only two are left.
		 * All boolean conditions of the original join go into the first join
		 * generated. It always merges from the left to the right.
		 */
		while (dataAtoms.size() > 2) {
			Function joinAtom = fac.getFunctionalTerm(
					OBDAVocabulary.SPARQL_JOIN, dataAtoms.remove(0),
					dataAtoms.remove(0));
			joinAtom.getTerms().addAll(booleanAtoms);
			booleanAtoms.clear();

			dataAtoms.add(0, joinAtom);
		}
		atoms.clear();
		atoms.addAll(dataAtoms);

	}

	/***
	 * Counts the number of data atoms in this list of terms. Not recursive.
	 * 
	 * @param terms
	 * @return
	 */
	public static int countDataItems(List<NewLiteral> terms) {
		int count = 0;
		for (NewLiteral lit : terms) {
			Function currentAtom = (Function) lit;
			if (!currentAtom.isBooleanFunction())
				count += 1;
		}
		return count;
	}

	/***
	 * Eliminates all equalities in the query by applying a substitution to the
	 * database predicates.
	 * 
	 * @param query
	 *            null if there is an unsatisfiable equality
	 * @return
	 */
	public static CQIE pushEqualities(CQIE result, boolean clone) {
		if (clone)
			result = result.clone();

		List body = result.getBody();
		Map<Variable, NewLiteral> mgu = new HashMap<Variable, NewLiteral>();

		/* collecting all equalities as substitutions */

		for (int i = 0; i < body.size(); i++) {
			Function atom = (Function) body.get(i);
			Unifier.applyUnifier(atom, mgu);
			if (atom.getPredicate() == OBDAVocabulary.EQ) {
				Substitution s = Unifier.getSubstitution(atom.getTerm(0),
						atom.getTerm(1));
				if (s == null) {
					return null;
				}

				if (!(s instanceof NeutralSubstitution)) {
					Unifier.composeUnifiers(mgu, s);
				}
				body.remove(i);
				i -= 1;
				continue;
			}
		}
		result = Unifier.applyUnifier(result, mgu, false);
		return result;
	}

	public static DatalogProgram pushEqualities(DatalogProgram dp) {
		DatalogProgram clone = fac.getDatalogProgram();
		clone.setQueryModifiers(dp.getQueryModifiers());
		for (CQIE cq : dp.getRules()) {
			pushEqualities(cq, false);
			clone.appendRule(cq);
		}
		return clone;
	}

	/***
	 * This method introduces new variable names in each data atom and
	 * equalities to account for JOIN operations. This method is called before
	 * generating SQL queries and allows to avoid cross refrences in nested
	 * JOINs, which generate wrong ON or WHERE conditions.
	 * 
	 * 
	 * @param currentTerms
	 * @param substitutions
	 */
	public static void pullOutEqualities(CQIE query) {
		Map<Variable, NewLiteral> substitutions = new HashMap<Variable, NewLiteral>();
		int[] newVarCounter = { 1 };

		Set<Function> booleanAtoms = new HashSet<Function>();

		pullOutEqualities(query.getBody(), substitutions, booleanAtoms,
				newVarCounter, false);
		List body = query.getBody();
		body.addAll(booleanAtoms);

		/*
		 * All new variables have been generates, the substitutions also, we
		 * need to apply them to the equality atoms and to the head of the
		 * query.
		 */

		Unifier.applyUnifier(query, substitutions, false);

	}

	private static BranchDepthSorter sorter = new BranchDepthSorter();
	private static boolean secondDataAtomFound;
	private static boolean firstDataAtomFound;

	/***
	 * Compares two atoms by the depth of their JOIN/LEFT JOIN branches. This is
	 * used to sort atoms in a query bodybased on the depth, to assure the
	 * depesth branches are visited first.
	 * 
	 * @author mariano
	 * 
	 */
	private static class BranchDepthSorter implements Comparator<Function> {

		public int getDepth(Function term) {
			int max = 0;
			if (term.isDataFunction() || term.isBooleanFunction() || term.isDataTypeFunction()) {
				return 0;
			} else {
				List<NewLiteral> innerTerms = term.getTerms();

				for (NewLiteral innerTerm : innerTerms) {
					int depth = getDepth((Function) innerTerm);
					max = Math.max(max, depth);
				}
				max += 1;
			}

			//System.out.println("MAX: " + max);
			return max;
		}

		@Override
		public int compare(Function arg0, Function arg1) {
			return getDepth(arg1) - getDepth(arg0);
		}
	}

	/***
	 * Adds a trivial equality to a LeftJoin in case the left join doesn't have
	 * at least one boolean condition. This is necessary to have syntactically
	 * correct LeftJoins in SQL.
	 * 
	 * @param leftJoin
	 */
	private static void addMinimalEqualityToLeftJoin(Function leftJoin) {
		int booleanAtoms = 0;
		boolean isLeftJoin = leftJoin.isAlgebraFunction() && (leftJoin.getPredicate() == OBDAVocabulary.SPARQL_LEFTJOIN);
		for (NewLiteral term: leftJoin.getTerms()) {
			Function f = (Function)term;
			if (f.isAlgebraFunction()) {
				addMinimalEqualityToLeftJoin(f);
			}
			if (f.isBooleanFunction())
				booleanAtoms += 1;
		}
		if (isLeftJoin && booleanAtoms == 0) {
			Function trivialEquality = fac.getEQFunction(fac.getValueConstant("1", COL_TYPE.INTEGER), fac.getValueConstant("1", COL_TYPE.INTEGER));
			leftJoin.getTerms().add(trivialEquality);
		}
	}
	
	public static void addMinimalEqualityToLeftJoin(CQIE query) {
		for (Function f: query.getBody()) {
			if (f.isAlgebraFunction()) {
				addMinimalEqualityToLeftJoin(f);
			}
		}	
	}
	/***
	 * This method introduces new variable names in each data atom and
	 * equalities to account for JOIN operations. This method is called before
	 * generating SQL queries and allows to avoid cross refrences in nested
	 * JOINs, which generate wrong ON or WHERE conditions.
	 * 
	 * 
	 * @param currentTerms
	 * @param substitutions
	 */
	private static void pullOutEqualities(List currentTerms,
			Map<Variable, NewLiteral> substitutions,
			Set<Function> leftConditionBooleans, int[] newVarCounter,
			boolean isLeftJoin) {

		List orderedList = new LinkedList();
		orderedList.addAll(currentTerms);
		Collections.sort(orderedList, sorter);
	//	System.out.println("sorted");
		for (int i = 0; i < orderedList.size(); i++) {

			NewLiteral term = (NewLiteral) orderedList.get(i);

			/*
			 * We don't expect any functions as terms, data atoms will only have
			 * variables or constants at this level. This method is only called
			 * exactly before generating the SQL query.
			 */
			if (!(term instanceof Function))
				throw new RuntimeException(
						"Unexpected term found while normalizing (pulling out equalities) the query.");

			Function atom = (Function) term;
			if (atom.isBooleanFunction() || atom.isArithmeticFunction() || atom.isDataTypeFunction()) {
				// NO-OP
				continue;
			}

			List<NewLiteral> subterms = atom.getTerms();

			if (atom.isAlgebraFunction()) {
				if (atom.getFunctionSymbol() == OBDAVocabulary.SPARQL_LEFTJOIN)
					pullOutEqualities(subterms, substitutions,
							leftConditionBooleans, newVarCounter, true);
				else
					pullOutEqualities(subterms, substitutions,
							leftConditionBooleans, newVarCounter, false);
			
				if (!isLeftJoin) {
					/*
					 * Collecting any conditions that had to be pulled up to an
					 * upper level JOIN from a lower lever leftJoin. These are
					 * genearted when there are constants in left side data
					 * atoms in a left join
					 */
					currentTerms.addAll(leftConditionBooleans);
					leftConditionBooleans.clear();
				}

				continue;
			}

			/*
			 * This is a data atom, we need to change ALL variables that appear
			 * in it
			 */
			if (!(atom.isDataFunction()))
				throw new RuntimeException(
						"Unpexpected kind of function found while pulling out equalities. Exected data atom");

			for (int j = 0; j < subterms.size(); j++) {
				NewLiteral subTerm = subterms.get(j);
				if (subTerm instanceof Variable) {

					Variable var1 = (Variable) subTerm;
					Variable var2 = (Variable) substitutions.get(var1);

					if (var2 == null) {
						/*
						 * No substitution exists, hence, no action but genrate
						 * a new variable and register in the substitutions, and
						 * replace the current value with a fresh one.
						 */
						var2 = fac.getVariable(var1.getName() + "f"
								+ newVarCounter[0]);

						substitutions.put(var1, var2);
						subterms.set(j, var2);

					} else {

						/*
						 * There already exists one, so we generate a fresh,
						 * replace the current value, and add an equalility
						 * between the substitution and the new value.
						 */

						Variable newVariable = fac.getVariable(var1.getName()
								+ newVarCounter[0]);

						subterms.set(j, newVariable);
						Function equality = fac
								.getEQFunction(var2, newVariable);
						currentTerms.add(equality);

					}
					newVarCounter[0] += 1;
				} else if (subTerm instanceof Constant) {
					/*
					 * This case was necessary for query 7 in BSBM
					 */
					/**
					 * A('c') Replacing the constant with a fresh variable x and
					 * adding an quality atom ,e.g., A(x), x = 'c'
					 */
					Variable var = fac.getVariable("f" + newVarCounter[0]);
					newVarCounter[0] += 1;
					Function equality = fac.getEQFunction(var, subTerm);
					subterms.set(j, var);
					if (isLeftJoin && currentTerms.indexOf(atom) == 0) {
						leftConditionBooleans.add(equality);
					} else {
						currentTerms.add(equality);
					}

				}
			}
		}
	}

	/****
	 * Gets all the variables that are defined in this list of atoms, except in
	 * atom i
	 * 
	 * @param atoms
	 * @return
	 */
	private static Set<Variable> getDefinedVariables(List atoms) {
		Set<Variable> currentLevelVariables = new HashSet<Variable>();
		for (Object l : atoms) {
			Function atom = (Function) l;
			if (atom.isBooleanFunction()) {
				continue;
			} else if (atom.isAlgebraFunction()) {
				currentLevelVariables.addAll(getDefinedVariables(atom
						.getTerms()));
			} else {
				currentLevelVariables.addAll(atom.getReferencedVariables());
			}
		}
		return currentLevelVariables;
	}

	/***
	 * Collects all the variables that appear in all other branches (these are
	 * atoms in the list of atoms) except in focusBranch.
	 * <p>
	 * Variables are considered problematic because they are out of the scope of
	 * focusBranch. There are not visible in an SQL algebra tree.
	 * <p>
	 * Note that this method should only be called after callin pushEqualities
	 * and pullOutEqualities on the CQIE. This is to assure that there are no
	 * transitive equalities to take care of and that each variable in a data
	 * atom is unique.
	 * 
	 * @param atoms
	 * @param branch
	 * @return
	 */
	private static Set<Variable> getProblemVariablesForBranchN(List atoms,
			int focusBranch) {
		Set<Variable> currentLevelVariables = new HashSet<Variable>();
		for (int i = 0; i < atoms.size(); i++) {
			if (i == focusBranch)
				continue;
			Function atom = (Function) atoms.get(i);
			if (atom.isDataFunction()) {
				currentLevelVariables.addAll(atom.getReferencedVariables());
			} else if (atom.isAlgebraFunction()) {
				currentLevelVariables.addAll(getDefinedVariables(atom
						.getTerms()));
			} else {
				// noop
			}
		}
		return currentLevelVariables;
	}

	/***
	 * This will
	 * 
	 * @param query
	 * @return
	 */
	public static CQIE pullUpNestedReferences(CQIE query, boolean clone) {

		if (clone)
			query = query.clone();

		List<Atom> body = query.getBody();

		Atom head = query.getHead();
		/*
		 * This set is only for reference
		 */
		Set<Variable> currentLevelVariables = new HashSet<Variable>();
		/*
		 * This set will be modified in the process
		 */
		Set<Function> resultingBooleanConditions = new HashSet<Function>();

		/*
		 * Analyze each atom that is a Join or LeftJoin, the process will
		 * replace everything needed.
		 */
		int[] freshVariableCount = { 0 };
		pullUpNestedReferences(body, head, currentLevelVariables,
				resultingBooleanConditions, freshVariableCount);

		/*
		 * Adding any remiding boolean conditions to the top level.
		 */
		for (Function condition : resultingBooleanConditions) {
			body.add(condition.asAtom());
		}

		return query;
	}

	private static void pullUpNestedReferences(List currentLevelAtoms,
			Atom head, Set<Variable> problemVariables,
			Set<Function> booleanConditions, int[] freshVariableCount) {

		/*
		 * Call recursively on each atom that is a Join or a LeftJoin passing
		 * the variables of this level
		 */
		for (int focusBranch = 0; focusBranch < currentLevelAtoms.size(); focusBranch++) {
			Object l = currentLevelAtoms.get(focusBranch);

			Function atom = (Function) l;
			if (!(atom.getFunctionSymbol() instanceof AlgebraOperatorPredicate))
				continue;
		//	System.out
			//		.println("======================== INTO ALGEBRA =====================");

			List<NewLiteral> terms = atom.getTerms();

			Set<Variable> nestedProblemVariables = new HashSet<Variable>();

			nestedProblemVariables.addAll(problemVariables);
			nestedProblemVariables.addAll(getProblemVariablesForBranchN(
					currentLevelAtoms, focusBranch));

			pullUpNestedReferences(terms, head, nestedProblemVariables,
					booleanConditions, freshVariableCount);
		}

		/*
		 * Add the resulting equalities that belong to the current level. An
		 * equality belongs to this level if ALL its variables are defined at
		 * the current level and not at the upper levels.
		 */
		Set<Function> removedBooleanConditions = new HashSet<Function>();
	//	System.out.println("Checking boolean conditions: "
		//		+ booleanConditions.size());
		for (Function equality : booleanConditions) {
			Set<Variable> atomVariables = equality.getReferencedVariables();

			boolean belongsToThisLevel = true;
			for (Variable var : atomVariables) {
				if (!problemVariables.contains(var))
					continue;
				belongsToThisLevel = false;
			}
			if (!belongsToThisLevel)
				continue;

			currentLevelAtoms.add(equality);
			removedBooleanConditions.add(equality);
		}
		booleanConditions.removeAll(removedBooleanConditions);

		/*
		 * Review the atoms of the current level and generate any variables,
		 * equalities needed at this level (no further recursive calls).
		 * Generate new variables for each variable that appears at this level,
		 * and also appears at a top level. We do this only for data atoms.
		 * 
		 * We do this by creating a substitution for each of the, and then
		 * applying the substitution. We also add an equality for each
		 * substitution we created.
		 */

		/*
		 * Review the current boolean atoms, if the refer to upper level
		 * variables then remove them from the current level and add them to the
		 * equalities set for the upper level.
		 * 
		 * If an contains at least 1 variable that is mentioned in an upper
		 * level, then this condition is removed from the current level and
		 * moved forward by adding it to the booleanConditions set.
		 */

		for (int index = 0; index < currentLevelAtoms.size(); index++) {
		//	System.out.println(index);
		//	System.out.println(currentLevelAtoms.size());
			NewLiteral l = (NewLiteral) currentLevelAtoms.get(index);
			Function atom = (Function) l;
			//System.out
				//	.println(atom.getFunctionSymbol().getClass() + " " + atom);
			if (!(atom.getFunctionSymbol() instanceof BooleanOperationPredicate))
				continue;
			Set<Variable> variables = atom.getReferencedVariables();
			boolean belongsUp = false;

			search: for (Variable var : variables) {
				if (problemVariables.contains(var)) {

					// /*
					// * looking for an equality that might indicate that in
					// fact,
					// * the atom doesn't belong up because there is an equality
					// * at this level that mentiones the "unsafe variable" and
					// a
					// * "safe variable" at the same time. (this pattern happens
					// * due to the call to
					// DatalogNormalizer.pullOutEqualities()
					// * that happens before pullingUp
					// */
					// for (int idx2 = 0; idx2 < currentLevelAtoms.size();
					// idx2++) {
					// NewLiteral l2 = (NewLiteral) currentLevelAtoms
					// .get(idx2);
					// if (!(l2 instanceof Function))
					// continue;
					// Function f2 = (Function) l2;
					// if (f2.getPredicate() != OBDAVocabulary.EQ)
					// continue;
					// List<NewLiteral> equalityVariables = f2.getTerms();
					// if (equalityVariables.contains(var)) {
					// // NewLiteral var2 = equalityVariables.get(0);
					// // if (!(var2 instanceof Variable))
					// // continue;
					// if (!(problemVariables
					// .containsAll(equalityVariables))) {
					// /*
					// * we found that var is acutally safe, there is
					// * an equality that bounds it to a data atom in
					// * the current level
					// */
					// continue search;
					// }
					// }
					//
					// }

					belongsUp = true;
					break;
				}
			}

			if (!belongsUp)
				continue;

			// Belongs up, removing and pushing up

		//	System.out.println("REMOVED!!!!");

			currentLevelAtoms.remove(index);
			index -= 1;
			booleanConditions.add(atom);
		}

	}

	/***
	 * Takes an AND atom and breaks it into a list of individual condition
	 * atoms.
	 * 
	 * @param atom
	 * @return
	 */
	public static List<Atom> getUnfolderAtomList(Atom atom) {
		if (atom.getPredicate() != OBDAVocabulary.AND) {
			throw new InvalidParameterException();
		}
		List<NewLiteral> innerFunctionalTerms = new LinkedList<NewLiteral>();
		for (NewLiteral term : atom.getTerms()) {
			innerFunctionalTerms.addAll(getUnfolderTermList((Function) term));
		}
		List<Atom> newatoms = new LinkedList<Atom>();
		for (NewLiteral innerterm : innerFunctionalTerms) {
			Function f = (Function) innerterm;
			Atom newatom = fac.getAtom(f.getFunctionSymbol(), f.getTerms());
			newatoms.add(newatom);
		}
		return newatoms;
	}

	/***
	 * Takes an AND atom and breaks it into a list of individual condition
	 * atoms.
	 * 
	 * @param atom
	 * @return
	 */
	public static List<NewLiteral> getUnfolderTermList(Function term) {

		List<NewLiteral> result = new LinkedList<NewLiteral>();

		if (term.getFunctionSymbol() != OBDAVocabulary.AND) {
			result.add(term);
		} else {
			List<NewLiteral> terms = term.getTerms();
			for (NewLiteral currentterm : terms) {
				if (currentterm instanceof Function) {
					result.addAll(getUnfolderTermList((Function) currentterm));
				} else {
					result.add(currentterm);
				}
			}
		}

		return result;
	}

	
	// THE FOLLOWING COMMENT IS TAKEN FROM THE CODE ABOVE, THE FUNCTIONALITY IT DESCRIBES
	// WAS IMPLEMENTED BELLOW
	/*
	 * Here we collect boolean atoms that have conditions of atoms on the
	 * left of left joins. These cannot be put in the conditions of
	 * LeftJoin(...) atoms as inside terms, since these conditions have to
	 * be applied no matter what. Keeping them there makes them "optional",
	 * i.e., or else return NULL. Hence these conditions have to be pulled
	 * up to the nearest JOIN in the upper levels in the branches. The
	 * pulloutEqualities method iwll do this, however if there are still
	 * remaiing some by the time it finish, we must add them to the body of
	 * the CQIE as normal conditions to the query (WHERE clauses)
	 */	
	
	public static void pullOutLeftJoinConditions(CQIE query) {
		secondDataAtomFound = false;
		firstDataAtomFound = false;
		Set<Function> booleanAtoms = new HashSet<Function>();
		Function f = query.getBody().get(0);
		if (f.getFunctionSymbol() == OBDAVocabulary.SPARQL_LEFTJOIN) {
			pullOutLJCond(query.getBody(), booleanAtoms, true);
		} else
			pullOutLJCond(query.getBody(), booleanAtoms, false);

		List body = query.getBody();
		body.addAll(booleanAtoms);
	}
	
	private static void pullOutLJCond(List currentTerms,	Set<Function> leftConditionBooleans, boolean isLeftJoin) {
		Set<Variable> firstDataAtomVars = null;

		for (int i = 0; i < currentTerms.size(); i++) {
			NewLiteral term = (NewLiteral) currentTerms.get(i);
			if (!(term instanceof Function))
				throw new RuntimeException(
						"Unexpected term found while normalizing (pulling out conditions) the query.");

			Function atom = (Function) term;
			List<NewLiteral> subterms = atom.getTerms();

			// if we are in left join then pull out boolean conditions that
			// correspond to first data atom
			if (isLeftJoin) {

				if (atom.isDataFunction()) {
					// if both are false then its first one
					if (!firstDataAtomFound && !secondDataAtomFound) {
						firstDataAtomFound = true;
						// collect variable references from first data atom
						firstDataAtomVars = atom.getReferencedVariables();
					}
				} else {
					// if we are past first data term, then collect terms
					if (firstDataAtomFound) {
						// check if this boolean term has variables referencing
						// first data atom
						// if yes then they need to be pulled out of LEFT JOINs ON clause
						if (hasReferencestoFirstDataAtom(atom,
								firstDataAtomVars)) {
							currentTerms.remove(i);
							leftConditionBooleans.add(atom);
						}

					}
				}
			}
			if (atom.isAlgebraFunction()) {
				if (atom.getFunctionSymbol() == OBDAVocabulary.SPARQL_LEFTJOIN)
					pullOutLJCond(subterms, leftConditionBooleans, true);
				else
					pullOutLJCond(subterms, leftConditionBooleans, false);
			}

		}

	}

	// check if an atom shares same variables with first data atom 
	private static boolean hasReferencestoFirstDataAtom(Function atom,
			Set<Variable> firstDataAtomVars) {
		Set<Variable> curAtomVars = atom.getReferencedVariables();
		if (firstDataAtomVars == null)
			return false;
		if (!curAtomVars.isEmpty() && !firstDataAtomVars.isEmpty()) {
			Variable first = (Variable) curAtomVars.toArray()[0];
			if (firstDataAtomVars.contains(first))
				return true;
		}
		return false;
	}
}


