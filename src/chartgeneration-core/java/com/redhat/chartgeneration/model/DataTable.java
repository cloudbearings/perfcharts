package com.redhat.chartgeneration.model;

import java.util.List;

public class PerfLog {
	// private List<Class<?>> fieldTypes;
	private List<List<Object>> rows;

	public PerfLog() {
		// this.fieldTypes = new ArrayList<Class<?>>(7);
		// this.fieldTypes.add(String.class);
		// this.fieldTypes.add(Long.class);
		// this.fieldTypes.add(Integer.class);
		// this.fieldTypes.add(Integer.class);
		// this.fieldTypes.add(Integer.class);
		// this.fieldTypes.add(Integer.class);
		// this.fieldTypes.add(Integer.class);
		// this.fieldTypes.add(String.class);
		// this.fieldTypes.add(Long.class);
		// this.fieldTypes.add(Float.class);
		// this.fieldTypes.add(Float.class);
		// this.fieldTypes.add(Float.class);
		// this.fieldTypes.add(Float.class);
		// this.fieldTypes.add(Integer.class);
	}

	// public List<Class<?>> getFieldTypes() {
	// return fieldTypes;
	// }
	//
	// public void setFieldTypes(List<Class<?>> fieldTypes) {
	// this.fieldTypes = fieldTypes;
	// }

	public List<List<Object>> getRows() {
		return rows;
	}

	public void setRows(List<List<Object>> rows) {
		this.rows = rows;
	}

}
