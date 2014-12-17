package com.redhat.chartgeneration.configtemplate;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.JmeterSummaryChartConfig;

public class JmeterSummaryChartTemplate implements ChartConfigTemplate {
	private FieldSelector labelField = new IndexFieldSelector(0);
	private String title;
	private String subtitle;
	private int interval = 1000;
	@Override
	public JmeterSummaryChartConfig generateChartConfig() {
		//List<TableRowConfigRule> rowRules = new ArrayList<TableRowConfigRule>();
		//rowRules.add(new TableRowConfigRule("$1", "TX-(.+)", null,  null, labelField, new IndexFieldSelector(0), new IndexFieldSelector(0));
		//List<TableColumnConfigRule> columnRules = new ArrayList<TableColumnConfigRule>();
		JmeterSummaryChartConfig config = new JmeterSummaryChartConfig();
		config.setTitle("Summary Chart");
		return config;
	}
	
	public JmeterSummaryChartTemplate() {
	}

	@Override
	public FieldSelector getLabelField() {
		return labelField;
	}

	@Override
	public void setLabelField(FieldSelector labelField) {
		this.labelField = labelField;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
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

}
