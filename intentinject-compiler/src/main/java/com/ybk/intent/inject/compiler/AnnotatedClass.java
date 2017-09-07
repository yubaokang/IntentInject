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
    public List<ExtraField> extras = new ArrayList<>();
    public List<ArgExtraField> argExtras = new ArrayList<>();
    public Elements mElementUtils;

    public static final int HOST_TYPE_ACTIVITY = 1;//注解的宿主类型：Activity
    public static final int HOST_TYPE_FRAGMENT = 2;//注解的宿主类型：Fragment
    private int hostType = 0;//注解的宿主类型

    public AnnotatedClass(TypeElement classElement, Elements elementUtils, int hostType) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
        this.hostType = hostType;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    public void addField(ExtraField field) {
        extras.add(field);
    }

    public void addArgField(ArgExtraField field) {
        argExtras.add(field);
    }

    public JavaFile generateExtras() {
        if (hostType == HOST_TYPE_ACTIVITY) {
            return generateExtrasActivity();//生成代码Activity_Builder
        } else if (hostType == HOST_TYPE_FRAGMENT) {
            return generateExtrasFragment();//生成代码Fragment_Builder
        } else {
            return null;
        }
    }

    /**
     * @return 生成 xxx_Builder
     */
    public JavaFile generateExtrasActivity() {
        List<MethodSpec.Builder> methods = new ArrayList<>();
        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
        //注入 inject
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL);

        injectMethodBuilder.addCode(
                "$T intent=host.getIntent();\n" +
                        "if(intent != null){\n" +
                        "\t$T bundle=intent.getExtras();\n" +
                        "\tif(bundle != null){\n", TypeUtil.INTENT, TypeUtil.BUNDLE);

        for (ExtraField field : extras) {
            // TODO: 2017/9/5 暂时不支持Parcelable对象数组 Test实现Parcelable
//            String type = field.getFieldType().toString();
//            VariableElement element = field.getmFieldElement();
//            try {
//                List<com.sun.tools.javac.code.Type> types = ((com.sun.tools.javac.code.Type.ClassType) ((com.sun.tools.javac.code.Type.ArrayType) element.asType()).elemtype).all_interfaces_field;
//                for (com.sun.tools.javac.code.Type t : types) {
//                    if (t.toString().equals("android.os.Parcelable")) {
//                        injectMethodBuilder.addCode("\t\tif (bundle.containsKey($S)) ", field.getKey());
//                        injectMethodBuilder.addCode("host.$N = ($T)bundle.getParcelableArray($S);\n", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
//                        break;
//                    }
//                }
//            } catch (Exception e) {
            injectMethodBuilder.addCode("\t\tif (bundle.containsKey($S)) ", field.getKey());
            injectMethodBuilder.addCode("host.$N = ($T)bundle.get($S);\n", field.getFieldName(), TypeName.get(field.getFieldType()), field.getKey());
//            }
        }

        injectMethodBuilder.addCode("\t\t}\n" + "\t}\n");

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

        /////////////////////////////////////////////////////////////////////
        //extras
        for (ExtraField field : extras) {
            MethodSpec.Builder key = MethodSpec.methodBuilder(field.getKey())
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

        TypeSpec.Builder inner = TypeSpec.classBuilder("Builder")
                .superclass(TypeUtil.INTENT_BUILDER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        for (MethodSpec.Builder method : methods) {
            inner.addMethod(method.build());
        }

        TypeSpec.Builder outter = TypeSpec.classBuilder(mClassElement.getSimpleName() + "_Builder")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJECT, TypeName.get(mClassElement.asType())))
                .addType(inner.build());
        outter.addMethod(injectMethodBuilder.build());
        outter.addMethod(injectIntent.build());

        TypeSpec finderClass = outter.build();

        return JavaFile.builder(packageName, finderClass).build();
    }

    public JavaFile generateExtrasFragment() {
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
        for (ArgExtraField field : argExtras) {
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
        for (ArgExtraField field : argExtras) {
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
