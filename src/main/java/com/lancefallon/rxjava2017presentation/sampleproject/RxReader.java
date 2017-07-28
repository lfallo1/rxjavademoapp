package com.lancefallon.rxjava2017presentation.sampleproject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RxReader {

    public static Observable<String> lines(BufferedReader reader) {
        return Observable.<String>create(emitter -> {
            String line;
            while ((line = reader.readLine()) != null) {
                emitter.onNext(line);
                if (emitter.isDisposed()) {
                    break;
                }
            }

            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    public static Observable<String> linesFromInput() {
        return lines(new BufferedReader(new InputStreamReader(System.in)));
    }
}
