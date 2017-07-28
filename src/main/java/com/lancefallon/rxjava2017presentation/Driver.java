package com.lancefallon.rxjava2017presentation;

import io.reactivex.Observable;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Driver {

    public static void main(String[] args) {
//        Disposable disposable = fakeUserInput().subscribe(n -> System.out.println(n));
//
//        while (!disposable.isDisposed()) {
//            sleep(100);
//        }

        fakeUserInput().blockingSubscribe(n -> System.out.println(n));
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Observable<Long> fakeUserInput() {
        Random random = new Random();
        return Observable.intervalRange(1, 20, 1000, 375, TimeUnit.MILLISECONDS)
                .concatMap(number -> Observable.just(random.nextLong() * number * Calendar.getInstance().getTimeInMillis() / 1000)
                        .delay(random.nextInt(2000), TimeUnit.MILLISECONDS));
    }
}
