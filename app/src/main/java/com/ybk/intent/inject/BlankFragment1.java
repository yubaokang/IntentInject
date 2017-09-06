package com.ybk.intent.inject;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ybk.intent.inject.annotation.ArgExtra;
import com.ybk.intent.inject.api.IntentInject;

import java.util.ArrayList;

public class BlankFragment1 extends BaseFragment {
    @ArgExtra
    Bundle bundle;
    @ArgExtra
    float aFloat;
    @ArgExtra
    float[] floats;
    @ArgExtra
    boolean aBoolean;
    @ArgExtra
    boolean[] booleen;
    @ArgExtra
    Test parcelable;
    @ArgExtra
    short aShort;
    @ArgExtra
    short[] shorts;
    @ArgExtra
    String string;
    @ArgExtra
    String[] strings;
    @ArgExtra
    byte aByte;
    @ArgExtra
    byte[] bytes;
    @ArgExtra
    double aDouble;
    @ArgExtra
    Double DDaouble;
    @ArgExtra
    double[] doubles;
    @ArgExtra
    Double[] DDoubles;
    @ArgExtra
    SerialzableTest serialzableTest;//SerialzableTest implement Serializable
    @ArgExtra
    SerialzableTest[] serialzableTests;//SerialzableTest implement Serializable
    @ArgExtra
    long aLong;
    @ArgExtra
    Long ALong;
    @ArgExtra
    long[] longs;
    @ArgExtra
    int anInt;
    @ArgExtra
    int[] ints;
    @ArgExtra
    char aChar;
    @ArgExtra
    char[] chars;
    @ArgExtra
    CharSequence charSequence;
    @ArgExtra
    CharSequence[] charSequences;
    @ArgExtra
    SparseArray<Test> sparseArray;
    @ArgExtra
    Size size;
    @ArgExtra
    SizeF sizeF;
    @ArgExtra
    IBinder iBinder;
    @ArgExtra
    ArrayList<String> arrayListString;
    @ArgExtra
    ArrayList<Test> arrayListTest;
    @ArgExtra
    ArrayList<Integer> arrayListInteger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentInject.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank_fragment1, container, false);
    }
}
