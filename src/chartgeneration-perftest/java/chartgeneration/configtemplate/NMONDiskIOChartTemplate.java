package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.calc.SumByLabelCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.configtemplate.BaseChart2DTemplateWithInterval;

public class NMONDiskIOChartTemplate extends BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector diskIOField = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^DISKREAD-(.+)$", "Read-$1", "KiB/s",
				labelField, timestampField, diskIOField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^DISKREAD-(.+)$", "Total Read", "KiB/s",
				labelField, timestampField, diskIOField, new SumByLabelCalculation(
						labelField, interval)));
		rules.add(new Chart2DSeriesConfigRule("^DISKWRITE-(.+)$", "Write-$1", "KiB/s",
				labelField, timestampField, diskIOField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^DISKWRITE-(.+)$", "Total Write", "KiB/s",
				labelField, timestampField, diskIOField, new SumByLabelCalculation(
						labelField, interval)));
		return createConfig("Disk IO over Time / (KiB/s)", "time", "KiB/s", rules, AxisMode.TIME);
	}

}
