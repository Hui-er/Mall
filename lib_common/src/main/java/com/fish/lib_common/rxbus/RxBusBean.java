package com.fish.lib_common.rxbus;

/**
 * <pre>
 *     author: Blankj
 *     github: Blankj/RxBus
 *     desc  : 对应 TagMessage
 * </pre>
 */
final class RxBusBean {

    Object mEvent;
    String mTag;

    RxBusBean(Object event, String tag) {
        mEvent = event;
        mTag = tag;
    }

    boolean isSameType(final Class eventType, final String tag) {
        return RxBusUtils.equals(getEventType(), eventType)
                && RxBusUtils.equals(this.mTag, tag);
    }

    Class getEventType() {
        return RxBusUtils.getClassFromObject(mEvent);
    }

    @Override
    public String toString() {
        return "event: " + mEvent + ", tag: " + mTag;
    }
}
