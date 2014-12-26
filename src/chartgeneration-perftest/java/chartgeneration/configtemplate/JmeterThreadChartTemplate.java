package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.configtemplate.BaseChart2DTemplateWithInterval;

public class JmeterThreadChartTemplate extends BaseChart2DTemplateWithInterval {
	@Override
	public Chart2DConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector threadsField = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-[SF]$", "virtual users", "VU",
				getLabelField(), timestampField, threadsField, new AverageCalculation(
						getInterval())));
		return createConfig("Concurrent Virtual Users",
				"time", "virtual users", rules, AxisMode.TIME);

	}
}
