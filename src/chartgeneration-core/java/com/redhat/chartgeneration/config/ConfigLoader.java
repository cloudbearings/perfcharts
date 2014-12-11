package com.redhat.chartgeneration.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.common.Utilities;
import com.redhat.chartgeneration.configtemplate.ChartConfigTemplate;

public class ConfigLoader {
	private final static String CONFIG_TEMPLATE_PACKAGE = "com.redhat.chartgeneration.configtemplate.";
	private FieldSelector labelField;

	public AppConfig load(String fileName) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			NoSuchFieldException, XMLStreamException, IllegalArgumentException,
			InvocationTargetException {
		InputStream in = new FileInputStream(fileName);
		AppConfig cfg = load(in);
		in.close();
		return cfg;
	}

	public AppConfig load(InputStream in) throws XMLStreamException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			InvocationTargetException {
		AppConfig cfg = new AppConfig();
		List<ChartConfigTemplate> list = new ArrayList<ChartConfigTemplate>();
		cfg.setConfigTemplate(list);
		XMLStreamReader reader = XMLInputFactory.newInstance()
				.createXMLStreamReader(in);
		int level = 0;
		FieldSelector labelField = null;
		ChartConfigTemplate configTemplate = null;
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamReader.START_ELEMENT:
				switch (++level) {
				case 1:
					cfg.setVersion(reader.getAttributeValue(null, "version"));
					cfg.setTitle(reader.getAttributeValue(null, "title"));
					cfg.setAuthor(reader.getAttributeValue(null, "author"));
					cfg.setInputFile(reader
							.getAttributeValue(null, "inputFile"));
					cfg.setOutputFile(reader.getAttributeValue(null,
							"outputFile"));
					String labelFieldAttr = reader.getAttributeValue(null,
							"labelField");
					cfg.setLabelField(labelField = new IndexFieldSelector(
							labelFieldAttr == null || labelFieldAttr.isEmpty() ? 0
									: Integer.parseInt(labelFieldAttr)));
					break;
				case 2:
					if (reader.getLocalName().equals("chartConfigTemplate")) {
						String className = reader.getAttributeValue(null,
								"class");
						if (!className.startsWith("/"))
							className = CONFIG_TEMPLATE_PACKAGE + className;
						else
							className = className.substring(1);
						Class<? extends ChartConfigTemplate> clazz = Class
								.forName(className).asSubclass(
										ChartConfigTemplate.class);
						configTemplate = clazz.newInstance();
						configTemplate.setLabelField(labelField);
						list.add(configTemplate);
					}
					break;
				case 3:
					String fieldName = reader.getLocalName();
					String valueType = reader.getAttributeValue(null, "type");
					Object value = valueType == null ? Utilities
							.parseString(reader.getElementText()) : Utilities
							.parseString(reader.getElementText(), valueType);
					--level;
					setField(configTemplate, fieldName, value);
					break;
				}
				break;
			case XMLStreamReader.END_ELEMENT:
				--level;
				break;
			}
		}
		return cfg;
	}

	private static void setField(Object obj, String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String methodName = "set" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
		for (Method m : obj.getClass().getMethods()) {
			if (m.getName().equals(methodName)) {
				m.invoke(obj, value);
				break;
			}
		}
	}

	public FieldSelector getLabelField() {
		return labelField;
	}

	public void setLabelField(FieldSelector labelField) {
		this.labelField = labelField;
	}
}
