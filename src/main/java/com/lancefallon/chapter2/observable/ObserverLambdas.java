package com.lancefallon.chapter2.observable;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ObserverLambdas {

    public static void main(String[] args) {
        Observable<String> source =
                Observable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon");

        Consumer<String> onNext = i -> System.out.println("##ObserverLambdas## -> RECEIVED: " + i);
        Action onComplete = () -> System.out.println("Done!");
        Consumer<Throwable> onError = Throwable::printStackTrace;

        source.subscribe(onNext, onError, onComplete);
    }

}
