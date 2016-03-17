package com.bbva.paas.gdd.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileUtils {

	static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	private FileUtils() {
		
	}
	
	public static String readFile(String fileName) throws IOException {
		logger.info("readFile: Reading file " + fileName);
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
	        }
			return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
