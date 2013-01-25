package com.test.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @Title: UnionpayUtil.java
 * @Package com.unionpay.upomp.api.util
 * @Description: TODO 文件操作
 * @author chentong
 * @date 2012-4-19
 * @version V1.0
 */

public class FileUtil {
	/**
	 * 写入配置文件
	 * 
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 */
	private final static Logger logger = Logger.getLogger(FileUtil.class);
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		InputStream fis =  null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			logger.error("写配置文件出错，IO异常",e);
		} catch (Exception e) {
			logger.error("写配置文件出错，异常",e);
		}finally {
			try {
				if(fis != null){fis.close();}
				if(fos != null){fos.close();}
			} catch (Exception e) {
				logger.error("写配置文件時，釋放資源IO異常",e);
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	@SuppressWarnings("unused")
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		FileInputStream fs = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		StringBuffer tempString = new StringBuffer();
		try {
			fs = new FileInputStream(file);
			reader = new InputStreamReader(fs, "utf-8");
			bufferedReader = new BufferedReader(reader);
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			String str = "";
			while ((str = bufferedReader.readLine()) != null) {
				// 显示行号
				// str = new String(str.getBytes("utf-8"), "utf-8");
				tempString.append(str);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			logger.error("读文件操作出错，IO异常",e);
		} catch (Exception e) {
			logger.error("读文件操作出错，异常",e);
		} finally {
			try {
				if(bufferedReader != null){bufferedReader.close();}
			} catch (Exception e) {
				logger.error("读文件时，釋放資源IO異常",e);
			}
			try {
				if (reader != null) {reader.close();}
			} catch (Exception e) {
				logger.error("读文件时，釋放資源IO異常",e);
			}
			try {
				if(fs != null){fs.close();}
			} catch (Exception e) {
				logger.error("读文件时，釋放資源IO異常",e);
			}
		}
		return tempString.toString();
	}

	public static void main(String[] args) {
		// String path =
		// "D:\\Program Files\\apache-tomcat-7.0.23\\webapps\\orderpush\\WEB-INF\\etc\\config\\platformConfig.xml";
		// String xml = FileUtil.readFileByLines(path);
		// String path1 =
		// "D:\\Program Files\\apache-tomcat-7.0.23\\webapps\\orderpush\\WEB-INF\\etc\\config\\unionpayConfig.xml";
		// PlatformConfigDecoder decoder = new PlatformConfigDecoder();//
		// 将配置文件转为实体类
		// PlatformConfig config = decoder.getEntity(xml);
		// System.out.println(config);
	}

	/**
	 * 执行写文件的操作
	 * 
	 * @param fileName
	 * @param message
	 */
	public static void writeFileData(String fileName, String content) {
		
		File f = new File(fileName);
		FileOutputStream fos = null;
		OutputStreamWriter write = null;
		BufferedWriter writer = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			fos = new FileOutputStream(f);
			write = new OutputStreamWriter(fos, "UTF-8");
			writer = new BufferedWriter(write);
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			logger.error("写文件内容操作出错，IO异常",e);
		} catch (Exception e) {
			logger.error("写文件内容操作出错，异常",e);
		} finally {
			try {
				if(writer != null){writer.close();}
				if(write != null){write.close();}
				if(fos != null){fos.close();}
			} catch (Exception e) {
				logger.error("写文件时，釋放資源IO異常",e);
			}
		}

	}

	/**
	 * 合并XML文件的操作
	 * 
	 * @param fileName
	 * @param message
	 */
	public static void appendFile(String fileName, String content) {
		
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.flush();
			//writer.close();
		} catch (IOException e) {
			logger.error("合并XML文件时，IO异常",e);
		} catch (Exception e) {
			logger.error("合并XML文件时，异常",e);
		}finally {
			try {
				if(writer != null){writer.close();}
			} catch (Exception e) {
				logger.error("合并XML文件时，釋放資源IO異常",e);
			}
		}
	}
}
