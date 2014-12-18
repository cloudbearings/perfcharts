package com.redhat.chartgeneration.configtemplate;

import java.util.List;

import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.config.GraphLineConfigRule;

public abstract class BaseGraphTemplate extends BaseChartTemplate {
	private String xLabel;
	private String yLabel;

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

	protected GraphConfig createConfig(String defaultTitle,
			String defaultXLabel, String defaultYLabel,
			List<GraphLineConfigRule> rules, AxisMode xaxisMode) {
		return createConfig(defaultTitle, null, defaultXLabel, defaultYLabel,
				rules, xaxisMode);
	}

	protected GraphConfig createConfig(String defaultTitle,
			String defaultSubtitle, String defaultXLabel, String defaultYLabel,
			List<GraphLineConfigRule> rules, AxisMode xaxisMode) {
		String title = getTitle();
		String subtitle = getSubtitle();
		return new GraphConfig(title == null ? defaultTitle : title,
				subtitle == null ? defaultSubtitle : subtitle,
				xLabel == null ? defaultXLabel : xLabel,
				yLabel == null ? defaultYLabel : yLabel, rules, xaxisMode);
	}

	public abstract GraphConfig generateChartConfig();
	
	
}
