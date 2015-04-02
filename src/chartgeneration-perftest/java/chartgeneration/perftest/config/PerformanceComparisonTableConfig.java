package chartgeneration.perftest.config;

import chartgeneration.config.GenericTableConfigBase;
import chartgeneration.generator.GenericTableFactory;
import chartgeneration.perftest.generator.PerformanceComparisonTableFactoryImpl;

public class PerformanceComparisonTableConfig extends GenericTableConfigBase {

	@Override
	public GenericTableFactory createChartFactory()
			throws Exception {
		return new PerformanceComparisonTableFactoryImpl();
	}

}
