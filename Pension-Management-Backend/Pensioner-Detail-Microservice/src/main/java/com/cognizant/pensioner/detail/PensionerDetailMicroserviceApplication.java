package com.cognizant.pensioner.detail;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cognizant.pensioner.detail.constant.CorsFilterConstant;

@SpringBootApplication
@EnableFeignClients
public class PensionerDetailMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PensionerDetailMicroserviceApplication.class, args);
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
}
