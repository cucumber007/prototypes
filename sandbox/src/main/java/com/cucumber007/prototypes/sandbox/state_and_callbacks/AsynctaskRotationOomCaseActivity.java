package com.cucumber007.prototypes.sandbox.state_and_callbacks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class AsynctaskRotationOomCaseActivity extends AppCompatActivity {

    /*@BindView(R.id.imageView)*/ ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callbacks);
        ButterKnife.bind(this);
        imageView = findViewById(R.id.imageView);

        /*List<Bitmap> bitmaps = Observable.range(0,5)
                .map(el -> SampleDataProvider.getBitmap(200, 200))
                .toList().toBlocking().first();
*/
        //imageView.setImageBitmap(bitmaps.get(0));

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return SampleDataProvider.getBitmap(2000, 2000);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        }.execute();


        //Glide.with(this).load().into(imageView);
    }

}
