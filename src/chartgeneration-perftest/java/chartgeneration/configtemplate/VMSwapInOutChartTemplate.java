package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;

public class VMSwapInOutChartTemplate extends BaseChart2DTemplateWithInterval {
	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector swapInField = new IndexFieldSelector(2);
		FieldSelector swapOutField = new IndexFieldSelector(3);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^VM$", "Swap In", "Pages / s",
				labelField, timestampField, swapInField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^VM$", "Swap Out", "Pages / s",
				labelField, timestampField, swapOutField,
				new AverageCalculation(interval)));
		
		FieldSelector singleValueField = swapInField;
		rules.add(new Chart2DSeriesConfigRule("^SWAP_IN$", "Swap In", "Pages / s",
				labelField, timestampField, singleValueField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^SWAP_OUT$", "Swap Out", "Pages / s",
				labelField, timestampField, singleValueField,
				new AverageCalculation(interval)));
		return createConfig("Page Swap In / Out", "Time", "Pages / s", rules,
				AxisMode.TIME);
	}

}
