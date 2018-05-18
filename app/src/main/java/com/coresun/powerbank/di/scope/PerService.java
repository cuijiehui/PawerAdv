package com.coresun.powerbank.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Android on 2017/9/12.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}
