package com.wypl.awss3client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.aws.credentials")
public class AwsCredentialsProperties {
	private String accessKey;
	private String secretKey;
}