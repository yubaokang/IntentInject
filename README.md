# IntentInject

A easy API of transmit bundle in activity or fragment

[中文文档](https://github.com/yubaokang/IntentInject/blob/master/README-ZH.md)

### dependencies：

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
     compile  'com.github.yubaokang.IntentInject:intentinject-api:1.0.2'
     annotationProcessor  'com.github.yubaokang.IntentInject:intentinject-compiler:1.0.2'
}
```

### how to use -- just like ButterKnife:

#### in Activity:
```java
public class MainActivity extends AppCompatActivity {
    @Extra
    String name;//"name"is the default key
    @Extra
    int age;
    
    @Extra("price")
    float price;
    @Extra("dou")
    double dou;
    @Extra("test")//Test need to implements Parcelable or Serializable
    Test test;
    
    @ExtraArrayString("datas")
    ArrayList<String> datas;
    @ExtraArrayParcelable("tests")
    ArrayList<Test> tests;
    @ExtraArrayInt("ints")
    ArrayList<Integer> ints;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentInject.inject(this);//add this line code
        Toast.makeText(this, "name:" + name, Toast.LENGTH_LONG).show();
    }
}
```

Remember building the project to generate MainActivity_Builder class

```java
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //transmit value and open MainActivity
        MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").start();
        //MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").startActivityForResult(111);//使用startActivityForResult

        //custom
        //Intent intent=MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").getIntent();
        //startActivity(intent);
    }
}
```

#### in Fragment -- relative to Activity，just need to add "Arg"
```java
public class BlankFragment1 extends Fragment {
    @ArgExtra
    String name;
    @ArgExtra("age")
    int age;
    @ArgExtraArrayString("datas")
    ArrayList<String> datas;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       IntentInject.inject(this);//add this line code
       Toast.makeText(this, "name:" + name, Toast.LENGTH_LONG).show();
    }
}
```

create Fragment
```java
    BlankFragment1_Builder.builder().name("yubaokang").age("25").build();
```
