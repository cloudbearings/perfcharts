package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.chart.Chart;
import com.redhat.chartgeneration.generator.ChartFactory;

/**
 * Represents a chart configuration.
 * A ChartConfig interface defines the {@link #createChartFactory()} method,
 * which creates a factory object for generating corresponding chart.
 * 
 * @author Rayson Zhu
 *
 * @param <T> the type of chart
 */
public interface ChartConfig<T extends Chart> {
	/**
	 * create a factory object for generating this chart
	 * @return
	 * @throws Exception
	 */
	public ChartFactory<T> createChartFactory() throws Exception;
}
