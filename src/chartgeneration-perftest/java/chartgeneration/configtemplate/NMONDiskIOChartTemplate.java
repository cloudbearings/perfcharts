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
import chartgeneration.configtemplate.Chart2DTemplateWithIntervalBase;

public class NMONDiskIOChartTemplate extends Chart2DTemplateWithIntervalBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector diskIOField = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^DISKREAD-(.+)$", "Disk-$1-Read", "KiB/s",
				labelField, timestampField, diskIOField,
				new AverageCalculation(interval)));
//		rules.add(new Chart2DSeriesConfigRule("^DISKREAD-(.+)$", "Disk-Total-Read", "KiB/s",
//				labelField, timestampField, diskIOField, new SumByLabelCalculation(
//						labelField, interval)));
		rules.add(new Chart2DSeriesConfigRule("^DISKWRITE-(.+)$", "Disk-$1-Write", "KiB/s",
				labelField, timestampField, diskIOField,
				new AverageCalculation(interval)));
//		rules.add(new Chart2DSeriesConfigRule("^DISKWRITE-(.+)$", "Disk-Total-Write", "KiB/s",
//				labelField, timestampField, diskIOField, new SumByLabelCalculation(
//						labelField, interval)));
		return createConfig("Disk IO", "Time", "Disk IO", rules, AxisMode.TIME);
	}

}
