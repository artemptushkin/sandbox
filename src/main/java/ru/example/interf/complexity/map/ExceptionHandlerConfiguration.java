package ru.example.interf.complexity.map;

import java.util.Map;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.HttpStatus;
import ru.example.interf.complexity.NotImplemented;
import ru.example.interf.complexity.ResponseEntity;

public class ExceptionHandlerConfiguration {

	public Map<Class<? extends Exception>, ResponseEntity> responseEntityMap() {
		return Map.of(
				NotImplemented.class, new ResponseEntity("Not implemented", HttpStatus.NOT_IMPLEMENTED),
				EntityNotFound.class, new ResponseEntity("Entity not found", HttpStatus.NOT_FOUND),
				ArgumentMismatchException.class, new ResponseEntity("Invalid request param", HttpStatus.BAD_REQUEST)
		);
	}

	public ExceptionHandlerMapAfter exceptionHandlerMap() {
		ResponseEntity defaultHandler = new ResponseEntity("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ExceptionHandlerMapAfter(responseEntityMap(), defaultHandler);
	}
}
