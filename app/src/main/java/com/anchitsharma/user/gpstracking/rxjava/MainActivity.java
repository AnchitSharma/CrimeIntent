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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
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

        Observable<User> userObservable = getObservable();
       userObservable
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .filter(new Predicate<User>() {
                   @Override
                   public boolean test(@NonNull User user) throws Exception {
                       return user.getGender().equalsIgnoreCase("female");
                   }
               })
               .subscribeWith(new DisposableObserver<User>() {
                   @Override
                   public void onNext(@NonNull User user) {
                       Log.d(TAG, user.getUname()+"="+user.getGender());
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {

                   }

                   @Override
                   public void onComplete() {

                   }
               });


    }



private Observable<User> getObservable(){
    final List<User> users = new ArrayList<>();
    users.add(new User("Mark","male"));
    users.add(new User("Lucy","female"));
    users.add(new User("John","male"));
    users.add(new User("Scarlett","female"));
    users.add(new User("Trump","male"));
    users.add(new User("April","female"));
    users.add(new User("Obama","male"));
    return Observable.create(new ObservableOnSubscribe<User>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Exception {
            for (User user: users){
                if(!emitter.isDisposed()){
                    emitter.onNext(user);
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
