package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.JmeterSummaryChart;

public interface JmeterSummaryChartFormatter extends ChartFormatter<JmeterSummaryChart> {
	public String format(JmeterSummaryChart chart) throws Exception;
}
