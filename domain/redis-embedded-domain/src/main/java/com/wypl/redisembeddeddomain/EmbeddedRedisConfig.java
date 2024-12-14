package com.wypl.redisembeddeddomain;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wypl.redisembeddeddomain.redis.OS;
import com.wypl.redisembeddeddomain.redis.RedisAvailablePortFind;
import com.wypl.redisembeddeddomain.redis.RedisAvailablePortFindForDebian;
import com.wypl.redisembeddeddomain.redis.RedisAvailablePortFindForLinux;
import com.wypl.redisembeddeddomain.redis.RedisAvailablePortFindForMac;
import com.wypl.redisembeddeddomain.redis.RedisAvailablePortFindForUbuntu;
import com.wypl.redisembeddeddomain.redis.RedisAvailablePortFindForWindows;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Profile({"default", "local", "test"})
@Configuration
public class EmbeddedRedisConfig {
	private static final String OS_NAME = System.getProperty("os.name");
	private final int REDIS_DEFAULT_PORT = 6379;

	private RedisServer redisServer;

	@PostConstruct
	private void start() throws IOException {
		RedisAvailablePortFind findAvailablePortUtils = getRedisAvailablePortFind();

		int port = findAvailablePortUtils.isRedisRunning(REDIS_DEFAULT_PORT)
			? findAvailablePortUtils.findAvailablePort(REDIS_DEFAULT_PORT)
			: REDIS_DEFAULT_PORT;
		log.info("Embedded Redis Running Port : [{}]", port);

		redisServer = new RedisServer(port);
		redisServer.start();
	}

	@PreDestroy
	private void stop() throws IOException {
		if (redisServer != null) {
			redisServer.stop();
		}
	}

	private RedisAvailablePortFind getRedisAvailablePortFind() {
		if (OS.MAC.contains(OS_NAME)) {
			return new RedisAvailablePortFindForMac();
		} else if (OS.WINDOWS.contains(OS_NAME)) {
			return new RedisAvailablePortFindForWindows();
		} else if (OS.UBUNTU.contains(OS_NAME)) {
			return new RedisAvailablePortFindForUbuntu();
		} else if (OS.LINUX.contains(OS_NAME)) {
			return new RedisAvailablePortFindForLinux();
		} else if (OS.DEBIAN.contains(OS_NAME)) {
			return new RedisAvailablePortFindForDebian();
		}
		throw new IllegalArgumentException("Unsupported OS : " + OS_NAME);
	}
}
