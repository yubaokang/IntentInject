package com.ybk.intent.inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ybk.intent.inject.annotation.Extra;
import com.ybk.intent.inject.api.IntentInject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends BaseActivity {

    @Extra
    String  aa;
    @Extra
    Integer sss;

    @Extra
    Integer qqqqqq;

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
        MainActivity_Builder.intent(this)
                .aa(111)
                .dou(1.2)
                .id("idididid")
                .test(test)
                .datas(datas)
                .tests(tests)
                .ints(ints)
                .start();
    }
}
