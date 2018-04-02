package com.cucumber007.prototypes.cases.state_and_callbacks.solutions.livedata;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.cucumber007.prototypes.cases.SampleDataProvider;

public class BitmapViewModel extends AndroidViewModel {

    private LiveData<Bitmap> bitmap;
    private Application application;

    public BitmapViewModel(@NonNull Application application) {
        super(application);
        bitmap = new BitmapLiveData(application);
        this.application = application;
    }

    public LiveData<Bitmap> getBitmap() {
        return bitmap;
    }

    public void update() {
        bitmap = new BitmapLiveData(application);
    }

    private class BitmapLiveData extends MutableLiveData<Bitmap> {

        private final Context context;

        public BitmapLiveData(Context context) {
            this.context = context;
            loadData();
        }

        @SuppressLint("StaticFieldLeak")
        private void loadData() {
            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return SampleDataProvider.getBitmap(2000, 2000);
                }

                @Override
                protected void onPostExecute(Bitmap data) {
                    setValue(data);
                }
            }.execute();
        }
    }

}
