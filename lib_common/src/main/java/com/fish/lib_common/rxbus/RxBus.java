package com.fish.lib_common.rxbus;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * <pre>
 *     author: Blankj
 *     github: Blankj/RxBus
 *     desc  :
 * </pre>
 */
public final class RxBus {

    private final FlowableProcessor<Object> mBus;

    private final Consumer<Throwable> mOnError = throwable -> RxBusUtils.logE(throwable.toString());

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    static RxBus getDefault() {
        return Holder.BUS;
    }


    void post(final String tag, final Object event) {
        post(event, tag, false);
    }

    void postSticky(final Object event, final String tag) {
        post(event, tag, true);
    }

    private void post(final Object event, final String tag, final boolean isSticky) {
        RxBusUtils.requireNonNull(event, tag);

        RxBusBean msgEvent = new RxBusBean(event, tag);
        if (isSticky) {
            RxBusCacheUtils.getInstance().addStickyEvent(event, tag);
        }
        mBus.onNext(msgEvent);
    }

    void removeSticky(final Object event,
                      final String tag) {
        RxBusUtils.requireNonNull(event, tag);

        RxBusCacheUtils.getInstance().removeStickyEvent(event, tag);
    }


    <T> void subscribe(final Object subscriber, final String tag, final Callback<T> callback) {
        subscribe(subscriber, tag, false, null, callback);
    }

    public <T> void subscribe(final Object subscriber, final Scheduler scheduler, final Callback<T> callback) {
        subscribe(subscriber, "", false, scheduler, callback);
    }

    public <T> void subscribe(final Object subscriber, final String tag, final Scheduler scheduler, final Callback<T> callback) {
        subscribe(subscriber, tag, false, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber, final Callback<T> callback) {
        subscribe(subscriber, "", true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber, final String tag, final Callback<T> callback) {
        subscribe(subscriber, tag, true, null, callback);
    }


    private <T> void subscribe(final Object subscriber, final String tag, final boolean isSticky, final Scheduler scheduler, final Callback<T> callback) {
        RxBusUtils.requireNonNull(subscriber, tag, callback);

        final Class<T> typeClass = RxBusUtils.getTypeClassFromParadigm(callback);

        final Consumer<T> onNext = new Consumer<T>() {
            @Override
            public void accept(T t) {
                callback.onEvent(t);
            }
        };

        if (isSticky) {
            final RxBusBean stickyEvent = RxBusCacheUtils.getInstance().findStickyEvent(typeClass, tag);
            if (stickyEvent != null) {
                Flowable<T> stickyFlowable = Flowable.create(new FlowableOnSubscribe<T>() {
                    @Override
                    public void subscribe(FlowableEmitter<T> emitter) {
                        emitter.onNext(typeClass.cast(stickyEvent.mEvent));
                    }
                }, BackpressureStrategy.LATEST);
                if (scheduler != null) {
                    stickyFlowable = stickyFlowable.observeOn(scheduler);
                }
                Disposable stickyDisposable = RxBusFlowableUtil.subscribe(stickyFlowable, onNext, mOnError);
                RxBusCacheUtils.getInstance().addDisposable(subscriber, stickyDisposable);
            } else {
                RxBusUtils.logW("sticky event is empty.");
            }
        }
        Disposable disposable = RxBusFlowableUtil.subscribe(toFlowable(typeClass, tag, scheduler), onNext, mOnError);
        RxBusCacheUtils.getInstance().addDisposable(subscriber, disposable);
    }

    private <T> Flowable<T> toFlowable(final Class<T> eventType, final String tag, final Scheduler scheduler) {
        Flowable<T> flowable = mBus.ofType(RxBusBean.class)
                .filter(tagMessage -> tagMessage.isSameType(eventType, tag))
                .map(tagMessage -> tagMessage.mEvent)
                .cast(eventType);
        if (scheduler != null) {
            return flowable.observeOn(scheduler);
        }
        return flowable;
    }

    public void unregister(final Object subscriber) {
        RxBusCacheUtils.getInstance().removeDisposables(subscriber);
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    public abstract static class Callback<T> {
        public abstract void onEvent(T t);
    }
}