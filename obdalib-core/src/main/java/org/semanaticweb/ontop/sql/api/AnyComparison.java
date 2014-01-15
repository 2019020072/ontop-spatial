package org.semanaticweb.ontop.sql.api;

import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Auxiliary Class used to visualize AnyComparison in string format.
 * Any and Some are the same in SQL so we consider always the case of ANY
 *
 */

public class AnyComparison extends AnyComparisonExpression{

	public AnyComparison(SubSelect subSelect) {
		super(subSelect);
	}
	
	@Override
	public String toString(){
		return "ANY "+getSubSelect();
	}

}
