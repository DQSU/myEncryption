package com.hitrontech.hitronencryption;


import android.util.Log;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

class Md5Utils {

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    static String getFileMd5(String filePath){
        if (filePath == null) {
            Log.w("Hitron:MD5", "getFileMd5(), filePath is null");
            return null;
        }
        return md5sum(filePath);
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b: bytes) {
            sb.append(HEX_DIGITS[(b & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b & 0x0f]);
        }
        return sb.toString();
    }

    private static String md5sum(String filename) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead;
        MessageDigest md5;
        try {
            fis = new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest()).trim();
        } catch (Exception e) {
            Log.w("Hitron:MD5", "MD5 failly.");
            return null;
        }
    }

    static String md5Str(String src) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            return toHexString(md5.digest(src.getBytes())).trim();
        } catch (Exception e) {
            Log.w("Hitron:MD5", "MD5 failly.");
            return null;
        }
    }
}
