package com.redhat.chartgeneration.report;

import java.util.ArrayList;
import java.util.List;

public class GraphSeries {
	private String label;
	private List<GraphPoint> stops;
	private boolean showLines;
	private boolean showBars;
	private String unit;
	private boolean showUnit;

	public GraphSeries() {
		stops = new ArrayList<GraphPoint>();
	}

	public GraphSeries(String l, String unit, List<GraphPoint> s) {
		this(l, unit, s, true, false, false);
	}

	public GraphSeries(String l, String unit, List<GraphPoint> s,
			boolean showLines, boolean showBars, boolean showUnit) {
		label = l;
		stops = s;
		this.unit = unit;
		this.showBars = showBars;
		this.showLines = showLines;
		this.showUnit = showUnit;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<GraphPoint> getStops() {
		return stops;
	}

	public void setStops(List<GraphPoint> stops) {
		this.stops = stops;
	}

	public boolean isShowLines() {
		return showLines;
	}

	public void setShowLines(boolean showLines) {
		this.showLines = showLines;
	}

	public boolean isShowBars() {
		return showBars;
	}

	public void setShowBars(boolean showBars) {
		this.showBars = showBars;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isShowUnit() {
		return showUnit;
	}

	public void setShowUnit(boolean showUnit) {
		this.showUnit = showUnit;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ":" + label + ", points "
				+ stops.size();
	}

}
