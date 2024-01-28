package com.example.programare_examene.common.exception;

import static org.springframework.http.HttpStatus.*;

import com.example.programare_examene.common.exception.model.ErrorDTO;
import com.example.programare_examene.common.exception.model.ErrorsDTO;


import com.example.programare_examene.common.config.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {

	private final ApplicationConfig.Api api;

	@ExceptionHandler(HttpClientErrorException.class)
	public final ResponseEntity<ErrorsDTO> handleException(
			HttpClientErrorException ex, WebRequest req) {
		String message = ex.getMessage();

		log.error("Response status exception '{}' : {}", req.getContextPath(), message, ex);
		return ResponseEntity.status(ex.getStatusCode().value())
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(ex.getStatusCode().value())
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorType(
																ErrorDTO.ErrorTypeEnum.FUNCTIONAL)
														.errorMessage(getMessage(ex, message))
														.build()))
								.build());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorsDTO> handleException(
			ConstraintViolationException ex, WebRequest req) {
		String message =
				ex.getConstraintViolations().stream()
						.map(ConstraintViolation::getMessageTemplate)
						.collect(Collectors.joining(","));
		log.error("Response status exception '{}' : {}", req.getContextPath(), message, ex);
		return ResponseEntity.status(BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(BAD_REQUEST.value())
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorType(
																ErrorDTO.ErrorTypeEnum.FUNCTIONAL)
														.errorMessage(getMessage(ex, message))
														.build()))
								.build());
	}

	@ExceptionHandler(value = {ResponseStatusException.class})
	private ResponseEntity<ErrorsDTO> handleResponseStatusException(
			ResponseStatusException ex, WebRequest req) {
		String message = ex.getMessage();
		log.error("Response status exception '{}' : {}", req.getContextPath(), message, ex);

		return ResponseEntity.status(ex.getStatus())
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(ex.getRawStatusCode())
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorType(
																ErrorDTO.ErrorTypeEnum.FUNCTIONAL)
														.errorMessage(getMessage(ex, message))
														.build()))
								.build());
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResponseEntity<ErrorsDTO> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, WebRequest req) {
		String message = ex.getMessage();
		log.error(
				"Invalid argument exception for path '{}' : {}", req.getContextPath(), message, ex);

		return ResponseEntity.status(BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(BAD_REQUEST.value())
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorType(ErrorDTO.ErrorTypeEnum.TECHNICAL)
														.errorMessage(
																getMessage(
																		ex,
																		BAD_REQUEST
																				.getReasonPhrase()
																				+ ": "
																				+ message))
														.build()))
								.build());
	}

	@ExceptionHandler(value = {AccessDeniedException.class})
	@ResponseStatus(value = FORBIDDEN)
	public ResponseEntity<ErrorsDTO> accessDeniedException(
			AccessDeniedException ex, WebRequest req) {
		String message = ex.getMessage();
		log.error("Access denied to path '{}' : {}", req.getContextPath(), message, ex);

		return ResponseEntity.status(FORBIDDEN)
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(FORBIDDEN.value())
														.errorType(ErrorDTO.ErrorTypeEnum.TECHNICAL)
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorMessage(
																getMessage(
																		ex,
																		FORBIDDEN.getReasonPhrase()
																				+ ": "
																				+ message))
														.build()))
								.build());
	}

	@ExceptionHandler(value = {MissingServletRequestParameterException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorsDTO> missingServletRequestParameterException(
			MissingServletRequestParameterException ex, WebRequest req) {
		String message = ex.getMessage();
		log.error(
				"Missing request parameter for the path '{}' : {}",
				req.getContextPath(),
				message,
				ex);

		return ResponseEntity.status(BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(BAD_REQUEST.value())
														.errorType(ErrorDTO.ErrorTypeEnum.TECHNICAL)
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorMessage(
																getMessage(
																		ex,
																		"Missing parameter "
																				+ ex
																				.getParameterName()
																				+ ": "
																				+ message))
														.build()))
								.build());
	}

	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorsDTO> unknownException(Exception ex, WebRequest req) {
		log.error("Unexpected exception", ex);

		return ResponseEntity.status(INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.body(
						ErrorsDTO.builder()
								.errors(
										List.of(
												ErrorDTO.builder()
														.errorCode(INTERNAL_SERVER_ERROR.value())
														.errorType(ErrorDTO.ErrorTypeEnum.TECHNICAL)
														.errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
														.errorMessage(
																getMessage(
																		ex,
																		INTERNAL_SERVER_ERROR
																				.getReasonPhrase()
																				+ ": "
																				+ ex.getMessage()))
														.build()))
								.build());
	}

	private String getMessage(Throwable throwable, String message) {
		return api.isStacktraceEnabled() ? getStacktrace(throwable) : message;
	}

	private static String getStacktrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
}
