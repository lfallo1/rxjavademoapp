package com.lancefallon.chapter2.observable;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
//        Observable<String> source = Observable.create(emitter -> {
//            try {
//                emitter.onNext("Alpha");
//                emitter.onNext("Beta");
//                emitter.onNext("Gamma");
//                emitter.onNext("Delta");
////                emitter.onNext(new String[]{"Dog"}[1]); //forcibly cause error
//                emitter.onNext("Epsilon");
//                emitter.onComplete();
//            } catch (Throwable e) {
//                emitter.onError(e);
//            }
//        });
        List<String> list = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable.fromIterable(list)
                .map(String::length)
                .filter(i -> i >= 5)
                .subscribe(s -> System.out.println("RECEIVED: " + s), Throwable::printStackTrace);
    }

}
