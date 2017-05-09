package com.ybk.intent.inject.api;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

public class IntentInject {
    private static final Map<String, Inject> FINDER_MAP = new HashMap<>();

    /**
     * @param activity 在Activity中使用
     */
    public static void inject(Activity activity) {
        _inject(activity);
    }

    /**
     * @param fragment 在Fragment-v4 中使用
     */
    public static void inject(android.support.v4.app.Fragment fragment) {
        _inject(fragment);
    }

    /**
     * @param fragment 在Fragment 中使用
     */
    public static void inject(android.app.Fragment fragment) {
        _inject(fragment);
    }

    private static void _inject(Object host) {
        String className = host.getClass().getName();
        try {
            Inject inject = FINDER_MAP.get(className);
            if (inject == null) {
                Class<?> finderClass = Class.forName(className + "_Builder");
                inject = (Inject) finderClass.newInstance();
                FINDER_MAP.put(className, inject);
            }
            inject.inject(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
