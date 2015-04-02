package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.AddTransformSelector;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.config.SeriesOrder;
import chartgeneration.configtemplate.Chart2DTemplateWithIntervalBase;

public class JmeterRTChartTemplate extends Chart2DTemplateWithIntervalBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(5);
		FieldSelector xField = new AddTransformSelector(timestampField, rtField);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-S$", "$1", "ms",  getLabelField(),
				xField, rtField, new AverageCalculation(getInterval())));
		Chart2DConfig cfg =  createConfig("Response Time over Time",
				"Time", "Response Time", rules, AxisMode.TIME);
		cfg.setSeriesOrder(SeriesOrder.SERIES_LABEL);
		return cfg;
	}

}
