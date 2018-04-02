package com.cucumber007.prototypes.cases.state_and_callbacks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.SampleDataProvider;

import butterknife.ButterKnife;

public class AsynctaskRotationOomCaseActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callbacks);
        ButterKnife.bind(this);
        imageView = findViewById(R.id.imageView);

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

    }

}
