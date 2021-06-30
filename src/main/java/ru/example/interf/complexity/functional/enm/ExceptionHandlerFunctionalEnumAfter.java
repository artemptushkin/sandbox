package ru.example.interf.complexity.functional.enm;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import ru.example.interf.complexity.ResponseEntity;

@RequiredArgsConstructor
public class ExceptionHandlerFunctionalEnumAfter {
	private final Map<Class<? extends Exception>, ErrorResponseType> responseEntityMap;
	private final ErrorResponseType defaultResponseEntity;

	public ResponseEntity handle(Exception e) {
		return responseEntityMap
				.getOrDefault(e.getClass(), defaultResponseEntity)
				.apply(e);
	}
}
