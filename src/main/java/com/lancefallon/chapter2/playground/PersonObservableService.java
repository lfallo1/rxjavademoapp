package com.lancefallon.chapter2.playground;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class PersonObservableService {

    private PublishSubject<Mailable> persons;

    public PersonObservableService() {
        this.persons = PublishSubject.create();
    }

    public void subscribe(Consumer<? super Mailable> onNext) {
        persons.subscribe(onNext);
    }

    public void addUser(Mailable person) {
        persons.onNext(person);
    }

}
