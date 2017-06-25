package com.cucumber007.prototypes.activities.giver;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.cucumber007.prototypes.ContextApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class PhotoTransferManager {
    private static PhotoTransferManager instance = new PhotoTransferManager();
    private PhotoTransferManager() {}
    public static PhotoTransferManager getInstance() {return instance;}

    public static final String OVERLAY_FILENAME = "_overlay";
    public static final String IMAGE_FILENAME = "image.png";

    private Bitmap currentBitmap;
    private Bitmap currentOverlay;
    private String filename;
    private String overlayFilename;

    public void saveBitmap(Bitmap result, @Nullable Bitmap overlay) {
        try {
            filename = saveBitmapFile(result, IMAGE_FILENAME);
            if (overlay != null) overlayFilename = saveBitmapFile(overlay, OVERLAY_FILENAME);

            //todo catch recycled bitmap bugs
            //if(currentBitmap != null && !currentBitmap.isRecycled()) currentBitmap.recycle();
            //if(currentOverlay != null && !currentOverlay.isRecycled() && overlay != null) currentOverlay.recycle();

            currentBitmap = result;
            currentOverlay = overlay;

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
        return filename;
    }



    public boolean isDataLoaded() {
        return currentBitmap != null && !currentBitmap.isRecycled() && currentOverlay != null && !currentOverlay.isRecycled();
    }

    @Nullable
    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    @Nullable
    public Bitmap getCurrentOverlay() {
        return currentOverlay;
    }

    public String getFilename() {
        return filename;
    }

    public String getOverlayFilename() {
        return overlayFilename;
    }


}
