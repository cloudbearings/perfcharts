package com.redhat.chartgeneration.report;

import java.util.List;

import com.redhat.chartgeneration.formatter.JmeterSummaryChartFormatter;

public class JmeterSummaryChart extends Chart {
	private JmeterSummaryChartFormatter formatter;
	private List<List<Object>> series;
	private List<String> columnLabels;

	public JmeterSummaryChart() {
	}

	public JmeterSummaryChart(String title, String subtitle,
			List<String> columnLabels, List<List<Object>> series) {
		super(title, subtitle);
		this.series = series;
		this.columnLabels = columnLabels;
	}

	@Override
	public String format() throws Exception {
		return formatter.format(this);
	}

	public List<List<Object>> getSeries() {
		return series;
	}

	public void setSeries(List<List<Object>> series) {
		this.series = series;
	}

	public List<String> getColumnLabels() {
		return columnLabels;
	}

	public void setColumnLabels(List<String> columnLabels) {
		this.columnLabels = columnLabels;
	}

	public JmeterSummaryChartFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(JmeterSummaryChartFormatter formatter) {
		this.formatter = formatter;
	}

}
