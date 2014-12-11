package com.redhat.chartgeneration.common;

import java.util.List;

public abstract class BinarySelector implements FieldSelector {
	private FieldSelector firstOperand;
	private FieldSelector secondOperand;

	public BinarySelector() {

	}

	public BinarySelector(FieldSelector firstOperand,
			FieldSelector secondOperand) {
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
	}

	@Override
	public Object select(List<?> row) {
		// TODO Auto-generated method stub
		return null;
	}

	public FieldSelector getFirstOperand() {
		return firstOperand;
	}

	public void setFirstOperand(FieldSelector firstOperand) {
		this.firstOperand = firstOperand;
	}

	public FieldSelector getSecondOperand() {
		return secondOperand;
	}

	public void setSecondOperand(FieldSelector secondOperand) {
		this.secondOperand = secondOperand;
	}
}
