package chartgeneration.generator;

import chartgeneration.chart.Chart;
import chartgeneration.model.DataTable;

/**
 * A generator reads data tables and produces charts according to preset
 * configurations.
 * 
 * @author Rayson Zhu
 */
public interface Generator {
	/**
	 * reads specified data table and produces a chart
	 * 
	 * @param dataTable
	 *            a data table
	 * @return a chart
	 * @throws Exception
	 */
	public Chart generate(DataTable dataTable) throws Exception;
}
