package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.JmeterSummaryChart;

public interface JmeterSummaryChartFormatter {
	public String format(JmeterSummaryChart chart) throws Exception;
}
