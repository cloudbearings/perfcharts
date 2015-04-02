package chartgeneration.perftest.generator;

import chartgeneration.chart.GenericTable;
import chartgeneration.config.ChartConfig;
import chartgeneration.formatter.GenericTableFormatter;
import chartgeneration.formatter.GenericTableFormatterImpl;
import chartgeneration.perftest.config.JmeterSummaryChartConfig;

/**
 * A default {@link JmeterSummaryChartFactory} implementation.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterSummaryChartFactoryImpl implements JmeterSummaryChartFactory {

	@Override
	public JmeterSummaryChartGenerator createGenerator(
			ChartConfig<GenericTable> config) throws Exception {
		return new JmeterSummaryChartGenerator(this,
				(JmeterSummaryChartConfig) config);
	}

	@Override
	public GenericTableFormatter createFormatter() throws Exception {
		return new GenericTableFormatterImpl();
	}

}
