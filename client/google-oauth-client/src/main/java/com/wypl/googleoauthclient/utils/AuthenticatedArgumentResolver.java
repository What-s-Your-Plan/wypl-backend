package com.wypl.googleoauthclient.utils;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wypl.googleoauthclient.annotation.Authenticated;
import com.wypl.googleoauthclient.service.AuthMemberService;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.googleoauthclient.exception.GoogleOAuthErrorCode;
import com.wypl.googleoauthclient.exception.GoogleOAuthException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticatedArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private final AuthMemberService authMemberService;

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
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		validateAuthorization(request);

		String accessToken = (Objects.requireNonNull(request)).getHeader(AUTHORIZATION_HEADER);
		return authMemberService.getValidatedMemberId(accessToken);
	}

	private void validateAuthorization(HttpServletRequest request) {
		if (Objects.requireNonNull(request).getHeader(AUTHORIZATION_HEADER) == null) {
			throw new GoogleOAuthException(GoogleOAuthErrorCode.NOT_AUTHORIZATION_MEMBER);
		}
	}
}
