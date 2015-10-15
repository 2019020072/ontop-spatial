package it.unibz.krdb.sql;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
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

import java.sql.Types;

public class Attribute {
	
	private final QuotedID id;
	private final int index;
	private final int type;
	private final String typeName;
	private final boolean canNull;
	
	private final RelationDefinition table;
	
	Attribute(RelationDefinition relation, int index, QuotedID id, int type, String typeName, boolean canNull) {
		this.table = relation;
		this.index = index;
		this.id = id;
		this.type = type;
		this.typeName = typeName;
		this.canNull = canNull;
	}
	
	public QuotedID getID() {
		return id;
	}
	
	public QualifiedAttributeID getQualifiedID() {
		return new QualifiedAttributeID(table.getID(), id);
	}
	
	public RelationDefinition getRelation() {
		return table;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean canNull() {
		return canNull;
	}
	
	/***
	 * Returns the name of the SQL type associated with this attribute. Note, the name maybe not match
	 * the integer SQL id. The integer SQL id comes from the {@link Types} class, and these are few. Often
	 * databases match extra datatypes they may provide to the same ID, e.g., in MySQL YEAR (which doesn't
	 * exists in standard SQL, is mapped to 91, the ID of DATE. This field helps in disambiguating this 
	 * cases.
	 * 
	 * @return
	 */
	public String getSQLTypeName() {
		return typeName;
	}
	
	@Override
	public String toString() {
		return id + ": " + type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj instanceof Attribute) {
			Attribute other = (Attribute)obj;
			//                                 the same reference(!) for the table
			return this.id.equals(other.id) && (this.table == other.table);  
		}
		
		return false;
	}
	
	@Override 
	public int hashCode() {
		return id.hashCode();
	}
}
