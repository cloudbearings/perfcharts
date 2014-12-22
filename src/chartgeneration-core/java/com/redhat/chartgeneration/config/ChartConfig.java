package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.generator.ChartFactory;
import com.redhat.chartgeneration.report.Chart;

public interface ChartConfig<T extends Chart> {
	public ChartFactory<T> createChartFactory() throws Exception;
}
