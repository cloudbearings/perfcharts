package com.redhat.chartgeneration.report;

import java.util.List;

public class StatTable extends StatChart {

	private List<StatRow> rows;
	private List<String> columnLabels;

	public StatTable() {

	}

	public StatTable(String title, String subtitle, List<String> columnLabels,
			List<StatRow> rows) {
		super(title, subtitle);
		this.columnLabels = columnLabels;
		this.rows = rows;
	}

	public List<StatRow> getRows() {
		return rows;
	}

	public void setRows(List<StatRow> rows) {
		this.rows = rows;
	}

	@Override
	public String format() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getColumnLabels() {
		return columnLabels;
	}

	public void setColumnLabels(List<String> columnLabels) {
		this.columnLabels = columnLabels;
	}

}
