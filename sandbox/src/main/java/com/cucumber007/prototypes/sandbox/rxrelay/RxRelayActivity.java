package com.cucumber007.prototypes.sandbox.rxrelay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.jakewharton.rxrelay2.ReplayRelay;

import io.reactivex.functions.Consumer;


public class RxRelayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_relay);

        //BehaviorRelay

        //Relay that emits the most recent item it has observed and all subsequent observed items to each subscribed Observer.

        // observer will receive all events.
        BehaviorRelay<String> behaviorRelay = BehaviorRelay.createDefault("default");
        behaviorRelay.subscribe(s -> LogUtil.logDebug("1", s));
        behaviorRelay.accept("one");
        behaviorRelay.accept("two");
        behaviorRelay.accept("three");

        // observer will receive the "one", "two" and "three" events, but not "zero"
        behaviorRelay = BehaviorRelay.createDefault("default");
        behaviorRelay.accept("zero");
        behaviorRelay.accept("one");
        behaviorRelay.subscribe(s -> LogUtil.logDebug("2", s));
        behaviorRelay.accept("two");
        behaviorRelay.accept("three");

        //PublishRelay

        //Relay that, once an Observer has subscribed, emits all subsequently observed items to the subscriber.

        PublishRelay<Object> publishRelay = PublishRelay.create();

        // observer1 will receive all events
        publishRelay.subscribe(s -> LogUtil.logDebug("3", s));
        publishRelay.accept("one");
        publishRelay.accept("two");

        // observer2 will only receive "three"
        publishRelay.subscribe(s -> LogUtil.logDebug("4", s));
        publishRelay.accept("three");

        //ReplayRelay

        //Relay that buffers all items it observes and replays them to any Observer that subscribes.

        ReplayRelay<Object> replayRelay = ReplayRelay.create();
        replayRelay.accept("one");
        replayRelay.accept("two");
        replayRelay.accept("three");
        // both of the following will get the events from above
        replayRelay.subscribe(s -> LogUtil.logDebug("5", s));
        replayRelay.subscribe(s -> LogUtil.logDebug("6", s));
    }
}
