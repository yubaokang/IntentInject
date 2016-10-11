package com.ybk.intent.inject.api;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yubao on 2016/10/7.
 */
public class IntentInject {
    private static final Map<String, Inject> FINDER_MAP = new HashMap<>();

    /**
     * @param activity 在Activity中使用
     */
    public static void inject(Activity activity) {
        String className = activity.getClass().getName();
        try {
            Inject inject = FINDER_MAP.get(className);
            if (inject == null) {
                Class<?> finderClass = Class.forName(className + "_Intent");
                inject = (Inject) finderClass.newInstance();
                FINDER_MAP.put(className, inject);
            }
            inject.inject(activity);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject for " + className, e);
        }
    }

    /**
     * @param fragment 在Fragment-v4 中使用
     */
    public static void inject(Fragment fragment) {
        String className = fragment.getClass().getName();
        try {
            Inject inject = FINDER_MAP.get(className);
            if (inject == null) {
                Class<?> finderClass = Class.forName(className + "_Intent");
                inject = (Inject) finderClass.newInstance();
                FINDER_MAP.put(className, inject);
            }
            inject.inject(fragment);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject for " + className, e);
        }
    }
}
