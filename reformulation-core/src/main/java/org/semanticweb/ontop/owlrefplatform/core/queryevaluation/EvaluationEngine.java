/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.owlrefplatform.core.queryevaluation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.semanticweb.ontop.model.OBDADataSource;

/**
 * An interface representing the Evaluation engine for a Technique wrapper.
 * The main task of such an evaluation engine is to evaluate the rewritten
 * and unfolded data log program over the data source.
 * 
 * @author Manfred Gerstgrasser
 *
 */

public interface EvaluationEngine {

	/**
	 * Evalutes the given string over the data source
	 * 
	 * @param sql the query
	 * @return the result set
	 * @throws Exception
	 */
	public ResultSet execute(String sql) throws Exception;
	
	public Statement getStatement() throws SQLException;
	
	/**
	 * Updates the current data source with the given one, i.e. after 
	 * the update queries will be evaluated over the new data source.
	 * 
	 * @param ds the new data source
	 */
	public void update(OBDADataSource ds);
	
	public void closeStatement() throws Exception;
	
	public void isCanceled (boolean bool);
	
	public void dispose();
}
