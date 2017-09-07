# IntentInject

## Version 1.3.1 更新说明：
* 只保留Extra注解字段，在Activity和Fragment中同样的使用
* 支持原生Bundle传值的所有类型（除了数组对象不支持，例如：Test[] tests; Test实现Parcelable序列化接口，这种情况暂时不支持）
* 升级：将原来的ArgExtra，ArgExtraXXX等注解全部换成Extra


###实现Activity或者Fragment 快速传递Bundle

###使用：
#### top-level build.gradle
```java
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

#### module build.gradle
```java

dependencies {
    ...
    compile  'com.github.yubaokang.IntentInject:intentinject-api:1.3.1'
    annotationProcessor  'com.github.yubaokang.IntentInject:intentinject-compiler:1.3.1'
}
```

###如何使用 --类似于ButterKnife:

#### 在Activity使用:
```java
public class MainActivity extends AppCompatActivity {
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
    ArrayList<String> arrayListString;
    @Extra
    ArrayList<Test> arrayListTest;
    @Extra
    ArrayList<Integer> arrayListInteger;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentInject.inject(this);//添加这行代码
        Toast.makeText(this, "aFloat:" + aFloat, Toast.LENGTH_LONG).show();
    }
}
```

记住一定要执行编译，否者无法生成MainActivity_Builder类

```java
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //传值并且跳转到
       MainActivity_Builder.intent(this)
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
                      .arrayListString((ArrayList<String>) stringList)
                      .arrayListTest((ArrayList<Test>) testArray)
                      .arrayListInteger((ArrayList<Integer>) intArray)
                      .start();
        //MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").startActivityForResult(111);//使用startActivityForResult

        //自定义
        //Intent intent=MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").getIntent();
        //startActivity(intent);
    }
}
```

#### 在Fragment使用
```java
public class BlankFragment1 extends Fragment {
    @Extra
    float aFloat;
    @Extra
    float[] floats;
    @Extra
    boolean aBoolean;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       IntentInject.inject(this);//添加这行代码
       Toast.makeText(this, "aFloat:" + aFloat, Toast.LENGTH_LONG).show();
    }
}
```

创建Fragment
```java
 BlankFragment1 blankFragment1 = BlankFragment1_Builder.builder()
                .aFloat(1.1f)
                .floats(floats)
                .aBoolean(true)
                .build();
```
