package chartgeneration.generator;

import chartgeneration.chart.GenericTable;
import chartgeneration.config.ChartConfig;
import chartgeneration.formatter.ChartFormatter;
import chartgeneration.formatter.GenericTableFormatterImpl;

public abstract class GenericTableFactoryBase implements GenericTableFactory {

	@Override
	public abstract Generator createGenerator(ChartConfig<GenericTable> config)
			throws Exception;

	@Override
	public ChartFormatter<GenericTable> createFormatter() throws Exception {
		return new GenericTableFormatterImpl();
	}


}
