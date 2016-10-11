package com.ybk.intent.inject.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class AnnotatedClass {

    public TypeElement mClassElement;
    public List<ExtraField> extras;
    public List<ExtraArrayStringField> extrasArrayStr;
    public List<ExtraArrayIntField> extrasArrayInt;
    public List<ExtraArrayParcelableField> extrasArrayPar;
    public Elements mElementUtils;

    public AnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.extras = new ArrayList<>();
        this.extrasArrayStr = new ArrayList<>();
        this.extrasArrayInt = new ArrayList<>();
        this.extrasArrayPar = new ArrayList<>();
        this.mElementUtils = elementUtils;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    public void addField(ExtraField field) {
        extras.add(field);
    }

    public void addField(ExtraArrayStringField field) {
        extrasArrayStr.add(field);
    }

    public void addField(ExtraArrayIntField field) {
        extrasArrayInt.add(field);
    }

    public void addField(ExtraArrayParcelableField field) {
        extrasArrayPar.add(field);
    }

    /**
     * @return 生成Extras类MainActivity_Intent
     */
    public JavaFile generateExtras() {
        List<MethodSpec.Builder> methods = new ArrayList<>();
        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
        //注入 inject
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL);

        injectMethodBuilder.addCode(
                "$T intent=host.getIntent();\n" +
                        "   if(intent != null){\n" +
                        "       $T bundle=intent.getExtras();\n" +
                        "       if(bundle != null){\n", TypeUtil.INTENT, TypeUtil.BUNDLE);
        for (ExtraField field : extras) {
            injectMethodBuilder.addStatement("          host.$N = ($T)bundle.get($S)", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
        }
        for (ExtraArrayStringField field : extrasArrayStr) {
            injectMethodBuilder.addStatement("          host.$N = ($T)bundle.get($S)", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
        }
        for (ExtraArrayIntField field : extrasArrayInt) {
            injectMethodBuilder.addStatement("          host.$N = ($T)bundle.get($S)", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
        }
        for (ExtraArrayParcelableField field : extrasArrayPar) {
            injectMethodBuilder.addStatement("          host.$N = ($T)bundle.get($S)", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
        }
        injectMethodBuilder.addCode("       }\n" + "   }\n");

        //intent
        MethodSpec.Builder injectIntent = MethodSpec.methodBuilder("intent")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(TypeUtil.CONTEXT, "context")
                .returns(ClassName.get("", "Builder"))
                .addStatement("return new Builder(context)");

        MethodSpec.Builder injectConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeUtil.CONTEXT, "context")
                .addStatement("super(context,$L)", mClassElement.getSimpleName() + ".class");
        methods.add(injectConstructor);

        //extras
        for (ExtraField field : extras) {
            MethodSpec.Builder key = MethodSpec.methodBuilder(String.valueOf(field.getFieldName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(field.getFieldType()), String.valueOf(field.getFieldName()))
                    .returns(ClassName.get("", "Builder"))
                    .addStatement("super.extra($S,$L)", field.getFieldName().toString(), field.getFieldName().toString())
                    .addStatement("return this");
            methods.add(key);
        }
        for (ExtraArrayStringField field : extrasArrayStr) {
            MethodSpec.Builder key = MethodSpec.methodBuilder(String.valueOf(field.getFieldName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(field.getFieldType()), String.valueOf(field.getFieldName()))
                    .returns(ClassName.get("", "Builder"))
                    .addStatement("super.putStringArrayList($S,$L)", field.getFieldName().toString(), field.getFieldName().toString())
                    .addStatement("return this");
            methods.add(key);
        }
        for (ExtraArrayIntField field : extrasArrayInt) {
            MethodSpec.Builder key = MethodSpec.methodBuilder(String.valueOf(field.getFieldName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(field.getFieldType()), String.valueOf(field.getFieldName()))
                    .returns(ClassName.get("", "Builder"))
                    .addStatement("super.putIntegerArrayList($S,$L)", field.getFieldName().toString(), field.getFieldName().toString())
                    .addStatement("return this");
            methods.add(key);
        }
        for (ExtraArrayParcelableField field : extrasArrayPar) {
            MethodSpec.Builder key = MethodSpec.methodBuilder(String.valueOf(field.getFieldName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(field.getFieldType()), String.valueOf(field.getFieldName()))
                    .returns(ClassName.get("", "Builder"))
                    .addStatement("super.putParcelableArrayList($S,$L)", field.getFieldName().toString(), field.getFieldName().toString())
                    .addStatement("return this");
            methods.add(key);
        }

        TypeSpec.Builder inner = TypeSpec.classBuilder("Builder")
                .superclass(TypeUtil.INTENT_BUILDER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        for (MethodSpec.Builder method : methods) {
            inner.addMethod(method.build());
        }

        TypeSpec.Builder outter = TypeSpec.classBuilder(mClassElement.getSimpleName() + "_Intent")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJECT, TypeName.get(mClassElement.asType())))
                .addType(inner.build());
        outter.addMethod(injectMethodBuilder.build());
        outter.addMethod(injectIntent.build());

        TypeSpec finderClass = outter.build();

        return JavaFile.builder(packageName, finderClass).build();
    }
}
