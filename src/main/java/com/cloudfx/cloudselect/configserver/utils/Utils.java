package com.cloudfx.cloudselect.configserver.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Utils {

	public static List<File> getAllFiles(String folderPath) {
		List<File> ret = new ArrayList<File>();
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	ret.add(file);
		    }
		}
		return ret;
	}
	
	public static Map<String, String> readProperty(String filePath) {
		File file = new File(filePath);
		return readProperty(file);
	}
	
	public static void writeProperty(String folder, String fileName, Map<String, String> properties) {
		File file = new File(folder + "/" + fileName);
		writeProperty(file, properties);
	}
	
	public static Map<String, String> readProperty(File file) {
		Map<String, String> ret = new HashMap<String, String>();
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(input);
			input.close();

			Enumeration keys = properties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = properties.getProperty(key);
				ret.put(key, value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
//	public static void writeProperty(File file, Map<String, String> properties) {
//		Properties prop = new Properties();
//		OutputStream output = null;
//		try {
//			output = new FileOutputStream(file);
//			for(Map.Entry<String, String> entry : properties.entrySet()) {
//				// set the properties value
//				prop.setProperty(entry.getKey(), new String(entry.getValue()));
//			}
//			// save properties
//			prop.store(output, null);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			if (output != null) {
//				try {
//					output.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	public static void writeProperty(File file, Map<String, String> properties) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				bw.write(entry.getKey() + "=" + entry.getValue());
				bw.newLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean deleteAllFiles(String folderPath) {
		boolean hasError = false;
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		for (File file : files) {
			try {
				file.delete();
			} catch (Exception ex) {
				hasError = true;
				ex.printStackTrace();
			}
		}
		return hasError;
	}
}
