package com.ybk.intent.inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ybk.intent.inject.compiler.annotation.Extra;
import com.ybk.intent.inject.compiler.annotation.ExtraArrayInt;
import com.ybk.intent.inject.compiler.annotation.ExtraArrayParcelable;
import com.ybk.intent.inject.compiler.annotation.ExtraArrayString;
import com.ybk.intent.inject.api.IntentInject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;
    @Extra
    String id;

    @Extra("aa")
    int aa;

    @Extra("bb")
    float bb;

    @Extra("dou")
    double dou;

    @Extra("test")
    Test test;

    @ExtraArrayString
    ArrayList<String> datas;

    @ExtraArrayParcelable("tests")
    ArrayList<Test> tests;

    @ExtraArrayInt("ints")
    ArrayList<Integer> ints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        IntentInject.inject(this);
        Toast.makeText(this, "id:" + id +
                        "\naa:" + aa +
                        "\nbb:" + bb +
                        "\ndou:" + dou
//                "\ntest:" + test.getName() +
//                "\ndatas:" + datas.get(0) +
//                "\ntests:" + tests.get(0).getName() +
//                "\nints:" + ints.get(0)
                , Toast.LENGTH_LONG).show();
        ArrayList<String> lists = new ArrayList<>();
        lists.add("fragment");
        BlankFragment1 blankFragment1 = BlankFragment1_Builder.builder().param1("a").param2("a2").list(lists).build();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main, blankFragment1).commit();
    }
}
