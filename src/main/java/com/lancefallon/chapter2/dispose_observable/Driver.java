package com.lancefallon.chapter2.dispose_observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Driver {
    public static void main(String[] args) {
        Observable<Integer> source = Observable.just(1, 2, 3, 4, 5);
        source.subscribe(new Observer<Integer>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("subscribing");
                this.disposable = disposable;
            }

            @Override
            public void onNext(Integer n) {
                System.out.println("received value: " + n);
            }

            @Override
            public void onError(Throwable throwable) {
                disposable.dispose();
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("completed");
                disposable.dispose();
            }
        });
    }
}
