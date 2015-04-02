package chartgeneration.perftest.generator;

import chartgeneration.chart.GenericTable;
import chartgeneration.config.ChartConfig;
import chartgeneration.formatter.ChartFormatter;
import chartgeneration.formatter.GenericTableFormatter;
import chartgeneration.generator.Generator;
import chartgeneration.generator.GenericTableFactory;
/**
 * A factory for creating creating {@link Generator} and {@link ChartFormatter}
 * objects for {@link JmeterSummaryChart}
 * 
 * @author Rayson Zhu
 *
 */
public interface JmeterSummaryChartFactory extends
		GenericTableFactory {
	@Override
	public JmeterSummaryChartGenerator createGenerator(ChartConfig<GenericTable> config) throws Exception;

	@Override
	public GenericTableFormatter createFormatter() throws Exception;
}
