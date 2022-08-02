package com.cognizant.pension.management.authentication.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.cognizant.pension.management.authentication.constant.SecurityConstant;
import com.cognizant.pension.management.authentication.domain.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Adrish
 *
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * Prevent user from accessing the application without log in
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setTimestamp(new Date());
		httpResponse.setHttpStatusCode(HttpStatus.UNAUTHORIZED.value());
		httpResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
		httpResponse.setReason(HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase());
		httpResponse.setMessage(SecurityConstant.ACCESS_DENIED_MESSAGE);
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}

}
