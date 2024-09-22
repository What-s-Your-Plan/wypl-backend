package com.wypl.wyplcore.auth.utils;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wypl.wyplcore.auth.annotation.Authenticated;
import com.wypl.wyplcore.auth.domain.AuthMember;

@Component
public class AuthenticatedArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Authenticated.class);
		boolean assignableFrom = AuthMember.class.isAssignableFrom(parameter.getParameterType());
		return hasParameterAnnotation && assignableFrom;
	}

	@Override
	public AuthMember resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		return null;
	}
}
