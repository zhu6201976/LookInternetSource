package com.example.administrator.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by My on 2017/12/8.
 */

public class StreamUtil {
    public static String streamToString(InputStream inputStream) {
        String result = null;
        ByteArrayOutputStream baos = null;
        byte[] buffer = new byte[1024];
        int len;
        try {
            baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            result = baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null && inputStream != null) {
                try {
                    baos.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
