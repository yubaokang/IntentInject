package com.ybk.intent.inject.compiler;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.code.Type;
import com.ybk.intent.inject.annotation.ArgExtra;
import com.ybk.intent.inject.annotation.Extra;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ViewFinderProcesser extends AbstractProcessor {

    private Filer mFiler; //文件相关的辅助类
    private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    /**
     * @return 指定哪些注解应该被注解处理器注册
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Extra.class.getCanonicalName());
//        types.add(ArgExtra.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();
        try {
            // TODO: 2017/9/7
            processIntentKey(roundEnv);
//            processIntentKeyOldActivity(roundEnv);
//            processIntentKeyOldFragment(roundEnv);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            return true;
        }
        for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                annotatedClass.generateExtras().writeTo(mFiler);
            } catch (IOException e) {
                return true;
            }
        }
        return true;
    }

    // TODO: 2017/9/7 只是用Extra 区分Activity和Fragment 无法导入 com.sun.tools.javac.code. 包
    private void processIntentKey(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(Extra.class)) {
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            Type.ClassType classType = getHostType((Type.ClassType) classElement.asType());
            if (classType.supertype_field.toString().equals("android.app.Activity")
                    || classType.supertype_field.toString().equals("android.support.v7.app.AppCompatActivity")
                    || classType.supertype_field.toString().equals("android.support.v4.app.FragmentActivity")) {
                AnnotatedClass annotatedClass = getAnnotatedClass(element, AnnotatedClass.HOST_TYPE_ACTIVITY);
                ExtraField field = new ExtraField(element);
                annotatedClass.addField(field);
            } else if (classType.supertype_field.toString().equals("android.support.v4.app.Fragment")
                    || classType.supertype_field.toString().equals("android.app.Fragment")) {
                AnnotatedClass annotatedClass = getAnnotatedClass(element, AnnotatedClass.HOST_TYPE_FRAGMENT);
                ExtraField field = new ExtraField(element);
                annotatedClass.addField(field);
            }
        }
    }

    //activity
    private void processIntentKeyOldActivity(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(Extra.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element, AnnotatedClass.HOST_TYPE_ACTIVITY);
            ExtraField field = new ExtraField(element);
            annotatedClass.addField(field);
        }
    }

    //fragment
    private void processIntentKeyOldFragment(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(ArgExtra.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element, AnnotatedClass.HOST_TYPE_FRAGMENT);
            ArgExtraField field = new ArgExtraField(element);
//            annotatedClass.addArgField(field);
        }
    }

    //获取类型
    private Type.ClassType getHostType(Type.ClassType classType) {
        if (classType == null) {
            throw new RuntimeException("@Extra只能使用在Activity中或者Fragment中");
        }
        if (classType.supertype_field.toString().equals("android.app.Activity")
                || classType.supertype_field.toString().equals("android.support.v7.app.AppCompatActivity")
                || classType.supertype_field.toString().equals("android.support.v4.app.FragmentActivity")) {
            return classType;
        } else if (classType.supertype_field.toString().equals("android.support.v4.app.Fragment")
                || classType.supertype_field.toString().equals("android.app.Fragment")) {
            return classType;
        } else {
            return getHostType((Type.ClassType) classType.supertype_field);
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element, int hostType) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(classElement, mElementUtils, hostType);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
