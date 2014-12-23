package com.redhat.chartgeneration.perftest.generator;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.formatter.ChartFormatter;
import com.redhat.chartgeneration.generator.ChartFactory;
import com.redhat.chartgeneration.generator.Generator;
import com.redhat.chartgeneration.perftest.chart.JmeterSummaryChart;
import com.redhat.chartgeneration.perftest.formatter.JmeterSummaryChartFormatter;
/**
 * A factory for creating creating {@link Generator} and {@link ChartFormatter}
 * objects for {@link JmeterSummaryChart}
 * 
 * @author Rayson Zhu
 *
 */
public interface JmeterSummaryChartFactory extends
		ChartFactory<JmeterSummaryChart> {
	@Override
	public JmeterSummaryChartGenerator createGenerator(ChartConfig<JmeterSummaryChart> config) throws Exception;

	@Override
	public JmeterSummaryChartFormatter createFormatter() throws Exception;
}
