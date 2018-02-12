package com.cucumber007.prototypes.activities.loaders;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FilesAndLoaderActivity extends Activity {

    //todo why the fuck we need to create a whole loader to get path from uri???
    private static final int REQUEST_CODE_PICTURE = 1;
    @BindView(R.id.imageView7) ImageView imageView7;
    @BindView(R.id.imageView8) ImageView imageView8;

    private Context context = this;

    private Uri imageUri;
    private Observable<Bitmap> bitmapObservable;
    private Bitmap imageBitmap;

    public static final String IMAGE_PATH = "save.jpg";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.button6)
    void click() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_PICTURE);
    }


    @OnClick(R.id.button7)
    void click1() {
        imageView8.setImageDrawable(getResources().getDrawable(R.color.red));
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                File file = null;
                try {
                    file = File.createTempFile(IMAGE_PATH, "", getCacheDir());
                    OutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return file.getName();
            }


            @Override
            protected void onPostExecute(String filename) {
                super.onPostExecute(filename);
                createImageLoadingObservable(getCacheDir() + "/" + filename).subscribe(bitmap -> {
                    imageView8.setImageBitmap(bitmap);
                });
            }
        }.execute();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICTURE) {
                imageUri = data.getData();
                getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        String[] projection = {MediaStore.Images.Media.DATA};
                        return new CursorLoader(context, imageUri, projection, null, null, null);
                    }


                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                        if (data != null) {
                            int columnIndex = data.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            data.moveToFirst();
                            String imagePath = data.getString(columnIndex);
                            imageView7.setImageDrawable(getResources().getDrawable(R.color.red));
                            bitmapObservable = createImageLoadingObservable(imagePath);
                            bitmapObservable.subscribe(bitmap -> {
                                imageBitmap = bitmap;
                                imageView7.setImageBitmap(bitmap);
                            });
                        } else {
                            //todo ???
                            String imagePath = imageUri.getPath();
                        }
                    }


                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                    }
                });
            }
        }
    }


    private Observable<Bitmap> createImageLoadingObservable(String path) {
        return Observable.<Bitmap>create(subscriber -> {
            File file = new File(path);
            byte[] bytes = new byte[(int) file.length()];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
            } catch (Exception e) {
                subscriber.onError(e);
            }

            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            subscriber.onNext(bmp);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

}
