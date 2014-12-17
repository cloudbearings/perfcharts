package com.redhat.chartgeneration.configtemplate;

import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;

public abstract class BaseChartTemplate implements ChartConfigTemplate {
	private FieldSelector labelField;
	private String title;
	private String subtitle;
	private String xLabel;
	private String yLabel;

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
		return new GraphConfig(title == null ? defaultTitle : title,
				subtitle == null ? defaultSubtitle : subtitle,
				xLabel == null ? defaultXLabel : xLabel,
				yLabel == null ? defaultYLabel : yLabel, rules, xaxisMode);
	}

	public abstract GraphConfig generateChartConfig();
}
