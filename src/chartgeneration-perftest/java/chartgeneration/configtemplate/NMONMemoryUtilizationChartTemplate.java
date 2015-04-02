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

public class NMONMemoryUtilizationChartTemplate extends
		Chart2DTemplateWithIntervalBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector totalMemField = new IndexFieldSelector(2);
		FieldSelector freeMemField = new IndexFieldSelector(3);
		FieldSelector cachedMemField = new IndexFieldSelector(4);
		FieldSelector buffersMemField = new IndexFieldSelector(5);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Memory-Total", "MiB", labelField,
				timestampField, totalMemField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Memory-Free", "MiB", labelField,
				timestampField, freeMemField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Memory-Cached", "MiB", labelField,
				timestampField, cachedMemField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Memory-Buffers", "MiB", labelField,
				timestampField, buffersMemField, new AverageCalculation(
						interval)));
		

		FieldSelector singleValueField = totalMemField;
		rules.add(new Chart2DSeriesConfigRule("^MEM_TOTAL$", "Memory-Total", "MiB", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM_FREE$", "Memory-Free", "MiB", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM_CACHED$", "Memory-Cached", "MiB", labelField,
				timestampField, singleValueField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM_BUFFERS$", "Memory-Buffers", "MiB", labelField,
				timestampField, singleValueField, new AverageCalculation(
						interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM_AVAILABLE$", "Memory-Available", "MiB", labelField,
				timestampField, singleValueField, new AverageCalculation(
						interval)));
		
		return createConfig("Memory Utilization over Time", "Time",
				"Memory Utilization", rules, AxisMode.TIME);
	}

}
