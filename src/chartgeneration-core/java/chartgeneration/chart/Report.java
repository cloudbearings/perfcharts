package chartgeneration.chart;

import java.util.List;

public class Report {
	/**
	 * the report title
	 */
	private String title;
	/**
	 * charts that the report contains
	 */
	private List<Chart> charts;

	/**
	 * Initialize an empty report.
	 */
	public Report() {

	}

	/**
	 * Initialize a report with specified parameters.
	 * 
	 * @param title
	 *            the report title
	 * @param charts
	 *            charts that the report contains
	 */
	public Report(String title, List<Chart> charts) {
		this.title = title;
		this.charts = charts;
	}

	/**
	 * Get charts that the report contains.
	 * 
	 * @return charts
	 */
	public List<Chart> getCharts() {
		return charts;
	}

	/**
	 * Set charts that the report contains.
	 * 
	 * @param charts
	 *            charts
	 */
	public void setCharts(List<Chart> charts) {
		this.charts = charts;
	}

	/**
	 * Get the report title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the report title.
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
