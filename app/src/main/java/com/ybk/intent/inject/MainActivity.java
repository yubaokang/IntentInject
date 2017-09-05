package com.ybk.intent.inject;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.widget.TextView;

import com.ybk.intent.inject.annotation.Extra;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView tv;
    // TODO: 2017/9/5 暂时不支持Parcelable对象数组 Test实现Parcelable
//    @Extra("aaaaaaaaaaaaaaaa")
//    Test[] tests;
    @Extra
    Bundle bundle;
    @Extra
    float aFloat;
    @Extra
    float[] floats;
    @Extra
    boolean aBoolean;
    @Extra
    boolean[] booleen;
    @Extra
    Test parcelable;
    @Extra
    short aShort;
    @Extra
    short[] shorts;
    @Extra
    String string;
    @Extra
    String[] strings;
    @Extra
    byte aByte;
    @Extra
    byte[] bytes;
    @Extra
    double aDouble;
    @Extra
    Double DDaouble;
    @Extra
    double[] doubles;
    @Extra
    Double[] DDoubles;
    @Extra
    SerialzableTest serialzableTest;//SerialzableTest implement Serializable
    @Extra
    SerialzableTest[] serialzableTests;//SerialzableTest implement Serializable
    @Extra
    long aLong;
    @Extra
    Long ALong;
    @Extra
    long[] longs;
    @Extra
    int anInt;
    @Extra
    int[] ints;
    @Extra
    char aChar;
    @Extra
    char[] chars;
    @Extra
    CharSequence charSequence;
    @Extra
    CharSequence[] charSequences;
    @Extra
    SparseArray<Test> sparseArray;
    @Extra
    Size size;
    @Extra
    SizeF sizeF;
    @Extra
    IBinder iBinder;
    @Extra
    ArrayList<String> arrayListString;
    @Extra
    ArrayList<Test> arrayListTest;
    @Extra
    ArrayList<Integer> arrayListInteger;
    ////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TTTTT.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<String> lists = new ArrayList<>();
        lists.add("fragment");
        BlankFragment1 blankFragment1 = BlankFragment1_Builder.builder().param1("a").param2("a2").list(lists).build();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main, blankFragment1).commit();
        tv.setText(toString());
    }

    @Override
    public String toString() {
        return "MainActivity{" +
                "bundle=" + bundle +
                ", aFloat=" + aFloat +
                ", floats=" + Arrays.toString(floats) +
                ", aBoolean=" + aBoolean +
                ", booleen=" + Arrays.toString(booleen) +
                ", parcelable=" + parcelable +
                ", aShort=" + aShort +
                ", shorts=" + Arrays.toString(shorts) +
                ", string='" + string + '\'' +
                ", strings=" + Arrays.toString(strings) +
                ", aByte=" + aByte +
                ", bytes=" + Arrays.toString(bytes) +
                ", aDouble=" + aDouble +
                ", DDaouble=" + DDaouble +
                ", doubles=" + Arrays.toString(doubles) +
                ", DDoubles=" + Arrays.toString(DDoubles) +
                ", serialzableTest=" + serialzableTest +
                ", serialzableTests=" + Arrays.toString(serialzableTests) +
                ", aLong=" + aLong +
                ", ALong=" + ALong +
                ", longs=" + Arrays.toString(longs) +
                ", anInt=" + anInt +
                ", ints=" + Arrays.toString(ints) +
                ", aChar=" + aChar +
                ", chars=" + Arrays.toString(chars) +
                ", charSequence=" + charSequence +
                ", charSequences=" + Arrays.toString(charSequences) +
                ", sparseArray=" + sparseArray +
                ", size=" + size +
                ", sizeF=" + sizeF +
                ", iBinder=" + iBinder +
                ", arrayListString=" + arrayListString +
                ", arrayListTest=" + arrayListTest +
                ", arrayListInteger=" + arrayListInteger +
//                ", tests=" + Arrays.toString(tests) +
                '}';
    }
}
