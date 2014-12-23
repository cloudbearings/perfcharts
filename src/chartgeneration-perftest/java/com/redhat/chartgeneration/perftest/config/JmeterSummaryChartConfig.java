package com.redhat.chartgeneration.perftest.config;

import com.redhat.chartgeneration.config.BaseChartConfig;
import com.redhat.chartgeneration.perftest.chart.JmeterSummaryChart;
import com.redhat.chartgeneration.perftest.generator.JmeterSummaryChartFactory;
import com.redhat.chartgeneration.perftest.generator.JmeterSummaryChartFactoryImpl;

/**
 * The configuration for Jmeter summary chart
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterSummaryChartConfig extends
		BaseChartConfig<JmeterSummaryChart> {
	@Override
	public JmeterSummaryChartFactory createChartFactory() throws Exception {
		return new JmeterSummaryChartFactoryImpl();
	}

}
