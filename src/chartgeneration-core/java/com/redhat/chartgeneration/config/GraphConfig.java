package com.redhat.chartgeneration.config;

import java.util.List;

import com.redhat.chartgeneration.generator.Generator;
import com.redhat.chartgeneration.generator.GraphGenerator;

public class GraphConfig extends StatChartConfig {
	private String xLabel;
	private String yLabel;
	private List<GraphLineConfigRule> rules;
	private AxisMode xaxisMode = AxisMode.NUMBER;

	public GraphConfig() {

	}

	public GraphConfig(String title, String subtitle, String xLabel,
			String yLabel, List<GraphLineConfigRule> rules) {
		super(title, subtitle);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
	}

	public GraphConfig(String title, String subtitle, String xLabel,
			String yLabel, List<GraphLineConfigRule> rules, AxisMode xaxisMode) {
		super(title, subtitle);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
		this.xaxisMode = xaxisMode;
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

	public List<GraphLineConfigRule> getRules() {
		return rules;
	}

	public void setRules(List<GraphLineConfigRule> rules) {
		this.rules = rules;
	}

	public AxisMode getXaxisMode() {
		return xaxisMode;
	}

	public void setXaxisMode(AxisMode xaxisMode) {
		this.xaxisMode = xaxisMode;
	}

	@Override
	public Generator createGenerator() throws Exception {
		return new GraphGenerator(this);
	}

}
