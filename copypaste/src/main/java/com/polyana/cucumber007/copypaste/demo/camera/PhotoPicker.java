package com.polyana.cucumber007.copypaste.demo.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.cucumber007.reusables.camera.PhotoUtils;
import com.cucumber007.reusables.utils.Callback;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.polyana.cucumber007.copypaste.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;

//todo copied from homy
public class PhotoPicker {

    public static final int REQUEST_PICKER = 7002;
    public static final int REQUEST_IMAGE_CAPTURE = 7006;
    private static String mCurrentPhotoPath;

    private Context context;
    private StartActivityForResultDelegate delegate;
    private AlertDialog dialog;
    private String lastImagePath;

    public PhotoPicker(Context context, StartActivityForResultDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    public void startPick() {
        if (dialog == null) dialog = createPhotoSourceDialog();
        dialog.show();
    }

    public void obtainBitmap(int requestCode, int resultCode, Intent data, Callback<ImageWithThumbnail> callback) {
        switch (requestCode) {
            //todo handle errors
            case REQUEST_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    getImage(lastImagePath, image -> {
                        callback.onReady(image);
                    });
                }
                break;
            }
            case REQUEST_PICKER: {
                if (data != null) {
                    getImage(data.getData(), image -> {
                        callback.onReady(image);
                    });
                }
                break;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Utils
    ///////////////////////////////////////////////////////////////////////////

    private AlertDialog createPhotoSourceDialog() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogStyle);

        builder.setMessage(context.getResources().getString(R.string.ad_photo_source))
                .setTitle(context.getResources().getString(R.string.ad_add_photo))
                .setPositiveButton(context.getResources().getString(R.string.ad_gallery), (dialogInterface, i) -> loadPhotoFromGallery())
                .setNegativeButton(context.getResources().getString(R.string.ad_camera), (dialogInterface, i) -> loadPhotoFromCamera());
        return builder.create();*/
        return null;
    }

    private void loadPhotoFromGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        //todo
        //Intent chooserIntent = Intent.createChooser(getIntent, context.getResources().getString(R.string.ad_add_photo));
        Intent chooserIntent = Intent.createChooser(getIntent, "");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        delegate.startActivityForResult(chooserIntent, REQUEST_PICKER);
    }

    private void loadPhotoFromCamera() {
        File imageFile = createImageFile(context);
        lastImagePath = imageFile.getPath();
        delegate.startActivityForResult(createCameraPictureIntent(context, imageFile), REQUEST_IMAGE_CAPTURE);
    }

    @Nullable
    public static Intent createCameraPictureIntent(Context context, File imageFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = imageFile;

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.cucumber007.homy.fileprovider", photoFile);
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
            Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(in -> {
                LogUtil.makeToastWithDebug("Failed to retrieve photo", "File not found");
            });
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(in -> {
                LogUtil.makeToastWithDebug("Failed to retrieve photo", "NullPointerException");
            });
            e.printStackTrace();
            return null;
        }
    }

    public void getImage(String filename, Callback<ImageWithThumbnail> callback) {
        registerPhotoAndAddToGallery(filename, thumbnail -> {
            callback.onReady(new ImageWithThumbnail(filename, thumbnail));
        });
    }

    public void getImage(Uri uri, Callback<ImageWithThumbnail> callback) {
        registerPhoto(uri, thumbnail -> {
            callback.onReady(new ImageWithThumbnail(uri, thumbnail));
        });
    }

    public void registerPhotoAndAddToGallery(String path, Callback<Bitmap> thumbnailCallback) {

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                String galleryPath = PhotoUtils.insertBitmapToGallery(context, path);
                return getThumbnail(galleryPath);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                thumbnailCallback.onReady(bitmap);
            }
        }.execute(path);


    }

    public void registerPhoto(Uri uri, Callback<Bitmap> thumbnailCallback) {

        new AsyncTask<Uri, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Uri... uris) {
                return getThumbnail(uri);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                thumbnailCallback.onReady(bitmap);
            }
        }.execute(uri);


    }

    public Bitmap getThumbnail(String path) {
        String[] splitted = path.split("/");
        Long id = Long.parseLong(splitted[splitted.length-1]);
        return MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
    }

    public Bitmap getThumbnail(Uri uri) {
        Long id = Long.parseLong(uri.getLastPathSegment());
        return MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
    }

    public interface StartActivityForResultDelegate {
        void startActivityForResult(Intent intent, int requestCode);
    }

}
