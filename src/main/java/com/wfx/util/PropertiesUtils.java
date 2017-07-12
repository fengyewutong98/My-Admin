package com.wfx.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * 此方法的作用是获取系统的JVM(虚拟机内存),CPU,内存的使用情况
 * 
 * 下面的方法读取属性文件会有缓存问题，在修改属性文件时不起作用。 
 * InputStream in =  PropertiesUtils.class.getResourceAsStream("/config.properties"); 
 * 解决方法： 
 * String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
 * 
 * @author FengXiang
 *
 */
public class PropertiesUtils {
	/**
	 * 获取属性文件的数据 根据key获取值
	 * @param fileName 文件名 (注意：加载的是src下的文件,如果在某个包下．请把包名加上)
	 * @param key
	 * @return
	 */
	public static String findPropertiesKey(String key) {

		try {
			Properties prop = getProperties();
			return prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 测试获取文件的信息
	 * @param args
	 */
	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream in = PropertiesUtils.class.getResourceAsStream("/conf/config.properties");
		try {
			prop.load(in);
			Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
				System.err.println((e.getKey().toString() + "" + e.getValue().toString()));
			}
			in.close();
		} catch (Exception e) {

		}
	}

	/**
	 * 返回 Properties 获取文件的信息
	 * 
	 * @param fileName 文件名 (注意：加载的是src下的文件,如果在某个包下．请把包名加上)
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		Properties prop = new Properties();
		try {
			Reader reader = Resources.getResourceAsReader("/conf/config.properties");
			prop.load(reader);
			reader.close();
		} catch (Exception e) {
			return null;
		}
		return prop;
	}

	public static Properties getjdbcProperties() {
		Properties prop = new Properties();
		try {
			Reader reader = Resources.getResourceAsReader("/conf/jdbc.properties");
			prop.load(reader);
			reader.close();
		} catch (Exception e) {
			return null;
		}
		return prop;
	}

	/**
	 * 写入properties信息
	 * 
	 * @param key 名称
	 * @param value 值
	 */
	public static void modifyProperties(String key, String value) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties();
			prop.setProperty(key, value);
			String path = PropertiesUtils.class.getResource("/conf/config.properties").getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
