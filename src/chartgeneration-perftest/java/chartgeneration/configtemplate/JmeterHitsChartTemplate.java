package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.CountCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.configtemplate.Chart2DTemplateWithIntervalBase;

public class JmeterHitsChartTemplate extends Chart2DTemplateWithIntervalBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		if (interval < 1)
			interval = 10000;
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector yField = new IndexFieldSelector(2);
		//FieldSelector xField = new AddTransformSelector(timestampField,yField);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^HIT$", "Hits", "",
				getLabelField(), timestampField, yField, new CountCalculation(
						interval)));
		return createConfig("Hits over Time", "Time",
				"Hits", rules, AxisMode.TIME);
	}

}
