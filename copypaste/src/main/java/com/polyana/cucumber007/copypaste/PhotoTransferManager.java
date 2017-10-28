package com.polyana.cucumber007.copypaste;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PhotoTransferManager {
    private static PhotoTransferManager instance = new PhotoTransferManager();
    private PhotoTransferManager() {}
    public static PhotoTransferManager getInstance() {return instance;}

    public static final String IMAGE_FILENAME = "image.png";

    private Bitmap currentBitmap;
    private String path;


    public void saveBitmap(Bitmap result) {
        try {
            this.path = saveBitmapFile(result, IMAGE_FILENAME);
            currentBitmap = result;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String saveBitmapFile(Bitmap bitmap, String path) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] bytes = stream.toByteArray(); //long

        File file = File.createTempFile(path, "", ContextApplication.getContext().getCacheDir());
        String filename = file.getName();
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        outputStream.close();
        return file.getAbsolutePath();
    }

    @Nullable
    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public String getPath() {
        return path;
    }

}
