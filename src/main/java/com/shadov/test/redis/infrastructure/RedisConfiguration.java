package com.shadov.test.redis.infrastructure;

import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfiguration {
	@Bean
	LettuceConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties) {
		final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
		redisStandaloneConfiguration.setPassword(redisProperties.getPassword());

		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public VavrModule vavrModule() {
		return new VavrModule();
	}
}
