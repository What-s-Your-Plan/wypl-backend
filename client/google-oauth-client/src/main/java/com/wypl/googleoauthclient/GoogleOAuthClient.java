package com.wypl.googleoauthclient;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.wypl.common.exception.GlobalErrorCode;
import com.wypl.common.exception.WyplException;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.exception.GoogleOAuthErrorCode;
import com.wypl.googleoauthclient.exception.GoogleOAuthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties(GoogleOAuthProperties.class)
@RequiredArgsConstructor
@Component
public class GoogleOAuthClient {

	private final GoogleOAuthProperties googleOAuthProperties;
	private final RestTemplate restTemplate;

	public GoogleTokenResponse fetchGoogleOAuthToken(String code) {
		MultiValueMap<String, String> params = GoogleOAuthParamFactory
			.create(googleOAuthProperties)
			.redirectUri(googleOAuthProperties.getRedirectUri())
			.code(code)
			.grantType("authorization_code")
			.build();
		return requestToken(params);
	}

	public GoogleTokenResponse fetchRefreshGoogleOAuthToken(String refreshToken) {
		MultiValueMap<String, String> params = GoogleOAuthParamFactory
			.create(googleOAuthProperties)
			.refreshToken(refreshToken)
			.grantType("refresh_token")
			.build();
		return requestToken(params);
	}

	private GoogleTokenResponse requestToken(MultiValueMap<String, String> params) {
		HttpEntity<MultiValueMap<String, String>> formEntity = getHttpEntity(params);

		try {
			return restTemplate.postForObject(
				googleOAuthProperties.getAccessTokenUri(),
				formEntity,
				GoogleTokenResponse.class
			);
		} catch (HttpClientErrorException e) {
			log.warn(e.getMessage());

			if (e.getMessage().contains("Malformed")) {
				throw new GoogleOAuthException(GoogleOAuthErrorCode.MALFORMED);
			}
			if (e.getMessage().contains("Bad Request")) {
				throw new GoogleOAuthException(GoogleOAuthErrorCode.BAD_REQUEST);
			}
			throw new WyplException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private static HttpEntity<MultiValueMap<String, String>> getHttpEntity(MultiValueMap<String, String> params) {
		HttpHeaders headers = setHttpHeader();
		return new HttpEntity<>(params, headers);
	}

	private static HttpHeaders setHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}
}
