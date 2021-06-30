package ru.example.interf.complexity.functional.enm;

import java.util.Map;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.NotImplemented;

public class ExceptionHandlerFunctionalEnumConfiguration {

	public Map<Class<? extends Exception>, ErrorResponseType> responseEntityMap() {
		return Map.of(
				NotImplemented.class, ErrorResponseType.NOT_IMPLEMENTED_ERROR,
				EntityNotFound.class, ErrorResponseType.ENTITY_NOT_FOUND,
				ArgumentMismatchException.class, ErrorResponseType.ARGUMENT_MISMATCH
		);
	}

	public ExceptionHandlerFunctionalEnumAfter exceptionHandlerMap() {
		return new ExceptionHandlerFunctionalEnumAfter(responseEntityMap(), ErrorResponseType.DEFAULT);
	}
}
