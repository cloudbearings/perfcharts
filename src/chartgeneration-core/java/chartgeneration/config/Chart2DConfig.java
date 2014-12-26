package chartgeneration.config;

import java.util.List;

import chartgeneration.chart.Chart2D;
import chartgeneration.generator.Chart2DFactory;
import chartgeneration.generator.Chart2DFactoryImpl;

/**
 * The configuration for a general two dimensional chart
 * 
 * @author Rayson Zhu
 *
 */
public class Chart2DConfig extends BaseChartConfig<Chart2D> {
	/**
	 * the x-axis label
	 */
	private String xLabel;
	/**
	 * the y-axis label
	 */
	private String yLabel;
	/**
	 * rules for generating elements of the chart
	 */
	private List<Chart2DSeriesConfigRule> rules;
	/**
	 * the x-axis mode
	 */
	private AxisMode xaxisMode = AxisMode.NUMBER;

	/**
	 * the interval value. 0 means do not merge.
	 */
	private int interval = 0;

	public Chart2DConfig() {

	}

	/**
	 * 
	 * @param title
	 *            title of the chart
	 * @param subtitle
	 *            subtitle of the chart
	 * @param xLabel
	 *            x-axis label of the chart
	 * @param yLabel
	 *            y-axis label of the chart
	 * @param rules
	 *            for generating elements of the chart
	 */
	public Chart2DConfig(String title, String subtitle, String xLabel,
			String yLabel, List<Chart2DSeriesConfigRule> rules, int interval) {
		super(title, subtitle);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
		this.interval = interval;
	}

	/**
	 * 
	 * @param title
	 *            title of the chart
	 * @param subtitle
	 *            subtitle of the chart
	 * @param xLabel
	 *            x-axis label of the chart
	 * @param yLabel
	 *            y-axis label of the chart
	 * @param rules
	 *            for generating elements of the chart
	 * @param xaxisMode
	 *            the x-axis mode
	 */
	public Chart2DConfig(String title, String subtitle, String xLabel,
			String yLabel, List<Chart2DSeriesConfigRule> rules,
			AxisMode xaxisMode, int interval) {
		super(title, subtitle);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.rules = rules;
		this.xaxisMode = xaxisMode;
		this.interval = interval;
	}

	/**
	 * get the label of x-axis
	 * 
	 * @return the label of x-axis
	 */
	public String getXLabel() {
		return xLabel;
	}

	/**
	 * set the label of x-axis
	 * 
	 * @param xLabel
	 *            the label of x-axis
	 */
	public void setXLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	/**
	 * get the label of y-axis
	 * 
	 * @return the label of y-axis
	 */
	public String getYLabel() {
		return yLabel;
	}

	/**
	 * set the label of y-axis
	 * 
	 * @param yLabel
	 *            the label of y-axis
	 */
	public void setYLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	/**
	 * get rules for generating elements of the chart
	 * 
	 * @return rules
	 */
	public List<Chart2DSeriesConfigRule> getRules() {
		return rules;
	}

	/**
	 * set rules for generating elements of the chart
	 * 
	 * @param rules
	 *            rules
	 */
	public void setRules(List<Chart2DSeriesConfigRule> rules) {
		this.rules = rules;
	}

	/**
	 * get x-axis mode
	 * 
	 * @return x-axis mode
	 */
	public AxisMode getXaxisMode() {
		return xaxisMode;
	}

	/**
	 * set x-axis mode
	 * 
	 * @param xaxisMode
	 *            x-axis mode
	 */
	public void setXaxisMode(AxisMode xaxisMode) {
		this.xaxisMode = xaxisMode;
	}

	/**
	 * Get the interval value.
	 * 
	 * @return the interval value. 0 means do not merge.
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * Set the interval value.
	 * 
	 * @param interval
	 *            the interval value. 0 means do not merge.
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	@Override
	public Chart2DFactory createChartFactory() throws Exception {
		return new Chart2DFactoryImpl();
	}

}
