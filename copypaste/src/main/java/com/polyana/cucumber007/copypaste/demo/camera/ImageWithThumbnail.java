package com.polyana.cucumber007.copypaste.demo.camera;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.cucumber007.reusables.ContextApplication;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageWithThumbnail {
    private Uri uri;
    private String imagePath;
    private Bitmap thumbnail;

    public ImageWithThumbnail(String imagePath, Bitmap thumbnail) {
        this.imagePath = imagePath;
        this.thumbnail = thumbnail;
    }

    public ImageWithThumbnail(Uri uri, Bitmap thumbnail) {
        this.uri = uri;
        this.thumbnail = thumbnail;
    }

    public void getImageAsync(BitmapCallback callback) {
        if (imagePath != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            callback.onReady(BitmapFactory.decodeFile(imagePath, options));
        } else {
            try {
                callback.onReady(MediaStore.Images.Media
                        .getBitmap(ContextApplication.getContext().getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
                callback.onReady(null);
            }
        }
    }

    public String getPath() {
        if (imagePath != null) return imagePath;
        else {
            Cursor cursor = ContextApplication.getContext().getContentResolver().query(uri, null, null, null, null);
            if(cursor.moveToFirst()) {
                cursor.getPosition();
                String path = cursor.getString(cursor.getColumnIndex("_data"));
                cursor.close();
                return path;
            } else return null;
        }
    }

    public Bitmap getImageBitmap() {
        String path = getPath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        LogUtil.logDebug(path);
        return BitmapFactory.decodeFile(path, options);
    }

    public byte[] getImageBytes() throws IOException {
        File file = new File(getPath());
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        buf.read(bytes, 0, bytes.length);
        buf.close();
        return bytes;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }


    public interface BitmapCallback {
        void onReady(Bitmap bitmap);
    }

}
