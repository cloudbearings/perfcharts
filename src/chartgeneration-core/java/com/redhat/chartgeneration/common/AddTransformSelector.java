package com.redhat.chartgeneration.common;

import java.util.List;

/**
 * An AddTransformSelector is {@link BinarySelector} that adds the values
 * extracted by the other two {@link FieldSelector}s and regards the sum as its
 * extracted value.
 * 
 * @author Rayson Zhu
 *
 */
public class AddTransformSelector extends BinarySelector {

	public AddTransformSelector() {

	}

	public AddTransformSelector(FieldSelector firstOperand,
			FieldSelector secondOperand) {
		super(firstOperand, secondOperand);
	}

	/**
	 * Add the values extracted by two other {@link FieldSelector}s, and return
	 * the sum.
	 * 
	 * @see #setFirstOperand(FieldSelector)
	 * @see #setSecondOperand(FieldSelector)
	 */
	@Override
	public Object select(List<?> row) {
		Number a = (Number) getFirstOperand().select(row);
		Number b = (Number) getSecondOperand().select(row);
		return a.longValue() + b.longValue();
	}

}
