package com.redhat.chartgeneration.perftest.generator;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.perftest.chart.JmeterSummaryChart;
import com.redhat.chartgeneration.perftest.config.JmeterSummaryChartConfig;
import com.redhat.chartgeneration.perftest.formatter.JmeterSummaryChartFormatter;
import com.redhat.chartgeneration.perftest.formatter.JmeterSummaryChartFormatterImpl;

/**
 * A default {@link JmeterSummaryChartFactory} implementation.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterSummaryChartFactoryImpl implements JmeterSummaryChartFactory {

	@Override
	public JmeterSummaryChartGenerator createGenerator(
			ChartConfig<JmeterSummaryChart> config) throws Exception {
		return new JmeterSummaryChartGenerator(this,
				(JmeterSummaryChartConfig) config);
	}

	@Override
	public JmeterSummaryChartFormatter createFormatter() throws Exception {
		return new JmeterSummaryChartFormatterImpl();
	}

}
