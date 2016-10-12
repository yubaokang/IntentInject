package com.ybk.intent.inject.compiler;

import com.ybk.intent.inject.compiler.annotation.ExtraArrayParcelable;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class ExtraArrayParcelableField {
    private VariableElement mFieldElement;
    private String key;

    public ExtraArrayParcelableField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", ExtraArrayParcelable.class.getSimpleName()));
        }

        mFieldElement = (VariableElement) element;
        ExtraArrayParcelable extra = mFieldElement.getAnnotation(ExtraArrayParcelable.class);
        key = extra.value();

        if ("".equals(key)) {
            key = mFieldElement.getSimpleName().toString();
        }
    }

    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    public String getKey() {
        return key;
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }
}
