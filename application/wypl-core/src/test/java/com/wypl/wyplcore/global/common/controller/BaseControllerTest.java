package com.wypl.wyplcore.global.common.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.wypl.wyplcore.global.annotation.ControllerTest;

@ControllerTest
@WebMvcTest(this.controllers)
public class BaseControllerTest {
	Class<?> controllers;
}

