package com.sdhz.talkpallive.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Create on 2019-04-23 23:14
 * author revstar
 * Email 1967919189@qq.com
 */
public class BitmapUtils {
    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            int length = conn.getContentLength();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;    // 设置缩放比例
            Rect rect = new Rect(0, 0, 0, 0);
            bitmap = BitmapFactory.decodeStream(bis, rect, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
