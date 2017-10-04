package com.polyana.cucumber007.rxjava2sandbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity {

    @butterknife.BindView(R.id.textView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        //Single = item | error

        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Single from Callable";
            }
        }).subscribe(this::print);

        //todo threads?
        /*Single.fromFuture(new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Single from FutureTask from Callable";
            }
        })).subscribe(this::print);*/

        Single.just("Just Single string").subscribe(this::print);

        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> e) throws Exception {
                e.onSuccess("Custom Single");
            }
        }).subscribe(this::print);

        Single.fromCallable(() -> {
            List list = null;
            list.size();
            return "Error single";
        }).subscribe(this::print, this::printError);

        //item | complete | error Maybe
        //complete | error Completable
    }

    private void print(String string) {
        textView.append(string+"\n\n");
    }

    private void printError(Throwable error) {
        print(error.getMessage());
    }
}
