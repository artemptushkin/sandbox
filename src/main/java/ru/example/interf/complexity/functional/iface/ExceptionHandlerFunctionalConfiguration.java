package ru.example.interf.complexity.functional.iface;

import java.util.Map;
import java.util.function.Function;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.NotImplemented;
import ru.example.interf.complexity.ResponseEntity;
import ru.example.interf.complexity.functional.ExceptionHandlerFunctionalAfter;

public class ExceptionHandlerFunctionalConfiguration {

	public Map<Class<? extends Exception>, Function<Exception, ResponseEntity>> responseEntityMap() {
		return Map.of(
				NotImplemented.class, ErrorResponse.NOT_IMPLEMENTED_ERROR,
				EntityNotFound.class, ErrorResponse.ENTITY_NOT_FOUND,
				ArgumentMismatchException.class, ErrorResponse.ARGUMENT_MISMATCH
		);
	}

	public ExceptionHandlerFunctionalAfter exceptionHandlerMap() {
		return new ExceptionHandlerFunctionalAfter(responseEntityMap(), ErrorResponse.DEFAULT);
	}
}
