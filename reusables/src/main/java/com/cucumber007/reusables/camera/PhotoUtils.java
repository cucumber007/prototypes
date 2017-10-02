package com.cucumber007.reusables.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.cucumber007.reusables.R;
import com.cucumber007.reusables.logging.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoUtils {

    public static final int REQUEST_IMAGE_CAPTURE = 8223;
    static String mCurrentPhotoPath;

    @Nullable
    public static Intent createCameraPictureIntent(Context context, File imageFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = imageFile;

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.chesno.adbusters.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                return takePictureIntent;
            } else {
                return null;
                //todo handle error
            }
        } else {
            return null;
            //todo handle error
        }
    }

    public static File createImageFile(Context context) {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        LogUtil.logDebug(mCurrentPhotoPath);
        return image;
    }

    public static void insertBitmapToGallery(Context context, Bitmap bitmap) {
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                context.getResources().getString(R.string.app_name), "");
    }

    public static String insertBitmapToGallery(Context context, String path) {
        try {
            return MediaStore.Images.Media.insertImage(context.getContentResolver(), path,
                    context.getResources().getString(R.string.app_name), "");
        } catch (FileNotFoundException e) {
            LogUtil.makeToastWithDebug("Failed to retrieve photo", "File not found");
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            LogUtil.makeToastWithDebug("Failed to retrieve photo", "NullPointerException");
            e.printStackTrace();
            return null;
        }
    }


}
