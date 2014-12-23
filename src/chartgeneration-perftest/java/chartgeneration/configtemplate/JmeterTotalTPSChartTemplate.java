package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.CountCalculation;
import chartgeneration.common.AddTransformSelector;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.configtemplate.BaseChart2DTemplateWithInterval;

public class JmeterTotalTPSChartTemplate extends
		BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		if (interval < 1)
			interval = 10000;
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(5);
		FieldSelector xField = new AddTransformSelector(timestampField, rtField);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-S$", "Success", "TPS",
				getLabelField(), xField, null, new CountCalculation(
						interval, 1000.0 / interval, false)));
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-F$", "Failure", "TPS",
				getLabelField(), xField, null, new CountCalculation(
						interval, 1000.0 / interval, true)));
		return createConfig("Total TPS over Time", "time", "TPS", rules,
				AxisMode.TIME);
	}

}
