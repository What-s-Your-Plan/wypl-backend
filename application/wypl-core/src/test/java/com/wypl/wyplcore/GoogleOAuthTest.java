package com.wypl.wyplcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;

@ActiveProfiles("local")
@SpringBootTest
public class GoogleOAuthTest {
	@Autowired
	private GoogleOAuthClient googleOAuthClient;

	@Test
	void testAccessToken() {
		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchGoogleOAuthToken(
			"!4%2F0AVG7fiQ0WZaJujdmtTrHyNdzHHa0z9YEdMjrKbojlBuyzAhnQAA_aC_U8KMu7X_P_HIzmw");

		System.out.println(googleTokenResponse);
	}

	@Test
	void testRefreshToken() {
		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchRefreshGoogleOAuthToken(
			"1//0edNF9tmusTsOCgYIARAAGA4SNwF-L9IrUKrzGqXFOd8Yn4L0ydz7xawU2GLUsjmaoeJ_1PB3kLG-YrNaNn009WbxXtVS3soqkWg");

		System.out.println(googleTokenResponse);
	}
}
