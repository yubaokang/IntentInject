# IntentInject
A easy API to transmit bundle at activity and fragment

###引用：

#### top-level build.gradle
```java
dependencies {
    classpath 'com.android.tools.build:gradle:2.2.0'
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8' 
}
```

#### module build.gradle
```java
apply plugin: 'android-apt'

dependencies {
    compile 'com.yubaokang:intentinject-api:v1.0.0'
    apt 'com.yubaokang:intentinject-compiler:v1.0.0'
}
```

###如何使用 --类似于ButterKnife:

#### 在Activity使用:
```java
public class MainActivity extends AppCompatActivity {
@Extra("name")
String name;
    @Extra("age")
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

记住一定要执行编译，否者无法生成MainActivity_Intent类

```java
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //传值并且跳转到
        MainActivity_Intent.intent(this).name("yubaokang").age(25).price(1.2f).id("idididid").start();
    }
}
```

#### 在Fragment使用，相对于在Activity中的注解，前面都加上Arg
```java
public class BlankFragment1 extends Fragment {
    @ArgExtra("name")
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
public class TabActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        BlankFragment1_Intent.builder().name("yubaokang").age("25").build();
    }
```
