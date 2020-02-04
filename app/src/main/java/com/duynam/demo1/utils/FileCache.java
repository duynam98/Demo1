package com.duynam.demo1.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class FileCache {

    public static final String FILENAME = "login.cache";

    public static void create(Context context, String content, String logincache){
        try{
            File file = context.getCacheDir();
            String filename = logincache;
            File cacheFile =  new File(file, filename);
            FileOutputStream outputStream = new FileOutputStream(cacheFile.getAbsoluteFile());
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String readCache(Context context, String logincache) {
        try {
            File folderCacheDir = context.getCacheDir();
            String fileCacheName = logincache;
            File fileCache = new File(folderCacheDir, fileCacheName);
            FileInputStream inputStream = new FileInputStream(fileCache.getAbsoluteFile());
            Scanner scanner = new Scanner(inputStream);
            String content = "";
            while (scanner.hasNext()) {
                content += scanner.nextLine() + "\n";
            }
            inputStream.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteAllCache(Context context) {
        try {
            File folderCacheDir = context.getCacheDir();
            File listFile[] = folderCacheDir.listFiles();
            for (File file : listFile) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
