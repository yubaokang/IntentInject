package com.ybk.intent.inject;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ybk.intent.inject.annotation.Extra;
import com.ybk.intent.inject.api.IntentInject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlankFragment1 extends BaseFragment {
    @BindView(R.id.tv)
    TextView tv;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank_fragment1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        tv.setText(toString());
        Toast.makeText(getActivity(), "this is a Fragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String toString() {
        return "BlankFragment1{" +
                ", bundle=" + bundle +
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
                '}';
    }
}
