package com.redhat.chartgeneration.common;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Pattern;

public class Utilities {
	private static final Pattern longPattern = Pattern.compile("^\\d+$");
	private static final Pattern doublePattern = Pattern
			.compile("^\\d*\\.\\d+$");

	public static boolean isDouble(String s) {
		return doublePattern.matcher(s).matches();
	}

	public static boolean isLong(String s) {
		return longPattern.matcher(s).matches();
	}

	public static Object parseString(String s) {
		if (Utilities.isDouble(s))
			return Double.parseDouble(s);
		if (Utilities.isLong(s)) {
			if (s.length() > 9)
				return Long.parseLong(s);
			return Integer.parseInt(s);
		}
		return s;
	}

	public static Object parseString(String s, Class<?> clazz) {
		Object value;
		if (clazz == Integer.class || clazz == int.class)
			value = Integer.parseInt(s);
		else if (clazz == Double.class || clazz == double.class)
			value = Double.parseDouble(s);
		else if (clazz == Long.class || clazz == long.class)
			value = Long.parseLong(s);
		else if (clazz == Float.class || clazz == float.class)
			value = Float.parseFloat(s);
		else
			value = s;
		return value;
	}

	public static Object parseString(String s, String type) {
		Object value;
		if (type.equals("int"))
			value = Integer.parseInt(s);
		else if (type.equals("double"))
			value = Double.parseDouble(s);
		else if (type.equals("long"))
			value = Long.parseLong(s);
		else if (type.equals("float"))
			value = Float.parseFloat(s);
		else
			value = s;
		return value;
	}

	public static String commonConvertToJsonValue(Object o) {
		if (o == null)
			return "null";
		if (o instanceof Integer || o instanceof Long)
			return o.toString();
		if (o instanceof Double || o instanceof Float)
			return String.format("%.2f", o);
		return "\"" + o.toString().replace("\"", "\\\"") + "\"";
	}

	public static Number fastSelect(Collection<Long> collection, int targetRank) {
		if (collection.isEmpty())
			return null;
		// Comparable<Object> obj = null;
		ArrayList<Long> copy = new ArrayList<Long>(collection);
		int start = 0, end = copy.size();
		while (start < end) {
			int pivot = partition(copy, start, end);
			//System.err.println("call partition:" + start + "," + end + "," + pivot + "," + targetRank);
			if (pivot < targetRank) {
				start = pivot + 1;
			} else if (pivot > targetRank) {
				end = pivot;
			} else {
				return copy.get(pivot);
			}
		}
		return null;
	}

	private static Random random = new Random();

	private static <T> int partition(ArrayList<Long> arr, int start, int end) {
		if (end <= start || start < 0 || end > arr.size() || arr.isEmpty())
			throw new InvalidParameterException("The precondition does not meet: 0<=start<end<=arr.size()");
		int left = start, right = end - 1;
		int rnd = start + random.nextInt(end - start);
		Long pivotValue = arr.get(rnd);
		arr.set(rnd, arr.get(left));
		while (left < right) {
			while (left < right && arr.get(right) >= pivotValue)
				--right;
			arr.set(left, arr.get(right));
			while (left < right && arr.get(left) <= pivotValue)
				++left;
			arr.set(right, arr.get(left));
		}
		arr.set(left, pivotValue);
		//System.err.println("arr," + start + "," + end + "," + left);
		return left;
	}

}
