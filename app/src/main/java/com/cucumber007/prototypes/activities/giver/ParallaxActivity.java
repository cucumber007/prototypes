package com.cucumber007.prototypes.activities.giver;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParallaxActivity extends AppCompatActivity {

    public static final double M_PI = Math.PI;
    public static final int SENSIBILITY = 20;
    public static final int FPS_LIMIT = 60;

    private SensorManager sensorManager;
    private Sensor gravitySensor;
    private Activity context = this;
    double[] lastGravity = {0,0,0};
    private SurfaceHolder holder;
    private float[] ds = new float[] {0,0};
    private Bitmap background;

    @BindView(R.id.surfaceView2) SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        ButterKnife.bind(this);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_camera);

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                ds = calculateDs(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);

        holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Thread t = new Thread() {
                    public void run() {
                        while (true) {
                            try {
                                long time = System.currentTimeMillis();
                                drawBackground();
                                long delta = System.currentTimeMillis() - time;
                                long sleep = 1000/FPS_LIMIT - delta;
                                if(sleep < 0) sleep = 0;
                                sleep(sleep);
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                };
                t.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

    }

    synchronized private float[] getDs() {
        return ds;
    }

    private void drawBackground() {
        //Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

        Canvas canvas = holder.lockCanvas();;
        Paint paint = new Paint();
        float[] ds = getDs();
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(background, -ds[0]*SENSIBILITY, -ds[1]*SENSIBILITY, paint);

        //canvas.drawBitmap(b1, (float)Math.random(), (float)Math.random(), paint);
        holder.unlockCanvasAndPost(canvas);
    }

    private float[] calculateDs(float[] values) {
        float gX = values[0];
        float gY = values[1];
        float gZ = values[2];

        double pitch, roll = 0;

        // normalize gravity vector at first
        double gSum = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        if (gSum != 0) {
            gX /= gSum;
            gY /= gSum;
            gZ /= gSum;
        }
        if (gZ != 0)
            roll = Math.atan2(gX, gZ) * 180 / M_PI;
        pitch = Math.sqrt(gX * gX + gZ * gZ);
        if (pitch != 0)
            pitch = Math.atan2(gY, pitch) * 180 / M_PI;
        float dgX = (float)(roll - lastGravity[0]);
        float dgY = (float)(pitch - lastGravity[1]);
        // if device orientation is close to vertical – rotation around x is almost undefined – skip!
        if (gY > 0.99) dgX = 0;
        // if rotation was too intensive – more than 180 degrees – skip it
        if (dgX > 180) dgX = 0;
        if (dgX < -180) dgX = 0;
        if (dgY > 180) dgY = 0;
        if (dgY < -180) dgY = 0;
        if (/*!screen->isPortrait()*/false) {
            // Its landscape mode – swap dgX and dgY
            float temp = dgY;
            dgY = dgX;
            dgX = temp;
        }
        lastGravity[0] = roll;
        lastGravity[1] = pitch;
        return new float[] {dgX, dgY};
    }
}
