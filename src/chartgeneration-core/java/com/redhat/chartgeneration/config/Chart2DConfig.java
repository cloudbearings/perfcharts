package com.redhat.chartgeneration.config;

import java.util.List;

import com.redhat.chartgeneration.generator.GraphFactory;
import com.redhat.chartgeneration.generator.GraphFactoryImpl;
import com.redhat.chartgeneration.report.Graph;

public class GraphConfig extends BaseChartConfig<Graph> {
	private String xLabel;
	private String yLabel;
	private List<GraphSeriesConfigRule> rules;
	private AxisMode xaxisMode = AxisMode.NUMBER;

	public GraphConfig() {

	}

	public GraphConfig(String title, String subtitle, String xLabel,
			String yLabel, List<GraphSeriesConfigRule> rules) {
		super(title, subtitle);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
	}

	public GraphConfig(String title, String subtitle, String xLabel,
			String yLabel, List<GraphSeriesConfigRule> rules, AxisMode xaxisMode) {
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

	public List<GraphSeriesConfigRule> getRules() {
		return rules;
	}

	public void setRules(List<GraphSeriesConfigRule> rules) {
		this.rules = rules;
	}

	public AxisMode getXaxisMode() {
		return xaxisMode;
	}

	public void setXaxisMode(AxisMode xaxisMode) {
		this.xaxisMode = xaxisMode;
	}

	@Override
	public GraphFactory createChartFactory() throws Exception {
		return new GraphFactoryImpl();
	}

}
