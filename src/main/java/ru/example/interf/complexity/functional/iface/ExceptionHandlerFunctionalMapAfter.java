package ru.example.interf.complexity.functional.iface;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import ru.example.interf.complexity.ResponseEntity;

@RequiredArgsConstructor
public class ExceptionHandlerFunctionalMapAfter {
	private final Map<Class<? extends Exception>, ErrorResponse> responseEntityMap;
	private final ErrorResponse defaultResponseEntity;

	public ResponseEntity handle(Exception e) {
		return responseEntityMap
				.getOrDefault(e.getClass(), defaultResponseEntity)
				.apply(e);
	}
}
