package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.ConstantSelector;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.configtemplate.BaseChart2DTemplate;

public class JmeterAverageRTOverallChartTemplate extends BaseChart2DTemplate {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector rtField = new IndexFieldSelector(5);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-S$", "$1", "RT",
				getLabelField(), new ConstantSelector(1), rtField,
				new AverageCalculation(), false, true, false));
		return createConfig("Transactions Average Response Times Overall", "", "response time / ms",
				rules, AxisMode.CATEGORIES);
	}

}
