package com.ybk.intent.inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    void click() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("datas");
        ArrayList<Test> tests = new ArrayList<>();
        Test test = new Test("testName");
        tests.add(test);
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(11);
        MainActivity_Intent.intent(this)
                .aa(111)
                .bb(1.2f)
                .dou(1.2)
                .id("idididid")
                .test(test)
                .datas(datas)
                .tests(tests)
                .ints(ints)
                .start();
    }
}
