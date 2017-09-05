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

public class ArgAnnotatedClass {

    public TypeElement mClassElement;
    public List<ArgExtraField> extras;
    public Elements mElementUtils;

    public ArgAnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.extras = new ArrayList<>();
        this.mElementUtils = elementUtils;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    public void addField(ArgExtraField field) {
        extras.add(field);
    }

    /**
     * @return 生成 xxx_Builder
     */
    public JavaFile generateExtras() {
        List<MethodSpec.Builder> methods = new ArrayList<>();
        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
        //inject
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL);
        injectMethodBuilder.addCode(
                "$T bundle=host.getArguments();\n" +
                        "if(bundle != null){\n", TypeUtil.BUNDLE);
        for (ArgExtraField field : extras) {
            injectMethodBuilder.addCode("\tif(bundle.containsKey($S)) ", field.getKey());
            injectMethodBuilder.addCode("host.$N = ($T)bundle.get($S);\n", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
        }
        injectMethodBuilder.addCode("}\n");

        //builder
        MethodSpec.Builder builder = MethodSpec.methodBuilder("builder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", "Builder"))
                .addStatement("return new Builder(new $T())", mClassElement.asType());

        //Builder
        MethodSpec.Builder injectConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mClassElement.asType()), "fragment")
                .addStatement("super(fragment)");
        methods.add(injectConstructor);

        //extras
        for (ArgExtraField field : extras) {
            MethodSpec.Builder key = MethodSpec.methodBuilder(String.valueOf(field.getFieldName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(field.getFieldType()), field.getKey())
                    .returns(ClassName.get("", "Builder"));

            if ("java.util.ArrayList<java.lang.String>".equals(field.getFieldType().toString())) {
                key.addStatement("super.putStringArrayList($S,$L)", field.getKey(), field.getKey());
            } else if ("java.util.ArrayList<java.lang.Integer>".equals(field.getFieldType().toString())) {
                key.addStatement("super.putIntegerArrayList($S,$L)", field.getKey(), field.getKey());
            } else if (field.getFieldType().toString().startsWith("java.util.ArrayList<")) {
                key.addStatement("super.putParcelableArrayList($S,$L)", field.getKey(), field.getKey());
            } else {
                key.addStatement("super.extra($S,$L)", field.getKey(), field.getKey());
            }

            key.addStatement("return this");
            methods.add(key);
        }

        //Builder inner class
        TypeSpec.Builder inner = TypeSpec.classBuilder("Builder")
                .superclass(ParameterizedTypeName.get(TypeUtil.FRAGMENT_BUILDER, TypeName.get(mClassElement.asType())))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        for (MethodSpec.Builder method : methods) {
            inner.addMethod(method.build());
        }

        //outter class
        TypeSpec.Builder outter = TypeSpec.classBuilder(mClassElement.getSimpleName() + "_Builder")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJECT, TypeName.get(mClassElement.asType())))
                .addType(inner.build());//add inner class
        outter.addMethod(injectMethodBuilder.build());
        outter.addMethod(builder.build());

        TypeSpec finderClass = outter.build();
        return JavaFile.builder(packageName, finderClass).build();
    }
}
