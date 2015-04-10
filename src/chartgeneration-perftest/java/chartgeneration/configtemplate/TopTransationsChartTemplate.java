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

public class TopTransationsChartTemplate extends Chart2DTemplateBase {
	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector<Double> rtField = new IndexFieldSelector<Double>(5);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-S$", "$1", "ms",
				getLabelField(), new ConstantSelector(1), rtField,
				new AverageCalculation(), false, true, false));
		Chart2DConfig cfg = createConfig("Transactions Top 10", "", "Response Time",
				rules, AxisMode.CATEGORIES);
		//cfg.setSeriesOrder(SeriesOrder.FIRST_POINT_Y_DESC);
		return cfg;
	}

}
