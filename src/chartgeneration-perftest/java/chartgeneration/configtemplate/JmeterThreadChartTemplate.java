package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.configtemplate.Chart2DTemplateWithIntervalBase;

public class JmeterThreadChartTemplate extends Chart2DTemplateWithIntervalBase {
	@Override
	public Chart2DConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector threadsField = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-[SF]$",
				"Virtual Users", "", getLabelField(), timestampField,
				threadsField, new AverageCalculation(getInterval())));
		return createConfig("Concurrent Virtual Users", "Time",
				"Concurrent Virtual Users", rules, AxisMode.TIME);

	}
}
