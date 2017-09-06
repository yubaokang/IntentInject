package com.ybk.intent.inject.compiler;

import com.google.auto.service.AutoService;
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
        types.add(ArgExtra.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();
    private Map<String, ArgAnnotatedClass> frgClassMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();
        frgClassMap.clear();
        try {
            processIntentKey(roundEnv);
            processArgIntentKey(roundEnv);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            return true;
        }
        for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                info("Generating file for %s", annotatedClass.getFullClassName());
                annotatedClass.generateExtras().writeTo(mFiler);
            } catch (IOException e) {
                error("Generate file failed, reason: %s", e.getMessage());
                return true;
            }
        }
        for (ArgAnnotatedClass argAnnotatedClass : frgClassMap.values()) {
            try {
                info("Generating file for %s", argAnnotatedClass.getFullClassName());
                argAnnotatedClass.generateExtras().writeTo(mFiler);
            } catch (IOException e) {
                error("Generate file failed, reason: %s", e.getMessage());
                return true;
            }
        }
        return true;
    }

    //activity
    private void processIntentKey(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(Extra.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            ExtraField field = new ExtraField(element);
            annotatedClass.addField(field);
        }
    }

    //fragment
    private void processArgIntentKey(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(ArgExtra.class)) {
            ArgAnnotatedClass argAnnotatedClass = getArgAnnotatedClass(element);
            ArgExtraField argExtraField = new ArgExtraField(element);
            argAnnotatedClass.addField(argExtraField);
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(classElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    private ArgAnnotatedClass getArgAnnotatedClass(Element element) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        ArgAnnotatedClass argAnnotatedClass = frgClassMap.get(fullClassName);
        if (argAnnotatedClass == null) {
            argAnnotatedClass = new ArgAnnotatedClass(classElement, mElementUtils);
            frgClassMap.put(fullClassName, argAnnotatedClass);
        }
        return argAnnotatedClass;
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
