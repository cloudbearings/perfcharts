package com.redhat.chartgeneration.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.common.Utilities;
import com.redhat.chartgeneration.configtemplate.ChartConfigTemplate;

public class ReportConfigLoader {
	private final static String CONFIG_TEMPLATE_PACKAGE = ChartConfigTemplate.class
			.getPackage().getName() + ".";
	private FieldSelector labelField;

	public ReportConfig load(String fileName) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			NoSuchFieldException, XMLStreamException, IllegalArgumentException,
			InvocationTargetException {
		InputStream in = new FileInputStream(fileName);
		ReportConfig cfg = load(in);
		in.close();
		return cfg;
	}

	public ReportConfig load(InputStream in) throws XMLStreamException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			InvocationTargetException, IOException {
		ReportConfig cfg = new ReportConfig();
		List<ChartConfigTemplate> list = new ArrayList<ChartConfigTemplate>();
		cfg.setConfigTemplate(list);
		XMLStreamReader reader = XMLInputFactory.newInstance()
				.createXMLStreamReader(in);
		int level = 0;
		FieldSelector labelField = null;
		ChartConfigTemplate configTemplate = null;

		URLClassLoader customClassLoader = null;
		try {
			while (reader.hasNext()) {
				switch (reader.next()) {
				case XMLStreamReader.START_ELEMENT:
					switch (++level) {
					case 1:
						String classPathAttr = reader.getAttributeValue(null,
								"classPath");
						if (classPathAttr != null && !classPathAttr.isEmpty()) {
							String[] classPathStrings = classPathAttr
									.split(":");
							URL[] urls = new URL[classPathStrings.length];
							for (int i = 0; i < classPathStrings.length; i++) {
								if (!classPathStrings[i].startsWith("/"))
									classPathStrings[i] = AppData.getInstance()
											.getCgt_lib()
											+ File.separator
											+ classPathStrings[i];
								if (!classPathStrings[i].startsWith("file:"))
									classPathStrings[i] = "file:"
											+ classPathStrings[i];
								urls[i] = new URL(classPathStrings[i]);
								AppData.getInstance()
										.getLogger()
										.info("add config class location '"
												+ urls[i].toString() + "'");
							}
							customClassLoader = new URLClassLoader(urls,
									ReportConfigLoader.class.getClassLoader());
						}
						cfg.setVersion(reader
								.getAttributeValue(null, "version"));
						cfg.setTitle(reader.getAttributeValue(null, "title"));
						cfg.setAuthor(reader.getAttributeValue(null, "author"));
						cfg.setInputFile(reader.getAttributeValue(null,
								"inputFile"));
						cfg.setOutputFile(reader.getAttributeValue(null,
								"outputFile"));
						String labelFieldAttr = reader.getAttributeValue(null,
								"labelField");
						cfg.setLabelField(labelField = new IndexFieldSelector(
								labelFieldAttr == null
										|| labelFieldAttr.isEmpty() ? 0
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

							/*
							 * Class<? extends ChartConfigTemplate> clazz =
							 * Class .forName(className).asSubclass(
							 * ChartConfigTemplate.class);
							 */
							ClassLoader loader = customClassLoader != null ? customClassLoader
									: ReportConfigLoader.class.getClassLoader();
							Class<? extends ChartConfigTemplate> clazz = loader
									.loadClass(className).asSubclass(
											ChartConfigTemplate.class);
							configTemplate = clazz.newInstance();
							configTemplate.setLabelField(labelField);
							list.add(configTemplate);
						}
						break;
					case 3:
						String fieldName = reader.getLocalName();
						String valueType = reader.getAttributeValue(null,
								"type");
						Object value = valueType == null ? Utilities
								.parseString(reader.getElementText())
								: Utilities.parseString(
										reader.getElementText(), valueType);
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
		} finally {
			if (customClassLoader != null)
				customClassLoader.close();
		}

		return cfg;
	}

	private static void setField(Object obj, String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String methodName = "set" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
		// need to be optimized when free
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
