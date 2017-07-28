package com.lancefallon.chapter2.single_completable_maybe;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class Driver {

    public static void main(String[] args) {
        //There are a few specialized flavors of Observable that are explicitly set up for one or no emissions: Single, Maybe, and Completable

        //Single- exactly 1 emission
        /**
         * interfaceSingleObserver<T> {
             voidonSubscribe(Disposabled);
             voidonSuccess(T value);
             voidonError(Throwableerror);
         }
         */
        Single.just("Hello")
                .map(String::length)
                .subscribe(System.out::println,
                        Throwable::printStackTrace);

        //Maybe- 0 or 1 emissions
        //NOTE: onError | onComplete | onSuccess (so onComplete will not be called if onSuccess is called)
        Maybe<Integer> presentSource = Maybe.just(100);

        presentSource.subscribe(s -> System.out.println("Process 1 received: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Process 1 done!"));

        //no emission
        Maybe<Integer> emptySource = Maybe.empty();

        emptySource.subscribe(s -> System.out.println("Process 2 received: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Process 2 done!"));



        // Completable- Completable is simply concerned with an action being executed, but it does not receive any emissions.
        // Logically, it does not have  onNext() or onSuccess() to receive emissions, but it does have onError() and onComplete()
        // allegedly will not use very often
        Completable.fromRunnable(() -> runProcess())
                .subscribe(() -> System.out.println("Done!"));
    }

    public static void runProcess() {
        //run process here
    }
}
