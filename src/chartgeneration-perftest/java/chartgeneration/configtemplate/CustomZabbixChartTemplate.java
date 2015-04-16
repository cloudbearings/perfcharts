package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;

public class CustomZabbixChartTemplate extends Chart2DTemplateWithIntervalBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector value = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("ZABBIX-(.+)", "$1", "",
				getLabelField(), timestampField, value,
				new AverageCalculation(getInterval())));
		return createConfig("Zabbix Monitoring Raw Result", "Time",
				"Value", rules, AxisMode.TIME);
	}
}