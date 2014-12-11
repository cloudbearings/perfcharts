package com.redhat.chartgeneration.common;

import java.util.List;

public class AddTransformSelector extends BinarySelector {

	public AddTransformSelector() {

	}

	public AddTransformSelector(FieldSelector firstOperand,
			FieldSelector secondOperand) {
		super(firstOperand, secondOperand);
	}

	@Override
	public Object select(List<?> row) {
		Number a = (Number) getFirstOperand().select(row);
		Number b = (Number) getSecondOperand().select(row);
		return a.longValue() + b.longValue();
	}

}
