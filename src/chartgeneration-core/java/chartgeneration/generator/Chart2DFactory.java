package chartgeneration.generator;

import chartgeneration.chart.Chart2D;
import chartgeneration.config.ChartConfig;
import chartgeneration.formatter.Chart2DFormatter;
import chartgeneration.formatter.ChartFormatter;

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
