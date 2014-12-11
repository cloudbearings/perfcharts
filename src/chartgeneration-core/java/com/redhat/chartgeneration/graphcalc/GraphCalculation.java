package com.redhat.chartgeneration.graphcalc;

import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.graph.LineStop;

public interface GraphCalculation {
	public List<LineStop> produce(List<List<Object>> rows, FieldSelector xField, FieldSelector yField);
}
