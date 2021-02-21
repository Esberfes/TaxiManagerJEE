package com.taxi.utils;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Qualifier
@Target({FIELD, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaxiLoggerProducer {
}
