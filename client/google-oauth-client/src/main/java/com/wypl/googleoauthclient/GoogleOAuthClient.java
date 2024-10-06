package com.wypl.googleoauthclient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;

import lombok.RequiredArgsConstructor;

@EnableConfigurationProperties(GoogleOAuthProperties.class)
@RequiredArgsConstructor
@Component
public class GoogleOAuthClient {

	private final GoogleOAuthProperties googleOAuthProperties;
	// private final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";
	// private final String ACCESS_TOKEN_URI = "https://oauth2.googleapis.com/token";

	private final RestTemplate restTemplate = new RestTemplate();

	public void fetchGoogleAOAuthToken() {
		String code = "4%2F0AVG7fiTCI_si2GbBHv1bCqUqP32-_IEDSsC3XFeTXevm2-wXQxvSMytX-qBb1hzIZlVLsw";
		String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);

		// Todo : GoogleTokenRequest 로 변경
		// GoogleTokenRequest request = new GoogleTokenRequest(CLIENT_ID, CLIENT_SECRET, code, "authorization_code", REDIRECT_URI);

		// Todo : MultiValueMap을 필드로 갖는 팩토리 객체를 만들어도 될 듯, 메서드 체이닝
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		// Todo : utils로 중복 코드 뺄지 고민하자
		params.add("client_id", googleOAuthProperties.getClientId());
		params.add("client_secret", googleOAuthProperties.getClientSecret());
		params.add("code", decode);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", googleOAuthProperties.getRedirectUri());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity formEntity = new HttpEntity<>(params, headers);

		GoogleTokenResponse googleTokenResponse = restTemplate.postForObject(googleOAuthProperties.getAccessTokenUri(), formEntity,
			GoogleTokenResponse.class);

		// Todo : Error Handling

		System.out.println(googleTokenResponse.toString());
	}

	// public static void main(String[] args) {
	// 	RestTemplate restTemplate = new RestTemplate();  // RestTemplate 객체 생성
	// 	GoogleOAuthClient googleOAuthService = new GoogleOAuthClient();  // GoogleOAuthService 객체 생성
	// 	googleOAuthService.fetchGoogleAOAuthToken();  // 인스턴스 메서드 호출
	// }
}
