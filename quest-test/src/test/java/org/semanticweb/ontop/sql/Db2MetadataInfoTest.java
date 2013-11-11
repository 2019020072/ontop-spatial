/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.sql;

public class Db2MetadataInfoTest extends AbstractDbMetadataInfoTest {

	@Override
	protected String getConnectionPassword() {
		return "fish";
	}

	@Override
	protected String getConnectionString() {
		return "jdbc:db2://10.7.20.39:50001/datatypes";
	}

	@Override
	protected String getConnectionUsername() {
		return "db2inst2";
	}

	@Override
	protected String getDriverName() {
		return "com.ibm.db2.jcc.DB2Driver";
	}
}
