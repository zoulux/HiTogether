package com.lyy.hitogether.util;

import java.io.File;

import android.os.Environment;

public class FileService {
		public static void makeFile(String filePath){
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
		}
}
