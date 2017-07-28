package com.lancefallon.chapter1.rxjavademo;

import io.reactivex.Observable;

public class Driver {

    public static void main(String[] args) {
        Observable<String> myStrings = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        myStrings.map(s -> s.length()).subscribe(len -> System.out.println(len));

        TaskScheduler scheduler = new TaskScheduler();
        scheduler.subscribe(n -> System.out.println(n * n));
        scheduler.subscribe(n -> System.out.println("Number is " + n));

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
