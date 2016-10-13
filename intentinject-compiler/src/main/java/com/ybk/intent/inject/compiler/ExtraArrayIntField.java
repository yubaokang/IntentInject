package com.ybk.intent.inject.compiler;


import com.ybk.intent.inject.annotation.ExtraArrayInt;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class ExtraArrayIntField {
    private VariableElement mFieldElement;
    private String key;

    public ExtraArrayIntField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", ExtraArrayInt.class.getSimpleName()));
        }

        mFieldElement = (VariableElement) element;
        ExtraArrayInt extra = mFieldElement.getAnnotation(ExtraArrayInt.class);
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
