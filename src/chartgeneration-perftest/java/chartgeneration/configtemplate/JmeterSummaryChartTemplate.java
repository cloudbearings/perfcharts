package chartgeneration.configtemplate;

import chartgeneration.configtemplate.BaseChartTemplate;
import chartgeneration.perftest.config.JmeterSummaryChartConfig;

public class JmeterSummaryChartTemplate extends BaseChartTemplate {

	public JmeterSummaryChartConfig generateChartConfig() {
		JmeterSummaryChartConfig config = new JmeterSummaryChartConfig();
		config.setTitle("Summary Chart");
		return config;
	}

	public JmeterSummaryChartTemplate() {
	}
}
