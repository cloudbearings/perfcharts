package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.formatter.JmeterSummaryChartFormatter;
import com.redhat.chartgeneration.report.JmeterSummaryChart;

public interface JmeterSummaryChartFactory extends
		ChartFactory<JmeterSummaryChart> {
	@Override
	public JmeterSummaryChartGenerator createGenerator(ChartConfig<JmeterSummaryChart> config) throws Exception;

	@Override
	public JmeterSummaryChartFormatter createFormatter() throws Exception;
}
