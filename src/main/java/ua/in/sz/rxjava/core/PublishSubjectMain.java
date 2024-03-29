package ua.in.sz.rxjava.core;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class PublishSubjectMain {

    public static void main(String[] args) {
        publishToManySubscribes();
    }

    private static void publishToManySubscribes() {
        log.info("Start");

        Observable<Long> range = Observable.intervalRange(0, 4, 100, 300, TimeUnit.MILLISECONDS);

        io.reactivex.subjects.PublishSubject<Object> subject = io.reactivex.subjects.PublishSubject.create();

        subject.subscribe((e) -> log.info("Receive 1: {}", e));
        subject.subscribe((e) -> log.info("Receive 2: {}", e));
        subject.subscribe((e) -> log.info("Receive 3: {}", e));

        range.subscribe(subject);

        Completable.fromObservable(subject).blockingAwait();

        log.info("Source subscribe");
    }

    private static void subjectSubscribesOnTwoRange() {
        log.info("Start");

        Observable<Long> range1 = Observable.intervalRange(0, 4, 100, 300, TimeUnit.MILLISECONDS);
        Observable<Long> range2 = Observable.intervalRange(16, 4, 100, 250, TimeUnit.MILLISECONDS);

        io.reactivex.subjects.PublishSubject<Object> subject = io.reactivex.subjects.PublishSubject.create();

        log.info("Source subscribe");

        range2.subscribe(subject);
        range1.subscribe(subject);

        subject.subscribe((e) -> log.info("Receive: {}", e));

        Completable.fromObservable(subject).blockingAwait();

        log.info("End");
    }
}
