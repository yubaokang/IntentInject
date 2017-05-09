# IntentInject

实现Activity或者Fragment 快速传递Bundle

[English doc](https://github.com/yubaokang/IntentInject/blob/master/README.md)

###引用：

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

###如何使用 --类似于ButterKnife:

#### 在Activity使用:
```java
public class MainActivity extends AppCompatActivity {
    @Extra
    String name;//这种情况"name"就是默认的key
    @Extra
    int age;
    
    @Extra("price")
    float price;
    @Extra("dou")
    double dou;
    @Extra("test")//Test 需要实现序列化 Parcelable或者Serializable都可以
    Test test;
    
    @ExtraArrayString("datas")
    ArrayList<String> datas;
    @ExtraArrayParcelable("tests")//Test 需要实现序列化 Parcelable或者Serializable都可以
    ArrayList<Test> tests;
    @ExtraArrayInt("ints")
    ArrayList<Integer> ints;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentInject.inject(this);//添加这行代码
        Toast.makeText(this, "name:" + name, Toast.LENGTH_LONG).show();
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
        MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").start();
        //MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").startActivityForResult(111);//使用startActivityForResult

        //自定义
        //Intent intent=MainActivity_Builder.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").getIntent();
        //startActivity(intent);
    }
}
```

#### 在Fragment使用,相对于在Activity中的注解，前面都加上Arg
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
       IntentInject.inject(this);//添加这行代码
       Toast.makeText(this, "name:" + name, Toast.LENGTH_LONG).show();
    }
}
```

创建Fragment
```java
    BlankFragment1_Builder.builder().name("yubaokang").age("25").build();
```
