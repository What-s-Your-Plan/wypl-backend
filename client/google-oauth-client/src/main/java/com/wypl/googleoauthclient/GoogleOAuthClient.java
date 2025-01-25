package com.wypl.googleoauthclient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.wypl.common.exception.GlobalErrorCode;
import com.wypl.common.exception.WyplException;
import com.wypl.googleoauthclient.config.GoogleOAuthProperties;
import com.wypl.googleoauthclient.data.response.BirthdayResponse;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleTokenValidationResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
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
	private static final String VALIDATION_URI = "https://www.googleapis.com/oauth2/v1/tokeninfo";
	private static final String USERINFO_URI = "https://www.googleapis.com/oauth2/v1/userinfo";
	private static final String BIRTHDAY_URI = "https://people.googleapis.com/v1/people/me?personFields=birthdays";

	private final GoogleOAuthProperties googleOAuthProperties;
	private final RestTemplate restTemplate;

	/**
	 * <a href="https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko#creatingclient">승인 매개변수 설정 공식 문서</a>
	 * <p>
	 * Authorization code 값으로 구글에 토큰 발급을 요청합니다.
	 *
	 * @param code 토큰 발급 시 사용하는 Authorization Code
	 * @return 구글에서 발급받은 토큰 정보
	 * @throws GoogleOAuthException Authorization Code가 올바르지 않으면 예외를 던진다.
	 */
	public GoogleTokenResponse fetchGoogleOAuthToken(String code) {
		MultiValueMap<String, String> params = GoogleOAuthParamFactory
			.create(googleOAuthProperties)
			.redirectUri(googleOAuthProperties.getRedirectUri())
			.code(code)
			.grantType("authorization_code")
			.build();
		return requestToken(params);
	}

	/**
	 * <a href="https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko#offline">액세스 토큰 갱신 공식 문서</a>
	 * 구글에 토큰 재발급을 요청합니다.
	 *
	 * @param refreshToken 토큰 재발급에 사용할 Refresh Token
	 * @return 재발급 받은 토큰 정보
	 * @throws GoogleOAuthException Refresh Token이 올바르지 않으면 예외를 던진다.
	 */
	public GoogleTokenResponse fetchRefreshGoogleOAuthToken(String refreshToken) {
		MultiValueMap<String, String> params = GoogleOAuthParamFactory
			.create(googleOAuthProperties)
			.refreshToken(refreshToken)
			.grantType("refresh_token")
			.build();
		return requestToken(params);
	}

	/**
	 * Access Token으로 구글에 유저 정보를 요청한다.
	 *
	 * @param accessToken 구글에서 발급받은 Access Token
	 * @return 유저의 상세 정보
	 */
	public GoogleUserInfoResponse fetchUserInfo(String accessToken) {
		HttpEntity<String> entity = getAuthEntity(accessToken);

		try {
			return restTemplate.exchange(
					USERINFO_URI,
					HttpMethod.GET,
					entity,
					GoogleUserInfoResponse.class)
				.getBody();
		} catch (HttpClientErrorException e) {
			throw new GoogleOAuthException(GoogleOAuthErrorCode.INVALID_TOKEN);
		}
	}

	/**
	 * Access Token으로 구글에 생일 정보를 요청한다.
	 *
	 * @param accessToken 구글에서 발급받은 Access Token
	 * @return 유저의 생일 정보. 생일 비공개 사용자는 null
	 */
	public LocalDate fetchBirthday(String accessToken) {
		HttpEntity<String> entity = getAuthEntity(accessToken);

		ResponseEntity<BirthdayResponse> responseEntity = restTemplate.exchange(
			BIRTHDAY_URI,
			HttpMethod.GET,
			entity,
			BirthdayResponse.class);

		BirthdayResponse response = responseEntity.getBody();

		if (response.emptyBirthday()) {
			return null;
		}

		BirthdayResponse.Birthday.Date date = response.getBirthdays().get(0).getDate();

		return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
	}

	private static HttpEntity<String> getAuthEntity(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		return new HttpEntity<>("", headers);
	}

	/**
	 * 구글에 Access Token의 유효성 검증을 요청합니다.
	 *
	 * @param accessToken 유효성 검증할 Access Token
	 * @return 토큰의 유효성 정보
	 * @throws GoogleOAuthException Access Token이 올바르지 않으면 예외를 던진다.
	 */
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
