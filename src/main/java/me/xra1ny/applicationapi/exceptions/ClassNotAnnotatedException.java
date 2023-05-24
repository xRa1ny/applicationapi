package me.xra1ny.applicationapi.exceptions;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public class ClassNotAnnotatedException extends RuntimeException {
    public ClassNotAnnotatedException(@NotNull Class<?> clazz, @NotNull Class<? extends Annotation> annotation) {
        super("class " + clazz + " is not annotated with " + annotation + "!");
    }
}
