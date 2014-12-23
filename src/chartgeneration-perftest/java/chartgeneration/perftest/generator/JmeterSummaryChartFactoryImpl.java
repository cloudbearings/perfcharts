package chartgeneration.perftest.generator;

import chartgeneration.config.ChartConfig;
import chartgeneration.perftest.chart.JmeterSummaryChart;
import chartgeneration.perftest.config.JmeterSummaryChartConfig;
import chartgeneration.perftest.formatter.JmeterSummaryChartFormatter;
import chartgeneration.perftest.formatter.JmeterSummaryChartFormatterImpl;

/**
 * A default {@link JmeterSummaryChartFactory} implementation.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterSummaryChartFactoryImpl implements JmeterSummaryChartFactory {

	@Override
	public JmeterSummaryChartGenerator createGenerator(
			ChartConfig<JmeterSummaryChart> config) throws Exception {
		return new JmeterSummaryChartGenerator(this,
				(JmeterSummaryChartConfig) config);
	}

	@Override
	public JmeterSummaryChartFormatter createFormatter() throws Exception {
		return new JmeterSummaryChartFormatterImpl();
	}

}
