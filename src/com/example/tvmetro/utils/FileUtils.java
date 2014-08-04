package com.example.tvmetro.utils;

import java.io.BufferedInputStream;
import java.io.IOException;

import android.content.Context;

public class FileUtils {

    public static String readFileFromAssets(Context context, String name) {
        BufferedInputStream is = null;
        byte[] buffer = new byte[1024];
        String content = "";
        try {
            is = new BufferedInputStream(context.getAssets().open(name));
            int bytes = 0;
            while ((bytes = is.read(buffer)) != -1) {
                content += new String(buffer, 0, bytes);
            }
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content;
    }
}
