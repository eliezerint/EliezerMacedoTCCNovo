package br.grupointegrado.appmetaforadevenda.Util;

import android.content.Context;
import android.widget.Toast;

import java.lang.annotation.Annotation;

import eu.inmite.android.lib.validations.form.iface.IValidator;

/**
 * Created by eli on 14/10/2015.
 */
public class CustomValida implements IValidator {
    @Override
    public boolean validate(Annotation annotation, Object o) {
        System.out.print(o+"");

        return false;
    }

    @Override
    public String getMessage(Context context, Annotation annotation, Object o) {
        return null;
    }

    @Override
    public int getOrder(Annotation annotation) {
        return 0;
    }
}
