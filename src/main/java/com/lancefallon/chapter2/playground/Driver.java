package com.lancefallon.chapter2.playground;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.reactivex.Observable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Driver {

    static List<Mailable> personList = new ArrayList<>();

    public static void main(String[] args) {

        urlExampleTwo();

//        PersonObservableService service = new PersonObservableService();
//
//        //setup some subscribers
//        service.subscribe(p -> {
//            personList.add(p);
//            System.out.println("Count of users is " + personList.size());
//            System.out.println(personList.toString());
//        });
//        service.subscribe(p -> displayAdd(p));
//        service.subscribe(p -> p.sendEmail("fallon.lance@gmail.com", "This is a test"));
//
//        //add users
//        service.addUser(new Person(1, "Lance", new Date(1986, 10, 12)));
//        service.addUser(new Person(1, "Lance", new Date(1986, 10, 12)));
    }

    private static void urlExampleTwo() {
        Observable<String> requestStream = Observable.just("https://api.github.com/users");
        Observable<HttpResponse<JsonNode>> responseStream = requestStream
                .map(requestUrl -> Unirest.get(requestUrl).asJson());

        responseStream.subscribe(response -> {
            //handle emitted values (in this case, http responses)
            System.out.println(response.getBody().getArray().length() + " items returned");
        });
    }

    /**
     * return url response wrapped in Observable
     *
     * @param url
     * @return
     */
    private static Observable<HttpResponse<JsonNode>> createUrlResponseStream(String url) {
        return Observable.create(emitter -> {
            //custom stream emitting http response value
            System.out.println("custom stream emitting http response value");
            emitter.onNext(Unirest.get(url).asJson());
        });
    }

    private static void urlExample() {

        Observable<String> requestStream = Observable.just("https://api.github.com/users");
        requestStream.subscribe(requestUrl -> {

            System.out.println("observer received requestUrl: " + requestUrl);
            Observable<HttpResponse<JsonNode>> httpResponseStream = createUrlResponseStream(requestUrl);

            httpResponseStream.subscribe(response -> {
                List<String> gistList = new ArrayList<>();
                Iterator iterator = response.getBody().getArray().iterator();
                while (iterator.hasNext()) {
                    JSONObject obj = (JSONObject) iterator.next();
                    System.out.println(obj.get("gists_url"));
                    gistList.add(obj.getString("gists_url"));
                }

            }, e -> e.printStackTrace());
        });
    }

    private static void displayAdd(Mailable p) {
        System.out.println("###########");
        System.out.println("Adding user");
        System.out.println("###########");
    }

}
