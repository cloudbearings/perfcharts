package com.redhat.chartgeneration.perftest.formatter;

import com.redhat.chartgeneration.formatter.ChartFormatter;
import com.redhat.chartgeneration.perftest.chart.JmeterSummaryChart;

/**
 * Provides the support for formatting a {@link JmeterSummaryChart} to JSON
 * string.
 * 
 * @author Rayson Zhu
 *
 */
public interface JmeterSummaryChartFormatter extends
		ChartFormatter<JmeterSummaryChart> {
	public String format(JmeterSummaryChart chart) throws Exception;
}
