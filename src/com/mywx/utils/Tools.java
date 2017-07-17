package com.mywx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Tools {
	private static Logger logger = LoggerFactory.getLogger(Tools.class);

	//读取配置文件
	public static String readFile(String key) {
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("/").getPath();
		String path = classPath + "config.properties";

		String value = "";
		FileInputStream inputFile = null;

		try {
			inputFile = new FileInputStream(path);

			Properties properties = new Properties();
			properties.load(inputFile);
			value = properties.getProperty(key);
			properties.clear();
			inputFile.close();
		} catch (Exception e) {
			logger.error("读properties文件错误：" + e.getMessage());
			return value;
		}
		return value;
	}

	/**
	 * 写properties文件
	 *
	 * @param key
	 * @param content Date 2012-07-19
	 * @author 陈少伯
	 */
	public static void writerFile(String key, String content) {
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("/").getPath();
		String path = classPath + "config.properties";

		FileOutputStream outputFile = null;
		Properties properties = new Properties();

		try {
			outputFile = new FileOutputStream(path, true); // 此处不加true将不会追加
			properties.setProperty(key, content);
			properties.store(outputFile, "Update");
			properties.clear();
			outputFile.close();
		} catch (Exception e) {
			logger.error("写properties文件错误：" + e.getMessage());
		}
	}
}