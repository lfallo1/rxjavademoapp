package com.lancefallon.chapter1.rxjavademo;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    Observable<Long> secondIntervals;

    public TaskScheduler() {
        this.secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
    }

    public void subscribe(Consumer<? super Long> fn) {
        this.secondIntervals.subscribe(fn);
    }
}
