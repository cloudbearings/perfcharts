package chartgeneration.perftest.config;

import chartgeneration.config.GenericTableConfigBase;
import chartgeneration.perftest.generator.JmeterSummaryChartFactory;
import chartgeneration.perftest.generator.JmeterSummaryChartFactoryImpl;

/**
 * The configuration for Jmeter summary chart
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterSummaryChartConfig extends
		GenericTableConfigBase {
	@Override
	public JmeterSummaryChartFactory createChartFactory() throws Exception {
		return new JmeterSummaryChartFactoryImpl();
	}

}
