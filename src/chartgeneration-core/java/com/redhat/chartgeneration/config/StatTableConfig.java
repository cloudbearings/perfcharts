package com.redhat.chartgeneration.config;

import java.util.List;

import com.redhat.chartgeneration.generator.Generator;
import com.redhat.chartgeneration.generator.StatTableGenerator;

public class StatTableConfig extends StatChartConfig {
	private List<TableRowConfigRule> rowRules;
	private List<TableColumnConfigRule> columnRules;

	public StatTableConfig() {
	}

	public StatTableConfig(String title, String subtitle,
			List<TableRowConfigRule> rowRules,
			List<TableColumnConfigRule> columnRules) {
		super(title, subtitle);
		this.rowRules = rowRules;
		this.columnRules = columnRules;
	}

	@Override
	public Generator createGenerator() throws Exception {
		return new StatTableGenerator(this);
	}

	public List<TableRowConfigRule> getRowRules() {
		return rowRules;
	}

	public void setRowRules(List<TableRowConfigRule> rowRules) {
		this.rowRules = rowRules;
	}

	public List<TableColumnConfigRule> getColumnRules() {
		return columnRules;
	}

	public void setColumnRules(List<TableColumnConfigRule> columnRules) {
		this.columnRules = columnRules;
	}

}
