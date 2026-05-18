package com.svc.ventas.exception;

import java.util.*;
import java.util.stream.Collectors;

import com.svc.ventas.util.Constantes;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errores = ex.getFieldErrors()
						.stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
						.collect(Collectors.toList());
    log.error("handleValidationExceptions:: {}", errores);
		return new ResponseEntity<>(Collections.singletonMap("mensaje", Constantes.RESPONSE_ERROR_400), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		log.error("EntityNotFoundException:: {}", ex.getMessage());
		return new ResponseEntity<>(
						Collections.singletonMap("mensaje", Constantes.RESPONSE_ERROR_404),
						HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException ex) {
		log.error("UsernameNotFoundException:: {}", ex.getMessage());
		return new ResponseEntity<>(
						Collections.singletonMap("mensaje", Constantes.MENSAJE_USUARIO_NO_ENCONTRADO),
						HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Object> handleConflictException(ConflictException ex) {
		log.error("ConflictException:: {}", ex.getMessage());
		return new ResponseEntity<>(Collections.singletonMap("mensaje", Constantes.RESPONSE_ERROR_409), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex) {
		log.error("handleBusinessException:: {}", ex.getMessage());
		return new ResponseEntity<>(Collections.singletonMap("mensaje", ex.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException ex) {
		log.error("handleValidationException:: {}", ex.getMessage());
		return new ResponseEntity<>(Collections.singletonMap("mensaje", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class })
		public ResponseEntity<Map<String, Object>> handleEnumErrors(Exception ex) {
		return new ResponseEntity<>(Collections.singletonMap("mensaje", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDuplicateVenta(DataIntegrityViolationException  ex) {
		log.error("handleDataIntegrityViolation:: ", ex);

		String mensaje = "Error de integridad de datos";

		Throwable cause = ex.getRootCause();

		if (cause instanceof org.hibernate.exception.ConstraintViolationException constraintEx) {
			String constraintName = constraintEx.getConstraintName();

			mensaje = obtenerMensajePorConstraint(constraintName);
		}

		return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body(Collections.singletonMap("mensaje", mensaje));
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> handleGeneralExceptions(Exception ex) {
		log.error("handleGeneralExceptions:: {}", ex.getMessage());
		return new ResponseEntity<>(Collections.singletonMap("mensaje", Constantes.RESPONSE_ERROR_500), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Object> handleExpiredJwt(ExpiredJwtException ex) {
		log.error("handleExpiredJwt:: {}", ex.getMessage());
		return new ResponseEntity<>(Collections.singletonMap("mensaje", Constantes.RESPONSE_ERROR_401), HttpStatus.UNAUTHORIZED);
	}

	private String obtenerMensajePorConstraint(String constraintName) {

		if (constraintName == null) {
			return "Error de datos duplicados o inválidos";
		}

		return switch (constraintName) {
			case "uk_venta_serie_correlativo" ->
							"Ya existe una venta con ese serie y correlativo";

			case "uk_usuario_username" ->
							"El username ya está en uso";

			case "uk_producto_codigo" ->
							"El código del producto ya existe";

			default ->
							"Violación de restricción: " + constraintName;
		};
	}

}
