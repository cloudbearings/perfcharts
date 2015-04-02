package chartgeneration.configtemplate;

import chartgeneration.chart.Chart;
import chartgeneration.config.ChartConfig;
import chartgeneration.perftest.config.PerformanceComparisonTableConfig;

public class PerformanceComparisonTableTemplate extends ChartTemplateBase {

	@Override
	public ChartConfig<? extends Chart> generateChartConfig() {
		PerformanceComparisonTableConfig config = new PerformanceComparisonTableConfig();
		config.setTitle("Performance Comparison");
		config.setSubtitle(getSubtitle());
		return config;
	}

}
