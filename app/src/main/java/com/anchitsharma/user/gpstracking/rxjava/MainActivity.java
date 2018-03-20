package com.anchitsharma.user.gpstracking.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anchitsharma.user.gpstracking.R;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable<Note> numberObservable = getObservable();
      numberObservable
              .observeOn(Schedulers.io())
              .subscribeOn(AndroidSchedulers.mainThread())
              .distinct()
              .subscribeWith(getObserver());


    }






    private DisposableObserver<Note> getObserver(){
        return new DisposableObserver<Note>() {
            @Override
            public void onNext(@NonNull Note note) {
                Log.d(TAG, "onNext: "+note.getNote());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: "+e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

private Observable<Note> getObservable(){
    final List<Note> notes = new ArrayList<>();
    notes.add(new Note(1, "Buy tooth paste!"));
    notes.add(new Note(2, "Call brother!"));
    notes.add(new Note(3, "Call brother!"));
    notes.add(new Note(4, "Pay power bill!"));
    notes.add(new Note(5, "Watch Narcos tonight!"));
    notes.add(new Note(6, "Buy tooth paste!"));
    notes.add(new Note(7, "Pay power bill!"));

    return Observable.create(new ObservableOnSubscribe<Note>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<Note> emitter) throws Exception {
            for(Note note:notes){
                if (!emitter.isDisposed()){
                    emitter.onNext(note);
                }
            }

            if (!emitter.isDisposed()){
                emitter.onComplete();
            }

        }
    });
}



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
