package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.graphcalc.GraphCalculation;

public class TableColumnConfigRule {
	private String label;
	private String unit;
	private GraphCalculation calculation;

	public TableColumnConfigRule() {

	}

	public TableColumnConfigRule(String label, GraphCalculation calculation,
			String unit) {
		this.label = label;
		this.calculation = calculation;
		this.unit = unit;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public GraphCalculation getCalculation() {
		return calculation;
	}

	public void setCalculation(GraphCalculation calculation) {
		this.calculation = calculation;
	}
	
}
