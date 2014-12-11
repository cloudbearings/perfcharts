package com.redhat.chartgeneration.config;

import java.util.List;

public class LineGraphConfig {
	private String title;
	private String subtitle;
	private String xLabel;
	private String yLabel;
	private List<LineConfigRule> rules;
	private AxisMode xaxisMode = AxisMode.NUMBER;

	public LineGraphConfig() {

	}

	public LineGraphConfig(String title, String subtitle, String xLabel, String yLabel,
			List<LineConfigRule> rules) {
		this.title = title;
		this.subtitle = subtitle;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
	}

	public LineGraphConfig(String title, String subtitle, String xLabel, String yLabel,
			List<LineConfigRule> rules, AxisMode xaxisMode) {
		this.title = title;
		this.subtitle = subtitle;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
		this.xaxisMode = xaxisMode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXLabel() {
		return xLabel;
	}

	public void setXLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getYLabel() {
		return yLabel;
	}

	public void setYLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public List<LineConfigRule> getRules() {
		return rules;
	}

	public void setRules(List<LineConfigRule> rules) {
		this.rules = rules;
	}

	public AxisMode getXaxisMode() {
		return xaxisMode;
	}

	public void setXaxisMode(AxisMode xaxisMode) {
		this.xaxisMode = xaxisMode;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
