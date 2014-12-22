package com.redhat.chartgeneration.config;

import java.util.Set;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.graphcalc.GraphCalculation;

public class GraphSeriesConfig {
	private String label;
	private Comparable<?> startX;
	private Comparable<?> endX;
	private FieldSelector labelField;
	private FieldSelector xField;
	private FieldSelector yField;
	private GraphCalculation calculation;
	private Set<String> involvedRowLabels;
	private boolean showLines = true;
	private boolean showBars = false;
	private String unit;
	private boolean showUnit;

	public GraphSeriesConfig() {
	}

	public GraphSeriesConfig(String label, String unit, FieldSelector labelField,
			FieldSelector xField, FieldSelector yField,
			GraphCalculation calculation, Set<String> involvedRowLabels,
			boolean showLines, boolean showBars, boolean showUnit) {
		this.label = label;
		this.unit = unit;
		this.labelField = labelField;
		this.xField = xField;
		this.yField = yField;
		this.calculation = calculation;
		this.involvedRowLabels = involvedRowLabels;
		this.showBars = showBars;
		this.showLines = showLines;
		this.showUnit = showUnit;
	}

	public GraphSeriesConfig(String label, Comparable<?> startX, Comparable<?> endX,
			String unit, FieldSelector labelField, FieldSelector xField,
			FieldSelector yField, GraphCalculation calculation,
			Set<String> involvedRowLabels, boolean showLines, boolean showBars,
			boolean showUnit) {
		this(label, unit, labelField, xField, yField, calculation,
				involvedRowLabels, showLines, showBars, showUnit);
		this.startX = startX;
		this.endX = endX;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public FieldSelector getXField() {
		return xField;
	}

	public void setXField(FieldSelector xField) {
		this.xField = xField;
	}

	public FieldSelector getYField() {
		return yField;
	}

	public void setYField(FieldSelector yField) {
		this.yField = yField;
	}

	public GraphCalculation getCalculation() {
		return calculation;
	}

	public void setCalculation(GraphCalculation calculation) {
		this.calculation = calculation;
	}

	public FieldSelector getLabelField() {
		return labelField;
	}

	public void setLabelField(FieldSelector labelField) {
		this.labelField = labelField;
	}

	public Set<String> getInvolvedRowLabels() {
		return involvedRowLabels;
	}

	public void setInvolvedRowLabels(Set<String> involvedRowLabels) {
		this.involvedRowLabels = involvedRowLabels;
	}

	public FieldSelector getxField() {
		return xField;
	}

	public void setxField(FieldSelector xField) {
		this.xField = xField;
	}

	public FieldSelector getyField() {
		return yField;
	}

	public void setyField(FieldSelector yField) {
		this.yField = yField;
	}

	public boolean isShowLines() {
		return showLines;
	}

	public void setShowLines(boolean showLines) {
		this.showLines = showLines;
	}

	public boolean isShowBars() {
		return showBars;
	}

	public void setShowBars(boolean showBars) {
		this.showBars = showBars;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isShowUnit() {
		return showUnit;
	}

	public void setShowUnit(boolean showUnit) {
		this.showUnit = showUnit;
	}

	public Comparable<?> getStartX() {
		return startX;
	}

	public void setStartX(Comparable<?> startX) {
		this.startX = startX;
	}

	public Comparable<?> getEndX() {
		return endX;
	}

	public void setEndX(Comparable<?> endX) {
		this.endX = endX;
	}

}
