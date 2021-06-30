package ru.example.interf.complexity.functional;

import java.util.Map;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import ru.example.interf.complexity.ResponseEntity;

@RequiredArgsConstructor
public class ExceptionHandlerFunctionalAfter {
	private final Map<Class<? extends Exception>, Function<Exception, ResponseEntity>> responseEntityMap;
	private final Function<Exception, ResponseEntity> defaultResponseEntity;

	public ResponseEntity handle(Exception e) {
		return responseEntityMap
				.getOrDefault(e.getClass(), defaultResponseEntity)
				.apply(e);
	}
}
