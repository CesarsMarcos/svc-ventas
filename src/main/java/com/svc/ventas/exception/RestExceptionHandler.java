package com.svc.ventas.exception;

import java.util.*;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errores = new ArrayList<>();
		ex.getFieldErrors().forEach(err -> {
			errores.add("El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(getErrorsMap(errores));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(EntityNotFoundException ex) {
		return new ResponseEntity<>(Collections.singletonMap("mensaje", ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Object> handleConflictException(ConflictException ex) {
		return new ResponseEntity<>(Collections.singletonMap("mensaje", ex.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Map<String, Object>> handleEnumErrors(Exception ex) {
		return new ResponseEntity<>(Collections.singletonMap("mensaje", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("mensaje", errors);
		return errorResponse;
	}

}
