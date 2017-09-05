package com.ybk.intent.inject;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by ybk on 2017/9/5.
 */

public class TTTTT {
    public static void inject(final MainActivity host) {
        Intent intent = host.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("bundle")) host.bundle = (Bundle)bundle.get("bundle");
                if (bundle.containsKey("aFloat")) host.aFloat = (float)bundle.get("aFloat");
                if (bundle.containsKey("floats")) host.floats = (float[])bundle.get("floats");
                if (bundle.containsKey("aBoolean")) host.aBoolean = (boolean)bundle.get("aBoolean");
                if (bundle.containsKey("booleen")) host.booleen = (boolean[])bundle.get("booleen");
                if (bundle.containsKey("parcelable")) host.parcelable = (Test)bundle.get("parcelable");
                if (bundle.containsKey("aShort")) host.aShort = (short)bundle.get("aShort");
                if (bundle.containsKey("shorts")) host.shorts = (short[])bundle.get("shorts");
                if (bundle.containsKey("string")) host.string = (String)bundle.get("string");
                if (bundle.containsKey("strings")) host.strings = (String[])bundle.get("strings");
                if (bundle.containsKey("aByte")) host.aByte = (byte)bundle.get("aByte");
                if (bundle.containsKey("bytes")) host.bytes = (byte[])bundle.get("bytes");
                if (bundle.containsKey("aDouble")) host.aDouble = (double)bundle.get("aDouble");
                if (bundle.containsKey("DDaouble")) host.DDaouble = (Double)bundle.get("DDaouble");
                if (bundle.containsKey("doubles")) host.doubles = (double[])bundle.get("doubles");
                if (bundle.containsKey("serialzableTest")) host.serialzableTest = (SerialzableTest)bundle.get("serialzableTest");
                if (bundle.containsKey("aLong")) host.aLong = (long)bundle.get("aLong");
                if (bundle.containsKey("ALong")) host.ALong = (Long)bundle.get("ALong");
                if (bundle.containsKey("longs")) host.longs = (long[])bundle.get("longs");
                if (bundle.containsKey("anInt")) host.anInt = (int)bundle.get("anInt");
                if (bundle.containsKey("ints")) host.ints = (int[])bundle.get("ints");
                if (bundle.containsKey("aChar")) host.aChar = (char)bundle.get("aChar");
                if (bundle.containsKey("chars")) host.chars = (char[])bundle.get("chars");
                if (bundle.containsKey("charSequence")) host.charSequence = (CharSequence)bundle.get("charSequence");
                if (bundle.containsKey("charSequences")) host.charSequences = (CharSequence[])bundle.get("charSequences");
                if (bundle.containsKey("sparseArray")) host.sparseArray = (SparseArray<Test>)bundle.get("sparseArray");
                if (bundle.containsKey("size")) host.size = (Size)bundle.get("size");
                if (bundle.containsKey("sizeF")) host.sizeF = (SizeF)bundle.get("sizeF");
                if (bundle.containsKey("iBinder")) host.iBinder = (IBinder)bundle.get("iBinder");
                if (bundle.containsKey("arrayListString")) host.arrayListString = (ArrayList<String>)bundle.get("arrayListString");
                if (bundle.containsKey("arrayListTest")) host.arrayListTest = (ArrayList<Test>)bundle.get("arrayListTest");
                if (bundle.containsKey("arrayListInteger")) host.arrayListInteger = (ArrayList<Integer>)bundle.get("arrayListInteger");
//                if (bundle.containsKey("tests")) host.tests = (Test[]) bundle.getParcelableArray("tests");
                if (bundle.containsKey("DDoubles")) host.DDoubles = (Double[])bundle.get("DDoubles");
                if (bundle.containsKey("serialzableTests")) host.serialzableTests = (SerialzableTest[])bundle.get("serialzableTests");
            }
        }
    }
}
