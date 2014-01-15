package org.semanaticweb.ontop.sql.api;

/*
 * #%L
 * ontop-obdalib-core
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

/**
 * This class represents the literal of decimal value.
 */
public class DecimalLiteral extends NumericLiteral {

	private static final long serialVersionUID = 6801280683568130405L;
	
	/**
	 * The float value.
	 */
	protected Float value;

	public DecimalLiteral(String value) {
		set(new Float(value));
	}

	public void set(Float value) {
		this.value = value;
	}

	public Float get() {
		return value;
	}

	@Override
	public String toString() {
		return get().toString();
	}
}
