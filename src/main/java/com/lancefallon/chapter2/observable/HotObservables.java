package com.lancefallon.chapter2.observable;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class HotObservables {

    public static void main(String[] args) {

        //hot observable
        ConnectableObservable<String> source =

                Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                        .publish();

        //Set up observer 1
        source.subscribe(s -> System.out.println("Observer 1: " + s));

        //Set up observer 2
        source.map(String::length)
                .subscribe(i -> System.out.println("Observer 2: " + i));

        //Set up observer 3
        source.subscribe(s -> System.out.println("Observer 3: " + s));

        //Fire!
        source.connect();
    }

}
