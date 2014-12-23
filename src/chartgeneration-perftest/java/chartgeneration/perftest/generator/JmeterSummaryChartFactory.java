package chartgeneration.perftest.generator;

import chartgeneration.config.ChartConfig;
import chartgeneration.formatter.ChartFormatter;
import chartgeneration.generator.ChartFactory;
import chartgeneration.generator.Generator;
import chartgeneration.perftest.chart.JmeterSummaryChart;
import chartgeneration.perftest.formatter.JmeterSummaryChartFormatter;
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
