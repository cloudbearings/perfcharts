package chartgeneration.formatter;

import org.json.JSONArray;
import org.json.JSONObject;

import chartgeneration.chart.GenericTable;
import chartgeneration.chart.TableCell;

public class GenericTableFormatterImpl implements GenericTableFormatter {

	@Override
	public String format(GenericTable chart) throws Exception {
		JSONObject chartJSON = new JSONObject();
		chartJSON.put("chartType", "TABLE");
		chartJSON.put("title", chart.getTitle());
		chartJSON.put("subtitle", chart.getSubtitle());
		chartJSON.put("key", chart.getKey());
		if (chart.getColumnKeys() != null)
			chartJSON.put("columnKeys", new JSONObject(chart.getColumnKeys()));
		if (chart.getHeader() != null)
			chartJSON.put("header", new JSONArray(chart.getHeader()));
		if (chart.getFooter() != null)
			chartJSON.put("footer", new JSONArray(chart.getFooter()));
		JSONArray rowsArray = new JSONArray();
		for (TableCell[] row : chart.getRows())
			rowsArray.put(parseTableRow(row));
		chartJSON.put("rows", rowsArray);
		// chartJSON.put("rowSize", chart.getRows().size());
		return chartJSON.toString();
	}

	private static JSONArray parseTableRow(TableCell[] tr) {
		JSONArray r = new JSONArray();
		for (TableCell tc : tr) {
			r.put(parseTableCell(tc));
		}
		return r;

	}

	private static JSONObject parseTableCell(TableCell tc) {
		JSONObject r = new JSONObject();
		Object value = tc.getValue();
		String fallbackValueType = null;
		if (value instanceof Double) {
			fallbackValueType = "double";
			if (Double.isNaN((double) value)) {
				tc.setValue(JSONObject.NULL);
				tc.setRawValue("NaN");
			} else if (Double.POSITIVE_INFINITY == (double) value) {
				tc.setValue(JSONObject.NULL);
				tc.setRawValue("+∞");
			} else if (Double.NEGATIVE_INFINITY == (double) value) {
				tc.setValue(JSONObject.NULL);
				tc.setRawValue("-∞");
			}
		} else if (value instanceof Float) {
			fallbackValueType = "float";
			if (Float.isNaN((float) value)) {
				tc.setValue(JSONObject.NULL);
				tc.setRawValue("NaN");
			} else if (Float.POSITIVE_INFINITY == (float) value) {
				tc.setValue(JSONObject.NULL);
				tc.setRawValue("+∞");
			} else if (Float.NEGATIVE_INFINITY == (float) value) {
				tc.setValue(JSONObject.NULL);
				tc.setRawValue("-∞");
			}
		}/*
		 * else if (value instanceof Integer) { fallbackValueType = "int"; }
		 * else if (value instanceof Long) { fallbackValueType = "long"; } else
		 * if (value instanceof String) { fallbackValueType = "string"; }
		 */
		r.put("value", tc.getValue());
		r.put("valueType", tc.getValueType() != null ? tc.getValueType()
				: fallbackValueType);
		r.put("rawValue", tc.getRawValue());
		r.put("cssClass", tc.getCssClass());
		r.put("tag", tc.getTag());
		return r;
	}
}
