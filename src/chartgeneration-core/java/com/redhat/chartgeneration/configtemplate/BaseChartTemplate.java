package com.redhat.chartgeneration.configtemplate;

import com.redhat.chartgeneration.common.FieldSelector;

public abstract class BaseChartTemplate implements ChartConfigTemplate {
	private FieldSelector labelField;
	private String title;
	private String subtitle;
	private Object startX, endX;

	public FieldSelector getLabelField() {
		return labelField;
	}

	public void setLabelField(FieldSelector labelField) {
		this.labelField = labelField;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Object getStartX() {
		return startX;
	}

	public void setStartX(Comparable<?> startX) {
		this.startX = startX;
	}

	public Object getEndX() {
		return endX;
	}

	public void setEndX(Comparable<?> endX) {
		this.endX = endX;
	}

}
