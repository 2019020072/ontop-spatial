/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.sql.api;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForeignKey {
	
	private String SchemaName;
	private String CoPKTableName;
	private String CoPKColumnName;

	public ForeignKey(){
		SchemaName = null;
	}
	
	public String getCoPKTable(){
		return this.CoPKTableName;
	}
	
	public String getCoPKColumn(){
		return this.CoPKColumnName;
	}
	
	public void setSchemaName(String sn){
		this.SchemaName = sn;
	}
	
	//find the table and column where a Foreign key comes from
	public ForeignKey(DatabaseMetaData md, String tableName, String FK){
		try{
			ResultSet rs = md.getImportedKeys(null, this.SchemaName, tableName);
			while (rs.next()){
				String getFK=rs.getString("FKCOLUMN_NAME");
				if(FK.equalsIgnoreCase(getFK)){
					this.CoPKTableName = rs.getString("PKTABLE_NAME");
					this.CoPKColumnName = rs.getString("PKCOLUMN_NAME");
				}
			}
		}
		catch (SQLException e){  
            e.printStackTrace();     
        } 
	}

}
