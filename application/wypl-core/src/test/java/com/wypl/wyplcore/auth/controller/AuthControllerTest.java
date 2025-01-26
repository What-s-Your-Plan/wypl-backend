package com.wypl.wyplcore.auth.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.googleoauthclient.service.AuthMemberService;
import com.wypl.googleoauthclient.utils.AuthenticatedArgumentResolver;
import com.wypl.wyplcore.WyplCoreTestApplication;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;
import com.wypl.wyplcore.facade.AuthMemberFacade;

@AutoConfigureRestDocs
@ContextConfiguration(classes = WyplCoreTestApplication.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {
	private final String AUTHORIZATION_HEADER_VALUE = "Bearer oauth..";
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AuthenticatedArgumentResolver authenticatedArgumentResolver;
	@MockBean
	private AuthMemberFacade authMemberFacade;
	@MockBean
	private AuthMemberService authMemberService;

	@DisplayName("회원 가입 및 로그인한다.")
	@Test
	void signInTest() throws Exception {
		/* Given */
		AuthTokensResponse response = new AuthTokensResponse(0, "at", "rt");
		given(authMemberFacade.generateToken(any(String.class), any(String.class)))
			.willReturn(response);

		/* When */
		ResultActions actions = mockMvc.perform(
			RestDocumentationRequestBuilders.post(
					"/auth/v1/sign-in/{provider}?code={code}",
					"google",
					"dummy_code"
				)
				.contentType(MediaType.APPLICATION_JSON)
		);

		/* Then */
		actions.andDo(print())
			.andDo(document("auth/sign-in",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("provider").description("소셜 로그인 제공자")
				),
				queryParameters(
					parameterWithName("code").description("인증 코드")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING)
						.description("응답 메시지"),
					fieldWithPath("body.member_id").type(JsonFieldType.NUMBER)
						.description("회원 식별자"),
					fieldWithPath("body.access_token").type(JsonFieldType.STRING)
						.description("OAuth Access Token"),
					fieldWithPath("body.refresh_token").type(JsonFieldType.STRING)
						.description("OAuth Refresh Token")
				)
			))
			.andExpect(status().isOk());
	}

	@DisplayName("토큰을 재발급 한다.")
	@Test
	void reissueTest() throws Exception {
		/* Given */
		AuthTokensResponse response = new AuthTokensResponse(0, "at", "rt");
		given(authMemberFacade.reissueToken(any(String.class), any(String.class)))
			.willReturn(response);

		/* When */
		ResultActions actions = mockMvc.perform(
			RestDocumentationRequestBuilders.put(
					"/auth/v1/reissue?access_token={access_token}&refresh_token={refresh_token}",
					"access",
					"refresh"
				)
				.contentType(MediaType.APPLICATION_JSON)
		);

		/* Then */
		actions.andDo(print())
			.andDo(document("auth/reissue",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				queryParameters(
					parameterWithName("access_token").description("Access Token"),
					parameterWithName("refresh_token").description("Refresh Token")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING)
						.description("응답 메시지"),
					fieldWithPath("body.member_id").type(JsonFieldType.NUMBER)
						.description("회원 식별자"),
					fieldWithPath("body.access_token").type(JsonFieldType.STRING)
						.description("OAuth Access Token"),
					fieldWithPath("body.refresh_token").type(JsonFieldType.STRING)
						.description("OAuth Refresh Token")
				)
			))
			.andExpect(status().isCreated());
	}

	@DisplayName("사용자가 로그아웃한다.")
	@Test
	void logoutTest() throws Exception {
		/* Given */
		AuthMember authMemberMock = AuthMember.of(1L, "accessToken");

		given(authMemberService.getValidatedMemberId(anyString()))
			.willReturn(authMemberMock);

		/* When */
		ResultActions actions = mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/auth/v1/logout")
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
		);

		/* Then */
		actions.andDo(print())
			.andDo(document("auth/logout",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
				)
			))
			.andExpect(status().isOk());
	}

	// Todo : 이거 정상 작동하지 않아서 리졸버가 동작한다. 이유를 알아보자.
	private void givenMockLoginMember() {
		given(authenticatedArgumentResolver.supportsParameter(any(MethodParameter.class)))
			.willReturn(true);
		given(authenticatedArgumentResolver.resolveArgument(
			any(MethodParameter.class),
			any(ModelAndViewContainer.class),
			any(NativeWebRequest.class),
			any(WebDataBinderFactory.class))
		).willReturn(any(AuthMember.class));
	}
}