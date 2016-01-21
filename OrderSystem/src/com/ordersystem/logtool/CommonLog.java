package com.ordersystem.logtool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class CommonLog {
	public static void recordLog(String name)  {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(new File("log.txt"),true);
			bw = new BufferedWriter(fw);
			Calendar ca =Calendar.getInstance();
			bw.write(name+" executed at "+ ca.getTime());
			bw.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bw!=null) {
					bw.close();
				}
				if(fw!=null) {
					fw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
