/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanaticweb.ontop.sql.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.semanaticweb.ontop.parser.AggregationVisitor;
import org.semanaticweb.ontop.parser.AliasMapVisitor;
import org.semanaticweb.ontop.parser.JoinConditionVisitor;
import org.semanaticweb.ontop.parser.ProjectionVisitor;
import org.semanaticweb.ontop.parser.SelectionVisitor;
import org.semanaticweb.ontop.parser.TablesNameVisitor;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;



/**
 * A  structure to store the parsed SQL query string. 
 * It returns the information about the query using the visitor classes
 */
public class VisitedQuery implements Serializable{

	private static final long serialVersionUID = -4590590361733833782L;

	private String query; 
	private Statement stm;
	 
	private Select select; //the parsed query
	
	
	private ArrayList<RelationJSQL> tableSet;
	private HashMap<String, String> aliasMap;
	private ArrayList<String> joins;
	private SelectionJSQL selection;
	private ProjectionJSQL projection;
	private AggregationJSQL groupByClause;
	
	
	/**
	 * Constructs an empty query.
	 */
	public VisitedQuery() {
		super();
	}

	
	/**
	 * Parse a query given as a String
	 * @param queryString the SQL query to parse
	 * @throws JSQLParserException 
	 */
	
	public VisitedQuery(String queryString) throws JSQLParserException {
		query = queryString;
	 
	
			stm = CCJSqlParserUtil.parse(query);
			if (stm instanceof Select) {
				select = (Select)stm;
				
				//getting the values we also eliminate or handle the quotes
				tableSet = getTableSet();
				aliasMap = getAliasMap();
				joins = getJoinCondition();
				selection = getSelection();
				projection = getProjection();
				groupByClause =getGroupByClause();
				
			}
						//catch exception about wrong inserted columns
			else 
				throw new JSQLParserException("The inserted query is not a SELECT statement");

		
	}
	
	public VisitedQuery(Statement statement) throws JSQLParserException{
		
		this(statement.toString());
		

	}
	

	@Override
	public String toString() {
		return select.toString(); 
	}
	
	/**
	 * Returns all the tables in this query tree.
	 */
	public ArrayList<RelationJSQL> getTableSet() {
		
		if(tableSet== null){
			TablesNameVisitor tnp = new TablesNameVisitor();
			tableSet =tnp.getTableList(select);
		}
		return tableSet;
	}
	
	/**
	 * Get the string construction of alias name. 
	 */
	public HashMap<String, String> getAliasMap() {
		if(aliasMap== null){
			AliasMapVisitor aliasV = new AliasMapVisitor();
			aliasMap= aliasV.getAliasMap(select);
		}
		return aliasMap;
	}

	/**
	 * Get the string construction of the join condition. The string has the
	 * format of "VAR1=VAR2".
	 */
	public ArrayList<String> getJoinCondition() {
		if(joins==null){
			JoinConditionVisitor joinCV = new JoinConditionVisitor();
			joins= joinCV.getJoinConditions(select);
		}
		return joins;
	}

	/**
	 * Get the object construction for the WHERE clause.
	 * @throws JSQLParserException 
	 */
	public SelectionJSQL getSelection() throws JSQLParserException {
		if(selection==null){
			SelectionVisitor sel= new SelectionVisitor();
			selection= sel.getSelection(select);
		}
		return selection;
	}
	
	/**
	 * Get the object construction for the SELECT clause.
	 * @throws JSQLParserException 
	 */
	public ProjectionJSQL getProjection() throws JSQLParserException {
		if(projection==null){
			ProjectionVisitor proj = new ProjectionVisitor();
			projection= proj.getProjection(select);
		}
		return projection;
		
	}
	/**
	 * Set the object construction for the SELECT clause, 
	 * modifying the current statement
	 * @param projection
	 */
	
	public void setProjection(ProjectionJSQL projection) {
		ProjectionVisitor proj = new ProjectionVisitor();
		 proj.setProjection(select, projection);
		 this.projection= projection;
	}
	
	/**
	 * Set the object construction for the WHERE clause, 
	 * modifying the current statement
	 * @param selection
	 */
	
	public void setSelection(SelectionJSQL selection) {
		SelectionVisitor sel = new SelectionVisitor();
		sel.setSelection(select, selection);
		this.selection= selection;
	}

	
	/**
	 * Constructs the GROUP BY statement based on the Aggregation
	 * object.
	 */
	public AggregationJSQL getGroupByClause() {
		if(groupByClause== null){
			AggregationVisitor agg = new AggregationVisitor();
			groupByClause = agg.getAggregation(select);
		}
		
		return groupByClause;
	}
	
	public Statement getStatement(){
		return select;
	}
	
	
	
}
