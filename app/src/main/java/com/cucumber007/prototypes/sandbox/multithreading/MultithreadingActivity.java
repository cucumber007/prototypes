package com.cucumber007.prototypes.sandbox.multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;

public class MultithreadingActivity extends AppCompatActivity {

    Thread generatorThread;
    ReceiverThread receiverThread;
    LooperThread looperReceiverThread;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multithreading);

        LogUtil.logDebug("GoGoGo ----------");

        //todo threadGroup
        //todo interrupt
        //todo looper wtf

        LogUtil.logDebug(Thread.currentThread().getName());

        generatorThread = new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                int generatedNumber = i;
                LogUtil.logDebug("= Generated", generatedNumber);
                Bundle bundle = new Bundle();
                bundle.putInt("number", generatedNumber);
                Message message = Message.obtain();
                message.setData(bundle);
                mainHandler.dispatchMessage(message);
                looperReceiverThread.handler.dispatchMessage(message);
                receiverThread.handler.dispatchMessage(message);
            }
        });

        receiverThread = new ReceiverThread();
        looperReceiverThread = new LooperThread();

        mainHandler = new Handler(msg -> {
            LogUtil.logDebug("main", msg.getData().getInt("number"));
            return true;
        });

        receiverThread.start();
        looperReceiverThread.start();
        generatorThread.start();
    }

    class ReceiverThread extends Thread {
        public Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                LogUtil.logDebug("receiver", msg.getData().getInt("number"));
            }
        };

        @Override
        public void run() {
        }
    }

    class LooperThread extends Thread {
        public Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                LogUtil.logDebug("looper", msg.getData().getInt("number"));
            }
        };

        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        }
    }


}
