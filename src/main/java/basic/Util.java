package basic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
	public static boolean DEBUG = true;
	private static final int MAX_LOG_SIZE = 2 * 1024 * 1024; // 2MB
	private static int logCounter = 0;

	private static ResourceBundle rb = ResourceBundle.getBundle("config");

	public static void writeLog(String msg, Exception e) {
		try {
			File f = new File(rb.getString("logs.path") + getLogName());
			if (!f.exists())
				f.createNewFile();
			FileWriter fw = null;
			fw = new FileWriter(f, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("[message: " + msg + " ]");
			e.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (IOException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void writeLog(String msg) {
		try {
			File f = new File(rb.getString("logs.path") + getLogName());
			if (!f.exists())
				f.createNewFile();
			FileWriter fw = null;
			fw = new FileWriter(f, true);
			fw.write("[message: " + msg + " ]\n");
			fw.close();
		} catch (IOException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static String getLogName() {
		GregorianCalendar today = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyy");
		try {
			File directory = new File(rb.getString("logs.path"));
			File lastUpdatedFile = null;
			File[] filesList = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".txt");
				}
			});

			for (File f : filesList) {
				if (lastUpdatedFile == null) {
					lastUpdatedFile = f;
					continue;
				}
				if (new Date(lastUpdatedFile.lastModified()).before(new Date(f.lastModified())))
					lastUpdatedFile = f;
			}
			if (lastUpdatedFile == null)
				return "log" + sdf.format(today.getTime()) + ".txt";
			Date currentFileDate = sdf.parse(lastUpdatedFile.getName().substring("log".length(),
					lastUpdatedFile.getName().length() - ".txt".length()));
			if (currentFileDate.before(sdf.parse(sdf.format(today.getTime()))))
				return "log" + sdf.format(today.getTime()) + ".txt";
			if (lastUpdatedFile.length() > MAX_LOG_SIZE) {
				logCounter++;
				return "log" + sdf.format(today.getTime()) + "_" + logCounter + ".txt";
			}
			return lastUpdatedFile.getName();
		} catch (Exception ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return "log" + sdf.format(today.getTime()) + ".txt";
		}
	}
}
