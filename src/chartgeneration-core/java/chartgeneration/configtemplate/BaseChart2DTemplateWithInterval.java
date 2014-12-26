package chartgeneration.configtemplate;

import java.util.List;

import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;

/**
 * Represents a {@link BaseChart2DTemplateWithInterval} with interval merge
 * support
 * 
 * @author Rayson Zhu
 *
 */
public abstract class BaseChart2DTemplateWithInterval extends
		BaseChart2DTemplate {
	/**
	 * the interval value. 0 means do not merge.
	 */
	private int interval = 0;

	/**
 * 
 */
	public BaseChart2DTemplateWithInterval() {

	}

	/**
	 * 
	 * @param interval
	 *            the interval value. 0 means do not merge.
	 */
	public BaseChart2DTemplateWithInterval(int interval) {
		this.interval = interval;
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
	protected Chart2DConfig createConfig(String defaultTitle,
			String defaultXLabel, String defaultYLabel,
			List<Chart2DSeriesConfigRule> rules, AxisMode xaxisMode) {
		Chart2DConfig config = super.createConfig(defaultTitle, defaultXLabel, defaultYLabel, rules,
				xaxisMode);
		config.setInterval(interval);
		return config;
	}
}
