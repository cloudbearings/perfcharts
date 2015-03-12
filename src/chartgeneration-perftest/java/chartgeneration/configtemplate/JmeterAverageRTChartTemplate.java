package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.AddTransformSelector;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.config.Chart2DSeriesExclusionRule;
import chartgeneration.configtemplate.BaseChart2DTemplateWithInterval;

public class JmeterAverageRTChartTemplate extends
		BaseChart2DTemplateWithInterval {

	private String exclusionPattern;

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(5);
		FieldSelector xField = new AddTransformSelector(timestampField, rtField);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		Chart2DSeriesConfigRule rule = new Chart2DSeriesConfigRule(
				"^TX-(.+)-S$", "Average Response Time", "ms", getLabelField(),
				xField, rtField, new AverageCalculation(getInterval()));
		if (exclusionPattern != null && !exclusionPattern.isEmpty())
			rule.setExclusionRule(new Chart2DSeriesExclusionRule("$1",
					exclusionPattern));
		rules.add(rule);
		return createConfig("Average Response Time over Time", "Time",
				"Response Time", rules, AxisMode.TIME);
	}

	public String getExclusionPattern() {
		return exclusionPattern;
	}

	public void setExclusionPattern(String exclusionPattern) {
		this.exclusionPattern = exclusionPattern;
	}
}
