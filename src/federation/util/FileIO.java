package federation.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO {
	
	public static FileOutputStream write(String path) {
		FileOutputStream fileStream = null;
		
		try {
			fileStream = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			Log.log(Log.SEVERE, "No file/directory found at " + path);
			e.printStackTrace();
		}
		
		return fileStream;
	}
	
	public static FileInputStream read(String path) {
		FileInputStream fileStream = null;
		
		try {
			fileStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			Log.log(Log.SEVERE, "No file/directory found at " + path);
			e.printStackTrace();
		}
		
		return fileStream;
	}
	
	public static String getText(String path) {
		String text = "";
		
		FileInputStream fs = read(path);
		if (fs == null) return null;
		
		try {
			while (fs.available() > 0) {
				text += (char) fs.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
	}
}
