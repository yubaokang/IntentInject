package com.ybk.intent.inject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ybk.intent.inject.compiler.annotation.ArgExtra;
import com.ybk.intent.inject.compiler.annotation.ArgExtraArrayString;
import com.ybk.intent.inject.api.IntentInject;

import java.util.ArrayList;

public class BlankFragment1 extends Fragment {
    @ArgExtra
    String param1;
    @ArgExtra
    String param2;
    @ArgExtraArrayString("list")
    ArrayList<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentInject.inject(this);
        Toast.makeText(getActivity(), param1 + param2 + list.get(0), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank_fragment1, container, false);
    }
}
