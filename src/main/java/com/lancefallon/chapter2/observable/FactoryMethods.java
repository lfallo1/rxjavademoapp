package com.lancefallon.chapter2.observable;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FactoryMethods {

    private static int start = 1;
    private static int count = 5;

    public static void main(String[] args) {

        //just
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        //create
        Observable<String> source2 = Observable.create(emitter -> {
            try {
                emitter.onNext("Alpha");
                emitter.onNext("Beta");
                emitter.onNext("Gamma");
                emitter.onNext("Delta");
//                emitter.onNext(new String[]{"Dog"}[1]); //forcibly cause error
                emitter.onNext("Epsilon");
                emitter.onComplete();
            } catch (Throwable e) {
                emitter.onError(e);
            }
        });

        //fromIterable
        List<String> list = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<String> source3 = Observable.fromIterable(list);
        source3.map(String::length)
                .filter(i -> i >= 5)
                .subscribe(s -> System.out.println("RECEIVED: " + s), Throwable::printStackTrace);

        //ranges
        Observable<Integer> range = Observable.range(7, 100);
        range.subscribe(i -> System.out.println(i));

        //intervals
        Observable<Long> timer = Observable.interval(1, TimeUnit.SECONDS);

        //note that Observable.interval returns a COLD observable.
        //example below shows how subscriber2 gets its own emissions (after 5 secs when it comes in) starting at 0, instead of getting same emissions as observer 1).
        //NOTE: using a CummutableObserver would change behavior such that when observer 2 comes in, it receives same emissions as observer 1
        timer.subscribe(s -> System.out.println((s + 1) + " Mississippi"));
        sleep(1000);
        timer.subscribe(s -> System.out.println((s + 1) + " Alaska"));
        sleep(1000);


        //empty- can occasionally be helpful to create observable that emits nothing and calls onComplete()
        //NOTE: A close cousin of Observable.empty() is Observable.never(). The only difference between them is that it never calls onComplete(), forever leaving observers waiting for emissions but never actually giving any:
        Observable<String> empty = Observable.empty();

        empty.subscribe(System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Done!"));


        //Observable.error()
        //Helpful with testing
        Observable.error(new Exception("Crash and burn!"))
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        Throwable::printStackTrace,
                        () -> System.out.println("Done!"));
        //provide the exception through a lambda so that it is created from scratch and separate exception instances are provided to each Observer:
        Observable.error(() -> new Exception("Crash and burn!"))
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        Throwable::printStackTrace,
                        () -> System.out.println("Done!"));


        // Observable.defer()
        // using Observable.defer(), you can create a fresh Observable for each subscription.
        // Observable.defer() accepts a lambda instructing how to create an Observable for every subscription.
        Observable<Integer> source = Observable.defer(() ->
                Observable.range(start, count));

        source.subscribe(i -> System.out.println("Observer 1: " + i));

        //modify count
        count = 10;

        source.subscribe(i -> System.out.println("Observer 2: " + i));


        // Observable.fromCallable
        // If initializing your emission has a likelihood of throwing an error, you should use Observable.fromCallable() instead of Observable.just().
        Observable.fromCallable(() -> 1 / 0)
                .subscribe(i -> System.out.println("Received: " + i),
                        e -> System.out.println("Error Captured: " + e));

    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
