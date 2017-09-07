package com.ybk.intent.inject;

import android.os.Binder;
import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    void click() {

        float[] floats = {1.0f, 2.0f, 3.0f};
        boolean[] booleen = {true, false, true};
        String[] strings = {"str1", "str2", "str3"};
        short short1 = 1;
        byte byte1 = 1;
        byte[] bytes = {1, 2, 3};
        double[] doubles = {1.1111, 2.2222, 3.3333};
        Test[] tests = {new Test("11111"), new Test("22222"), new Test("33333")};
        long[] longs = {1111111111L, 22222222222L, 33333333333L};

        int[] ints = {1111111, 222222, 333333};
        short[] shorts = {1, 2, 3};
        char[] chars = {'a', 'b', 'c'};
        CharSequence cs = "CharSequence";
        CharSequence[] css = {"css1", "css2", "css3"};

        Double[] ddoubles = {1111111.1111, 222222.111, 333333.111};

        SparseArray<Test> sparseArray = new SparseArray<>();
        sparseArray.put(111, new Test("111"));
        sparseArray.put(222, new Test("222"));
        sparseArray.put(333, new Test("333"));

        List<String> stringList = new ArrayList<>();
        stringList.add("11111");
        stringList.add("2222");
        stringList.add("3333");

        List<Test> testArray = new ArrayList<>();
        testArray.add(new Test("array1"));
        testArray.add(new Test("array2"));
        testArray.add(new Test("array3"));

        List<Integer> intArray = new ArrayList<>();
        intArray.add(11111111);
        intArray.add(22222222);
        intArray.add(33333333);


        SerialzableTest[] serialzableTests = {new SerialzableTest("sss"), new SerialzableTest("222")};
        MainActivity_Builder.intent(this)
                .bundle(new Bundle())
                .aFloat(1.1f)
                .floats(floats)
                .aBoolean(true)
                .booleen(booleen)
                .parcelable(new Test("test Name"))
                .aShort(short1)
                .shorts(shorts)
                .string("string")
                .strings(strings)
                .aByte(byte1)
                .bytes(bytes)
                .aDouble(1.2)
                .DDaouble(1.3)
                .DDoubles(ddoubles)
                .doubles(doubles)
                .serialzableTest(new SerialzableTest("SerialzableTest"))
                .serialzableTests(serialzableTests)
//                .aaaaaaaaaaaaaaaa(tests)
                .aLong(11111111111L)
                .ALong(222222222222L)
                .longs(longs)
                .anInt(11111111)
                .ints(ints)
                .aChar('1')
                .chars(chars)
                .charSequence(cs)
                .charSequences(css)
                .sparseArray(sparseArray)
//                .size(new Size(123, 123))
//                .sizeF(new SizeF(222, 222))
                .iBinder(new Binder())
                .arrayListString((ArrayList<String>) stringList)
                .arrayListTest((ArrayList<Test>) testArray)
                .arrayListInteger((ArrayList<Integer>) intArray)
                .start();
    }
}
