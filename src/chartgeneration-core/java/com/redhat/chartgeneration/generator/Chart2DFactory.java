package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.chart.Chart2D;
import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.formatter.Chart2DFormatter;
import com.redhat.chartgeneration.formatter.ChartFormatter;

/**
 * A factory for creating creating {@link Generator} and {@link ChartFormatter}
 * objects for {@link Chart2D}
 * 
 * @author Rayson Zhu
 *
 */
public interface Chart2DFactory extends ChartFactory<Chart2D> {
	@Override
	public Chart2DGenerator createGenerator(ChartConfig<Chart2D> config)
			throws Exception;

	@Override
	public Chart2DFormatter createFormatter() throws Exception;
}
