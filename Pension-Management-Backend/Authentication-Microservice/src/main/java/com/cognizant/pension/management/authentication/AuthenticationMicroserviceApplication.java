package com.cognizant.pension.management.authentication;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cognizant.pension.management.authentication.constant.CorsFilterConstant;
import com.cognizant.pension.management.authentication.constant.FileConstant;

@SpringBootApplication
public class AuthenticationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationMicroserviceApplication.class, args);
		new File(FileConstant.USER_FOLDER).mkdirs();
	}

	/**
	 * CorsFilter for Frontend Connection
	 * 
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsFilterConstant.ANGULAR_URL));
		corsConfiguration.setAllowedHeaders(Arrays.asList(CorsFilterConstant.ALLOWED_HEADER_ORIGIN,
				CorsFilterConstant.ALLOWED_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN,
				CorsFilterConstant.ALLOWED_HEADER_CONTENT_TYPE, CorsFilterConstant.ALLOWED_HEADER_ACCEPT,
				CorsFilterConstant.ALLOWED_HEADER_JWT_TOKEN, CorsFilterConstant.ALLOWED_HEADER_AUTHORIZATION,
				CorsFilterConstant.ALLOWED_HEADER_ORIGIN_ACCEPT, CorsFilterConstant.ALLOWED_HEADER_X_REQUESTED_WITH,
				CorsFilterConstant.ALLOWED_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
				CorsFilterConstant.ALLOWED_HEADER_ACCESS_CONTROL_REQUEST_HEADERS));
		corsConfiguration.setExposedHeaders(Arrays.asList(CorsFilterConstant.ALLOWED_HEADER_ORIGIN,
				CorsFilterConstant.ALLOWED_HEADER_CONTENT_TYPE, CorsFilterConstant.ALLOWED_HEADER_ACCEPT,
				CorsFilterConstant.ALLOWED_HEADER_JWT_TOKEN, CorsFilterConstant.ALLOWED_HEADER_AUTHORIZATION,
				CorsFilterConstant.ALLOWED_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN,
				CorsFilterConstant.ALLOWED_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN,
				CorsFilterConstant.ALLOWED_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS));
		corsConfiguration.setAllowedMethods(Arrays.asList(CorsFilterConstant.HTTP_GET, CorsFilterConstant.HTTP_POST,
				CorsFilterConstant.HTTP_PUT, CorsFilterConstant.HTTP_DELETE, CorsFilterConstant.HTTP_OPTIONS));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	/**
	 * Encoder for Password Encryption
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
