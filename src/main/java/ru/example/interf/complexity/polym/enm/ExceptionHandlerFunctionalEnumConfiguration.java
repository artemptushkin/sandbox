package ru.example.interf.complexity.polym.enm;

import java.util.Map;
import java.util.function.Function;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.NotImplemented;
import ru.example.interf.complexity.ResponseEntity;
import ru.example.interf.complexity.polym.ExceptionHandlerFunctionalAfter;

public class ExceptionHandlerFunctionalEnumConfiguration {

	public Map<Class<? extends Exception>, Function<Exception, ResponseEntity>> responseEntityMap() {
		return Map.of(
				NotImplemented.class, ErrorResponseType.NOT_IMPLEMENTED_ERROR,
				EntityNotFound.class, ErrorResponseType.ENTITY_NOT_FOUND,
				ArgumentMismatchException.class, ErrorResponseType.ARGUMENT_MISMATCH
		);
	}

	public ExceptionHandlerFunctionalAfter exceptionHandlerMap() {
		return new ExceptionHandlerFunctionalAfter(responseEntityMap(), ErrorResponseType.DEFAULT);
	}
}
