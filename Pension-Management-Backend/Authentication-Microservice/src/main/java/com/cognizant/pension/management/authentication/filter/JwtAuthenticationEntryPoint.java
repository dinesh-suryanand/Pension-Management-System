package com.cognizant.pension.management.authentication.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.cognizant.pension.management.authentication.constant.SecurityConstant;
import com.cognizant.pension.management.authentication.domain.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Adrish
 *
 */
@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
	
	/**
	 * Prevent user from accessing the application without proper credentials
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setHttpStatusCode(HttpStatus.FORBIDDEN.value());
		httpResponse.setHttpStatus(HttpStatus.FORBIDDEN);
		httpResponse.setReason(HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase());
		httpResponse.setMessage(SecurityConstant.FORBIDDEN_MESSAGE);
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}
}
