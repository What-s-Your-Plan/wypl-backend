package com.wypl.wyplcore.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.test.context.ContextConfiguration;

import com.wypl.wyplcore.WyplCoreTestApplication;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureRestDocs
@ContextConfiguration(classes = WyplCoreTestApplication.class)
public @interface ControllerTest {
}