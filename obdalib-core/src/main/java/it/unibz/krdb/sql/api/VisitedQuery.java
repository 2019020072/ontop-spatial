/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package it.unibz.krdb.sql.api;

import it.unibz.krdb.obda.parser.AggregationVisitor;
import it.unibz.krdb.obda.parser.AliasMapVisitor;
import it.unibz.krdb.obda.parser.ColumnsVisitor;
import it.unibz.krdb.obda.parser.JoinConditionVisitor;
import it.unibz.krdb.obda.parser.ProjectionVisitor;
import it.unibz.krdb.obda.parser.SelectionVisitor;
import it.unibz.krdb.obda.parser.SubSelectVisitor;
import it.unibz.krdb.obda.parser.TablesNameVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
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
	boolean unquote=false; //used to remove all quotes from the query
	 
	private Select select; //the parsed query
	
	public static Pattern pQuotes;
	private ArrayList<RelationJSQL> tableSet;
	private ArrayList<SelectJSQL> selectsSet;
	private HashMap<String, String> aliasMap;
	private ArrayList<Expression> joins;
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
	 * @param unquote if true removes quotes from columns
	 * @throws JSQLParserException 
	 */
	
	public VisitedQuery(String queryString, boolean unquote) throws JSQLParserException {
		
		/**
		 * pattern used to remove quotes from the beginning and the end of columns
		 */
		pQuotes= Pattern.compile("[\"`\\[].*[\"`\\]]"); 
		
		query = queryString;
	 
	    this.unquote=unquote;
	    
		stm = CCJSqlParserUtil.parse(query);
		
			if (stm instanceof Select) {
				select = (Select)stm;
				
				
				//getting the values we also eliminate or handle the quotes if unquote is set to true
				if(unquote){	
				tableSet = getTableSet();
				selection = getSelection();
				projection = getProjection();
				joins = getJoinCondition();
				aliasMap = getAliasMap();
				groupByClause =getGroupByClause();
				}
				
			}
						//catch exception about wrong inserted columns
			else 
				throw new JSQLParserException("The inserted query is not a SELECT statement");

		
	}
	
	/**
	 * The query is not parsed again
	 * @param statement we pass already a parsed statement
	 * @param unquote emove quotes if present
	 * @throws JSQLParserException
	 */
	public VisitedQuery(Statement statement, boolean unquote) throws JSQLParserException{
		
		pQuotes= Pattern.compile("[\"`\\[].*[\"`\\]]"); 
		
		query = statement.toString();
	 
		stm=statement;
	    
		this.unquote=unquote;
	  
		
			if (stm instanceof Select) {
				select = (Select)stm;
				
				
				//getting the values we also eliminate or handle the quotes if unquote is set to true
				if(unquote){	
				tableSet = getTableSet();
				selection = getSelection();
				projection = getProjection();
				joins = getJoinCondition();
				aliasMap = getAliasMap();
				groupByClause =getGroupByClause();
				}
				
			}
						//catch exception about wrong inserted columns
			else 
				throw new JSQLParserException("The inserted query is not a SELECT statement");
		

	}
	
	
	

	@Override
	public String toString() {
		return select.toString(); 
	}
	
	/**
	 * Returns all the tables in this query.
	 */
	public ArrayList<RelationJSQL> getTableSet() throws JSQLParserException {
		
		if(tableSet== null){
			TablesNameVisitor tnp = new TablesNameVisitor();
			tableSet =tnp.getTableList(select);
		}
		return tableSet;
	}
	
	/**
	 * Returns all the subSelect in this query .
	 */
	public ArrayList<SelectJSQL> getSubSelectSet() {
		
		if(selectsSet== null){
			SubSelectVisitor tnp = new SubSelectVisitor();
			selectsSet =tnp.getSubSelectList(select);
		}
		return selectsSet;
	}
	
	/**
	 * Get the string construction of alias name. 
	 */
	public HashMap<String, String> getAliasMap() {
		if(aliasMap== null){
			AliasMapVisitor aliasV = new AliasMapVisitor();
			aliasMap= aliasV.getAliasMap(select, unquote);
		}
		return aliasMap;
	}

	/**
	 * Get the string construction of the join condition. The string has the
	 * format of "VAR1=VAR2".
	 */
	public ArrayList<Expression> getJoinCondition() throws JSQLParserException {
		if(joins==null){
			JoinConditionVisitor joinCV = new JoinConditionVisitor();
			joins= joinCV.getJoinConditions(select,unquote);
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
			selection= sel.getSelection(select,unquote);
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
			projection= proj.getProjection(select,unquote);
		}
		return projection;
		
	}
	
	/**
	 * Get the list of columns 
	 * @return
	 */
	public List<String> getColumns() {
		ColumnsVisitor col = new ColumnsVisitor();
		
		return col.getColumns(select);
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
			groupByClause = agg.getAggregation(select,unquote);
		}
		
		return groupByClause;
	}
	
	public Statement getStatement(){
		return select;
	}
	
	
	
}
