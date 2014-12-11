package com.redhat.chartgeneration.common;

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
		if (Utilities.isLong(s)){
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
			value =Integer.parseInt(s);
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
}
