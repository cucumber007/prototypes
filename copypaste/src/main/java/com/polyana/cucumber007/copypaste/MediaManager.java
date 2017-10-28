package com.polyana.cucumber007.copypaste;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.cucumber007.reusables.camera.PhotoUtils;

public class MediaManager {
    private static MediaManager instance = new MediaManager();

    public static MediaManager getInstance() {
        return instance;
    }

    private Context context;

    private MediaManager() {
        context = ContextApplication.getContext();
    }

    public void registerPhotoAndAddToGallery(String path, ThumbnailCallback callback) {

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                String galleryPath = PhotoUtils.insertBitmapToGallery(context, path);
                return getThumbnail(galleryPath);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                callback.got(bitmap);
            }
        }.execute(path);


    }

    public void registerPhoto(Uri uri, ThumbnailCallback callback) {

        new AsyncTask<Uri, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Uri... uris) {
                return getThumbnail(uri);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                callback.got(bitmap);
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

    public interface ThumbnailCallback {
        void got(Bitmap thumbnail);
    }

}
