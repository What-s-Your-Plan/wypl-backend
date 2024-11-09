package com.wypl.googleoauthclient;

import java.util.HashMap;
import java.util.Map;

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
import com.wypl.googleoauthclient.config.GoogleOAuthProperties;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleTokenValidationResponse;
import com.wypl.googleoauthclient.exception.GoogleOAuthErrorCode;
import com.wypl.googleoauthclient.exception.GoogleOAuthException;
import com.wypl.googleoauthclient.utils.GoogleOAuthParamFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties(GoogleOAuthProperties.class)
@RequiredArgsConstructor
@Component
public class GoogleOAuthClient {

	private final GoogleOAuthProperties googleOAuthProperties;
	private final RestTemplate restTemplate;
	private final String VALIDATION_URI = "https://www.googleapis.com/oauth2/v1/tokeninfo";

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

	public GoogleTokenValidationResponse validateToken(String accessToken) {
		Map<String, String> params = new HashMap<>();
		params.put("access_token", accessToken);

		try {
			return restTemplate.getForObject(VALIDATION_URI + "?access_token={access_token}"
			, GoogleTokenValidationResponse.class
			, params);
		} catch (HttpClientErrorException e) {
			throw new GoogleOAuthException(GoogleOAuthErrorCode.INVALID_TOKEN);
		}
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
			if (e.getMessage().contains("Malformed")) {
				throw new GoogleOAuthException(GoogleOAuthErrorCode.MALFORMED);
			}
			if (e.getMessage().contains("Bad Request")) {
				throw new GoogleOAuthException(GoogleOAuthErrorCode.BAD_REQUEST);
			}
			log.warn(e.getMessage());
			throw new WyplException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private HttpEntity<MultiValueMap<String, String>> getHttpEntity(MultiValueMap<String, String> params) {
		HttpHeaders headers = setHttpHeader();
		return new HttpEntity<>(params, headers);
	}

	private HttpHeaders setHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}
}
