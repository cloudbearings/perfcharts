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

public class CPULoadChartTemplate extends BaseChart2DTemplateWithInterval {
	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector cpuload1min = new IndexFieldSelector(2);
		FieldSelector cpuload5min = new IndexFieldSelector(3);
		FieldSelector cpuload15min = new IndexFieldSelector(4);
		FieldSelector coresField = new IndexFieldSelector(5);
		FieldSelector singleValueField = cpuload1min;
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "CPU load-1 min", "", labelField,
				timestampField, cpuload1min, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD_1MIN$", "CPU load-1 min", "", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "CPU load-5 min", "", labelField,
				timestampField, cpuload5min, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD_5MIN$", "CPU load-5 min", "", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "CPU load-15 min", "", labelField,
				timestampField, cpuload15min, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD_15MIN$", "CPU load-15 min", "", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "CPUs", "", labelField,
				timestampField, coresField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPUNUM$", "CPUs", "", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		return createConfig("CPU Load over Time", "Time", "CPU load", rules,
				AxisMode.TIME);
	}

}
