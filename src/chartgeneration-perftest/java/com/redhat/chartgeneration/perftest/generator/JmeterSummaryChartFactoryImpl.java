package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.config.JmeterSummaryChartConfig;
import com.redhat.chartgeneration.formatter.JmeterSummaryChartFormatter;
import com.redhat.chartgeneration.formatter.JmeterSummaryChartFormatterImpl;
import com.redhat.chartgeneration.report.JmeterSummaryChart;

public class JmeterSummaryChartFactoryImpl implements JmeterSummaryChartFactory {

	@Override
	public JmeterSummaryChartGenerator createGenerator(ChartConfig<JmeterSummaryChart> config) throws Exception {
		return new JmeterSummaryChartGenerator(this, (JmeterSummaryChartConfig)config);
	}

	@Override
	public JmeterSummaryChartFormatter createFormatter()
			throws Exception {
		return new JmeterSummaryChartFormatterImpl();
	}

}
