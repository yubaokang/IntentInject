package com.ybk.intent.inject.compiler;

import com.ybk.intent.inject.compiler.annotation.ExtraArrayString;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by brucezz on 2016-07-25.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class ExtraArrayStringField {
    private VariableElement mFieldElement;
    private String key;

    public ExtraArrayStringField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", ExtraArrayString.class.getSimpleName()));
        }

        mFieldElement = (VariableElement) element;
        ExtraArrayString extra = mFieldElement.getAnnotation(ExtraArrayString.class);
        key = extra.value();

//        if (mResId == null) {
//            throw new IllegalArgumentException(String.format("value() in %s for field %s is not valid !", BindView.class.getSimpleName(), mFieldElement.getSimpleName()));
//        }
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
