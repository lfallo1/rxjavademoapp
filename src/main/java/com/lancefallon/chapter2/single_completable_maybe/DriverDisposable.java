package com.lancefallon.chapter2.single_completable_maybe;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.ResourceObserver;

import java.util.concurrent.TimeUnit;

public class DriverDisposable {

    private static final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * public interface Disposable {
     * void dispose();
     * boolean isDisposed();
     * }
     *
     * @param args
     */

    public static void main(String[] args) {
        Observable<Long> timer = Observable.interval(1, TimeUnit.SECONDS);

        //setup observers
        ResourceObserver<Long> myResourceObserver = getResourceObserver();
        Observer<Long> observer = getObserver();

        //returns void (assumes the observer will handle dispose
        timer.subscribe(observer);

        //all these return dispose
        Disposable disposableObserver = timer.subscribe(s -> System.out.println("##Disposable observer " + s));
        Disposable resourceObserver1 = timer.subscribeWith(myResourceObserver);

        disposables.addAll(disposableObserver, resourceObserver1);

        sleep(2000);

        disposables.dispose();

        sleep(1000);

        System.out.println("is disposed? " + disposables.isDisposed());
    }

    private static void sleep(Integer ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Observer<Long> getObserver() {
        return new Observer<Long>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable disposable) {
                this.disposable = disposable;
            }

            @Override
            public void onNext(Long value) {
                System.out.println("##Observer: " + value);
                this.disposable.dispose();
                System.out.println("##Observer disposed");
            }

            @Override
            public void onError(Throwable e) {
                //has access to Disposable
            }

            @Override
            public void onComplete() {
                //has access to Disposable
            }
        };
    }

    private static ResourceObserver<Long> getResourceObserver() {
        return new ResourceObserver<Long>() {

            @Override
            public void onNext(Long value) {
                System.out.println("##ResourceObserver: " + value);
            }

            @Override
            public void onError(Throwable e) {
                //has access to Disposable
            }

            @Override
            public void onComplete() {
                System.out.println("on complete");
            }
        };
    }


}
