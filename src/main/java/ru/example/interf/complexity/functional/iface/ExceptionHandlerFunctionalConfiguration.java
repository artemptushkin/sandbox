package ru.example.interf.complexity.functional.iface;

import java.util.Map;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.NotImplemented;

public class ExceptionHandlerFunctionalConfiguration {

	public Map<Class<? extends Exception>, ErrorResponse> responseEntityMap() {
		return Map.of(
				NotImplemented.class, ErrorResponse.NOT_IMPLEMENTED_ERROR,
				EntityNotFound.class, ErrorResponse.ENTITY_NOT_FOUND,
				ArgumentMismatchException.class, ErrorResponse.ARGUMENT_MISMATCH
		);
	}

	public ExceptionHandlerFunctionalMapAfter exceptionHandlerMap() {
		return new ExceptionHandlerFunctionalMapAfter(responseEntityMap(), ErrorResponse.DEFAULT);
	}
}
