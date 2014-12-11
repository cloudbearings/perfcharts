package com.redhat.chartgeneration.common;

import java.util.List;

public class ConstantSelector implements FieldSelector {
	private Object constant;

	public ConstantSelector() {

	}

	public ConstantSelector(Object constant) {
		this.constant = constant;
	}

	@Override
	public Object select(List<?> row) {
		return constant;
	}

	public Object getConstant() {
		return constant;
	}

	public void setConstant(Object constant) {
		this.constant = constant;
	}

}
