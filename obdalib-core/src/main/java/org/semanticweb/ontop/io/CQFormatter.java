package org.semanticweb.ontop.io;

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

import org.semanticweb.ontop.model.CQIE;

/**
 * Classes that extend this abstract class must include a prefix manager to
 * support prefixed names. The <code>print(CQIE)</code> method is used to
 * format the conjunctive queries into a particular printed string.
 */
public abstract class CQFormatter {
	
	protected PrefixManager prefixManager;
	
	public CQFormatter(PrefixManager pm) {
		prefixManager = pm;
	}
	
	public abstract String print(CQIE query);
}
