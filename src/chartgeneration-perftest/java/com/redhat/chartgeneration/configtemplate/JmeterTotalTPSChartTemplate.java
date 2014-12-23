package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.CountCalculation;
import com.redhat.chartgeneration.common.AddTransformSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

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
