package com.cucumber007.prototypes.cases.state_and_callbacks.solutions;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.SampleDataProvider;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsynctaskRotationActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView imageView;

    private static int counter;
    private MyAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callbacks);
        ButterKnife.bind(this);

        counter++;
        LogUtil.logDebug("Starting "+counter);
        asyncTask = new MyAsyncTask(this, counter);
        asyncTask.execute();


        //Glide.with(this).load().into(imageView);
    }

    public void onBitmapReady(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    private static class MyAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        private WeakReference<AsynctaskRotationActivity> activityReference;
        private final int number;

        public MyAsyncTask(AsynctaskRotationActivity activity, int number) {
            this.activityReference = new WeakReference<>(activity);
            this.number = number;
        }

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
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            LogUtil.logDebug("Ended "+number+", null = "+(activityReference.get() == null));
            if (activityReference.get() != null) {
                activityReference.get().onBitmapReady(bitmap);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if  (asyncTask!= null) {
            asyncTask.cancel(true);
            LogUtil.logDebug("Destroy ", asyncTask.number);
        } else {
            LogUtil.logDebug("Destroy");
        }

    }
}
