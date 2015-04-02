package chartgeneration.config;

import chartgeneration.chart.GenericTable;
import chartgeneration.generator.GenericTableFactory;

public abstract class GenericTableConfigBase extends ChartConfigBase<GenericTable> {

	@Override
	public abstract GenericTableFactory createChartFactory() throws Exception;

}
