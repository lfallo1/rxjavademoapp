package com.lancefallon.chapter2.buttons_example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.reactivex.Observable;
import javafx.event.Event;
import javafx.event.EventType;

import java.util.Random;

public class Driver {

    private static final String GITHUB_URL = "https://api.github.com/users";

    public static void main(String[] args) {

    }

    private static Observable<Event> emulateClick() {
        return Observable.just(new Event(new EventType("ButtonClick")));
    }

    private static void urlExampleTwo() {

        Observable<Event> refreshClickStream = emulateClick();

        Observable<String> requestStream = refreshClickStream
                .map(s -> GITHUB_URL + "?since=" + new Random().nextInt(500))
                .startWith(Observable.just(GITHUB_URL));

        Observable<HttpResponse<JsonNode>> responseStream = requestStream
                .map(requestUrl -> Unirest.get(requestUrl).asJson());

        responseStream.subscribe(response -> {
            //handle emitted values (in this case, http responses)
            System.out.println(response.getBody().getArray().length() + " items returned");
        });
    }

}
