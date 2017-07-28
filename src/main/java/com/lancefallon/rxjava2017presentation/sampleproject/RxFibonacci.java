package com.lancefallon.rxjava2017presentation.sampleproject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RxFibonacci {

    public static void main(String[] args) {
        RxReader.linesFromInput()
                .observeOn(Schedulers.trampoline())
                .map(x->Integer.parseInt(x))
                .flatMapMaybe(x -> fibs().elementAt(x))
                .blockingSubscribe(System.out::println);
    }

    static Observable<Integer> fibs() {
        return Observable.create(emitter -> {
            int prev = 0;
            int curr = 1;
            emitter.onNext(0);
            emitter.onNext(1);
            while (!emitter.isDisposed()) {
                int oldPrev = prev;
                prev = curr;
                curr += oldPrev;
                emitter.onNext(curr);
            }
        });
    }

    public static Observable<Integer> fakeUserInput() {
        Random random = new Random();
        return Observable.intervalRange(1, 20, 1000, 375, TimeUnit.MILLISECONDS)
                .concatMap(number -> Observable.just(random.nextInt(20))
                        .delay(random.nextInt(2000), TimeUnit.MILLISECONDS));
    }

}
