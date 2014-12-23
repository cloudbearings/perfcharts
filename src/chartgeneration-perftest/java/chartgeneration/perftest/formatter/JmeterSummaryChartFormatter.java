package chartgeneration.perftest.formatter;

import chartgeneration.formatter.ChartFormatter;
import chartgeneration.perftest.chart.JmeterSummaryChart;

/**
 * Provides the support for formatting a {@link JmeterSummaryChart} to JSON
 * string.
 * 
 * @author Rayson Zhu
 *
 */
public interface JmeterSummaryChartFormatter extends
		ChartFormatter<JmeterSummaryChart> {
	public String format(JmeterSummaryChart chart) throws Exception;
}
