package chartgeneration.perftest.generator;

import chartgeneration.chart.GenericTable;
import chartgeneration.config.ChartConfig;
import chartgeneration.generator.GenericTableFactoryBase;
import chartgeneration.perftest.config.PerformanceComparisonTableConfig;

public class PerformanceComparisonTableFactoryImpl extends
		GenericTableFactoryBase {

	@Override
	public PerformanceComparisonTableGenerator createGenerator(
			ChartConfig<GenericTable> config) throws Exception {
		return new PerformanceComparisonTableGenerator(
				(PerformanceComparisonTableConfig) config);
	}

}
