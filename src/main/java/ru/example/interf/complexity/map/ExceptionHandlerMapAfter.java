package ru.example.interf.complexity.map;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import ru.example.interf.complexity.ResponseEntity;

@RequiredArgsConstructor
public class ExceptionHandlerMapAfter {
	private final Map<Class<? extends Exception>, ResponseEntity> responseEntityMap;
	private final ResponseEntity defaultResponseEntity;

	public ResponseEntity handle(Exception e) {
		return responseEntityMap.getOrDefault(e.getClass(), defaultResponseEntity);
	}
}
