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

public class NMONNetworkThroughputChartTemplate extends
		BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector labelField = getLabelField();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector netReadField = new IndexFieldSelector(2);
		FieldSelector netWriteField = new IndexFieldSelector(3);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^NET$", "Network-In", "KiB/s", labelField,
				timestampField, netReadField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^NET$", "Network-Out", "KiB/s", labelField,
				timestampField, netWriteField, new AverageCalculation(interval)));
		
		FieldSelector singleValueField = netReadField;
		rules.add(new Chart2DSeriesConfigRule("^NET_IF_IN-(.+)$", "Network-In-$1", "KiB/s", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^NET_IF_IN-(.+)$", "Network-Out-$1", "KiB/s", labelField,
				timestampField, singleValueField, new AverageCalculation(interval)));
		
		return createConfig("Network Throughput over Time", "Time", "Network Throughput",
				rules, AxisMode.TIME);
	}

}
