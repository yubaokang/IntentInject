package com.ybk.intent.inject.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yubao on 2016/10/9.
 */
public class BaseBundleBuilder<I> {
    protected I i;
    protected Intent intent;
    protected Bundle bundle;

    public BaseBundleBuilder(I i, Class<?> clazz) {
        this.i = i;
        intent = new Intent((Context) i, clazz);
        bundle = new Bundle();
    }

    public BaseBundleBuilder(I i) {
        this.i = i;
        bundle = new Bundle();
    }

    public I extra(String name, boolean value) {
        bundle.putBoolean(name, value);
        return i;
    }

    public I extra(String name, byte value) {
        bundle.putByte(name, value);
        return i;
    }

    public I extra(String name, char value) {
        bundle.putChar(name, value);
        return i;
    }

    public I extra(String name, short value) {
        bundle.putShort(name, value);
        return i;
    }

    public I extra(String name, int value) {
        bundle.putInt(name, value);
        return i;
    }

    public I extra(String name, long value) {
        bundle.putLong(name, value);
        return i;
    }

    public I extra(String name, float value) {
        bundle.putFloat(name, value);
        return i;
    }

    public I extra(String name, double value) {
        bundle.putDouble(name, value);
        return i;
    }

    public I extra(String name, String value) {
        bundle.putString(name, value);
        return i;
    }

    public I extra(String name, CharSequence value) {
        bundle.putCharSequence(name, value);
        return i;
    }

    public I extra(String name, Parcelable value) {
        bundle.putParcelable(name, value);
        return i;
    }

    public I extra(String name, Parcelable[] value) {
        bundle.putParcelableArray(name, value);
        return i;
    }

    public I putParcelableArrayList(String name, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(name, value);
        return i;
    }

    public I putIntegerArrayList(String name, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(name, value);
        return i;
    }

    public I putStringArrayList(String name, ArrayList<String> value) {
        bundle.putStringArrayList(name, value);
        return i;
    }

    public I extra(String name, Serializable value) {
        bundle.putSerializable(name, value);
        return i;
    }

    public I extra(String name, boolean[] value) {
        bundle.putBooleanArray(name, value);
        return i;
    }

    public I extra(String name, byte[] value) {
        bundle.putByteArray(name, value);
        return i;
    }

    public I extra(String name, short[] value) {
        bundle.putShortArray(name, value);
        return i;
    }

    public I extra(String name, char[] value) {
        bundle.putCharArray(name, value);
        return i;
    }

    public I extra(String name, int[] value) {
        bundle.putIntArray(name, value);
        return i;
    }

    public I extra(String name, long[] value) {
        bundle.putLongArray(name, value);
        return i;
    }

    public I extra(String name, float[] value) {
        bundle.putFloatArray(name, value);
        return i;
    }

    public I extra(String name, double[] value) {
        bundle.putDoubleArray(name, value);
        return i;
    }

    public I extra(String name, String[] value) {
        bundle.putStringArray(name, value);
        return i;
    }

    public I extra(String name, Bundle value) {
        bundle.putBundle(name, value);
        return i;
    }
}
