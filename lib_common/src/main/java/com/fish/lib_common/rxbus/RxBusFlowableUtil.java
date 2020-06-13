package com.fish.lib_common.rxbus;


import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;

/**
 * <pre>
 *     author: Blankj
 *     github: Blankj/RxBus
 *     desc  : 对应 FlowableUtils
 * </pre>
 */
public final class RxBusFlowableUtil {

    public static <T> Disposable subscribe(Flowable<T> flowable,
                                           Consumer<? super T> onNext,
                                           Consumer<? super Throwable> onError) {
        return subscribe(flowable,
                onNext, onError,
                Functions.EMPTY_ACTION,
                FlowableInternalHelper.RequestMax.INSTANCE
        );
    }

    private static <T> Disposable subscribe(Flowable<T> flowable,
                                            Consumer<? super T> onNext,
                                            Consumer<? super Throwable> onError,
                                            Action onComplete,
                                            Consumer<? super Subscription> onSubscribe) {
        ObjectHelper.requireNonNull(flowable, "flowable is null");
        ObjectHelper.requireNonNull(onNext, "onNext is null");
        ObjectHelper.requireNonNull(onError, "onError is null");
        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");

        RxBusSubscriber<T> ls = new RxBusSubscriber<T>(onNext, onError, onComplete, onSubscribe);
        flowable.subscribe(ls);
        return ls;
    }
}
