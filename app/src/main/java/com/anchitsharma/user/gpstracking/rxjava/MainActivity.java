package com.anchitsharma.user.gpstracking.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.anchitsharma.user.gpstracking.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnTapArea;
    private TextView txtTapResult;
    private TextView txtTapResultMax;
    private Disposable disposable;
    private int maxTaps = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTapArea = (Button)findViewById(R.id.layout_tap_area);
        txtTapResult = (TextView)findViewById(R.id.tap_result);
        txtTapResultMax = (TextView)findViewById(R.id.tap_result_max_count);

        RxView.clicks(btnTapArea)
                .map(new Function<Object, Integer>() {
                    @Override
                    public Integer apply(@NonNull Object o) throws Exception {
                        return 1;
                    }
                })
                .buffer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Integer> integers) {
                        Log.d(TAG, "onNext: "+integers.size()+" taps received");
                        for(int i :integers){
                            maxTaps = integers.size()>maxTaps?integers.size():maxTaps;
                            txtTapResult.setText(String.format("Received %d taps in 3 seconds",integers.size()));
                            txtTapResultMax.setText(String.format("Maximum of %d taps recieved in this session ", maxTaps));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();

    }
}
