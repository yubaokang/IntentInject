package com.ybk.intent.inject.compiler;

import com.squareup.javapoet.ClassName;

public class TypeUtil {
    public static final ClassName INJECT = ClassName.get("com.ybk.intent.inject.api", "Inject");
    public static final ClassName INTENT_BUILDER = ClassName.get("com.ybk.intent.inject.api", "IntentBuilder");
    public static final ClassName FRAGMENT_BUILDER = ClassName.get("com.ybk.intent.inject.api", "FragmentBuilder");
    public static final ClassName INTENT = ClassName.get("android.content", "Intent");
    public static final ClassName BUNDLE = ClassName.get("android.os", "Bundle");
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName CLASS = ClassName.get("java.lang", "Class");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");
    public static final ClassName FRAGMENT_V4 = ClassName.get("android.support.v4.app", "Fragment");
    public static final ClassName FRAGMENT = ClassName.get("android.app", "Fragment");
    public static final ClassName PARCELABLE = ClassName.get("android.os", "Parcelable");
}
