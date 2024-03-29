package ua.in.sz.rxjava.core;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Using {
    public static void main(String[] args) {
        log.info("Start");

        Observable<Long> observable = Observable.using(
                Using::openResource,
                Using::intervalRange,
                Using::closeResource
        );

        observable.subscribe((e) -> log.info("Receive: {}", e));
        Completable.fromObservable(observable).blockingAwait();

        log.info("End");
    }

    private static ObservableSource<Long> intervalRange(@NonNull String resource) throws Exception {
        return Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS);
    }

    private static String openResource() {
        String resource = "Resource 1";
        log.info("Open {}", resource);
        return resource;
    }

    private static void closeResource(String resource) {
        log.info("Close {}", resource);
    }
}
