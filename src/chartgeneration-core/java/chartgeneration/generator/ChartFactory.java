package chartgeneration.generator;

import chartgeneration.chart.Chart;
import chartgeneration.config.ChartConfig;
import chartgeneration.formatter.ChartFormatter;

/**
 * A factory interface for creating {@link Generator} and {@link ChartFormatter}
 * objects for specific type of {@link Chart}
 * 
 * @param T
 *            the type of {@link Chart}
 * @author Rayson Zhu
 *
 */
public interface ChartFactory<T extends Chart> {
	/**
	 * Create a generator for this kind of chart.
	 * 
	 * @param config
	 *            a configuration object
	 * @return a generator
	 * @throws Exception
	 */
	public Generator createGenerator(ChartConfig<T> config) throws Exception;

	/**
	 * Create a formatter for this kind of chart.
	 * 
	 * @return a formatter
	 * @throws Exception
	 */
	public ChartFormatter<T> createFormatter() throws Exception;
}
