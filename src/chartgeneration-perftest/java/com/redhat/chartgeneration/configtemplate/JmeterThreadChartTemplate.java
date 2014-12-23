package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class JmeterThreadChartTemplate extends BaseChart2DTemplateWithInterval {
	@Override
	public Chart2DConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector threadsField = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-[SF]$", "users (threads)", "VU",
				getLabelField(), timestampField, threadsField, new AverageCalculation(
						getInterval())));
		return createConfig("Concurrent Virtual Users",
				"time", "virtual users (threads)", rules, AxisMode.TIME);

	}
}
