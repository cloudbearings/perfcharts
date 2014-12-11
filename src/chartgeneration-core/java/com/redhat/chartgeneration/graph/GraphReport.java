package com.redhat.chartgeneration.graph;

import java.util.List;

public class GraphReport {
	private String title;
	private List<LineGraph> graphs;

	// private FlotChartFormatter formatter;
	public GraphReport() {

	}

	public GraphReport(String title, List<LineGraph> graphs) {
		this.title = title;
		this.graphs = graphs;
	}

	public List<LineGraph> getGraphs() {
		return graphs;
	}

	public void setGraphs(List<LineGraph> graphs) {
		this.graphs = graphs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
