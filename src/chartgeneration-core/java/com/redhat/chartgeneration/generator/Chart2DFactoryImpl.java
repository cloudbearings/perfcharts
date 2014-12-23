package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.chart.Chart2D;
import com.redhat.chartgeneration.config.Chart2DConfig;
import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.formatter.Chart2DFormatter;
import com.redhat.chartgeneration.formatter.FlotChartFormatter;

/**
 * A default {@link Chart2DFactory} implementation.
 * 
 * @author Rayson Zhu
 *
 */
public class Chart2DFactoryImpl implements Chart2DFactory {

	@Override
	public Chart2DGenerator createGenerator(ChartConfig<Chart2D> config)
			throws Exception {
		Chart2DConfig cfg = (Chart2DConfig) config;
		return new Chart2DGenerator(this, cfg);
	}

	@Override
	public Chart2DFormatter createFormatter() throws Exception {
		return new FlotChartFormatter();
	}

}
