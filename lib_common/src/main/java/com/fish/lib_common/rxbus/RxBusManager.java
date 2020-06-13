package com.fish.lib_common.rxbus;


import io.reactivex.Scheduler;

public class RxBusManager {

    /**
     * 用于当前页面内事件传递,来发送事件
     *
     * @param event
     * @param eventTag
     */
    public static void post(final String eventTag, final Object event) {
        RxBus.getDefault().post(eventTag, event);
    }

    /**
     * 用于跨页面之间事件传递,来发送事件
     *
     * @param event
     * @param eventTag
     */
    public static void postSticky(final Object event, String eventTag) {
        RxBus.getDefault().postSticky(event, eventTag);
    }

    /**
     * 用于当前页面内事件传递时,用此方法注册事件（注意此时需要先注册，在发送）
     *
     * @param subscriber
     * @param eventTag
     * @param rxCallback
     */
    public static void subscriber(final Object subscriber, String eventTag, RxBus.Callback rxCallback) {
        RxBus.getDefault().subscribe(subscriber, eventTag, rxCallback);
    }

    public static void subscriber(final Object subscriber, String eventTag, Scheduler scheduler, RxBus.Callback rxCallback) {
        RxBus.getDefault().subscribe(subscriber, eventTag, scheduler, rxCallback);
    }

    /**
     * 用于跨页面之间事件传递时,用此方法注册事件(可以不用先注册)
     *
     * @param subscriber
     * @param eventTag
     * @param rxCallback
     */
    public static void subscriberSticky(final Object subscriber, String eventTag, RxBus.Callback rxCallback) {
        RxBus.getDefault().subscribeSticky(subscriber, eventTag, rxCallback);
    }

    /**
     * 解除注册,此方法必须调用（如果采用了RxBus发送事件）
     *
     * @param object
     */
    public static void unRegister(final Object object) {
        RxBus.getDefault().unregister(object);
    }

    /**
     * 此方法在跨页面事件注册后,销毁事件时必须调用
     *
     * @param event
     * @param eventTag
     */
    public static void removeSticky(final Object event, String eventTag) {
        RxBus.getDefault().removeSticky(event, eventTag);
    }
}
