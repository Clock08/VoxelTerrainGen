package federation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	public static final int LOG = 0;
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int ERROR = 3;
	public static final int FATAL = 4;
	public static final int SEVERE = 5;
	
	public static void log(int level, String s) {
		String type = "[";
		
		switch (level) {
		case LOG:
			type += "LOG";
			break;
		case INFO:
			type += "INFO";
			break;
		case WARN:
			type += "WARN";
			break;
		case ERROR:
			type += "ERROR";
			break;
		case FATAL:
			type += "FATAL";
			break;
		case SEVERE:
			type += "SEVERE";
			break;
		}
		type += "]";
		
		System.out.println(timestamp() + " " + type + " " + s);
	}
	
	private static String timestamp() {
		return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]";
	}
}
