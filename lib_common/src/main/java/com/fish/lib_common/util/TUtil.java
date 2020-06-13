package com.fish.lib_common.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by hpw on 16/10/28.
 */

public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {

            Class<T> tClass = (Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass()))
                    .getActualTypeArguments()[i];

            return tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

