package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.common.FieldSelector;

public class TableRowConfigRule {
	private String labelPattern;
	private String seriesLabelFormat;
	private Comparable<?> startX;
	private Comparable<?> endX;
	private FieldSelector labelField;
	private FieldSelector xField;
	private FieldSelector yField;

	public TableRowConfigRule() {

	}

	public TableRowConfigRule(String labelPattern, String seriesLabelFormat,
			Comparable<?> startX, Comparable<?> endX, FieldSelector labelField,
			FieldSelector xField, FieldSelector yField) {
		this.labelPattern = labelPattern;
		this.seriesLabelFormat = seriesLabelFormat;
		this.labelField = labelField;
		this.xField = xField;
		this.yField = yField;
		this.startX = startX;
		this.endX = endX;
	}

	public String getLabelPattern() {
		return labelPattern;
	}

	public void setLabelPattern(String labelPattern) {
		this.labelPattern = labelPattern;
	}

	public String getSeriesLabelFormat() {
		return seriesLabelFormat;
	}

	public void setSeriesLabelFormat(String seriesLabelFormat) {
		this.seriesLabelFormat = seriesLabelFormat;
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

	public FieldSelector getLabelField() {
		return labelField;
	}

	public void setLabelField(FieldSelector labelField) {
		this.labelField = labelField;
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

}
