package com.redhat.chartgeneration.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphLine {
	private String label;
	private List<LineStop> stops;
	private boolean showLines;
	private boolean showBars;
	private String unit;
	private boolean showUnit;

	public GraphLine() {
		stops = new ArrayList<LineStop>();
	}

	public GraphLine(String l, String unit, List<LineStop> s) {
		this(l, unit, s, true, false, false);
	}

	public GraphLine(String l, String unit, List<LineStop> s,
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

	public List<LineStop> getStops() {
		return stops;
	}

	public void setStops(List<LineStop> stops) {
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
