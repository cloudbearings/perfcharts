package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.generator.JmeterSummaryChartFactory;
import com.redhat.chartgeneration.generator.JmeterSummaryChartFactoryImpl;
import com.redhat.chartgeneration.report.JmeterSummaryChart;

public class JmeterSummaryChartConfig extends
		BaseChartConfig<JmeterSummaryChart> {
	@Override
	public JmeterSummaryChartFactory createChartFactory() throws Exception {
		return new JmeterSummaryChartFactoryImpl();
	}

}
