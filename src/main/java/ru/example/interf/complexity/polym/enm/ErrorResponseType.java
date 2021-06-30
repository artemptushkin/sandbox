package ru.example.interf.complexity.polym.enm;

import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import ru.example.interf.complexity.HttpStatus;
import ru.example.interf.complexity.ResponseEntity;

import static ru.example.interf.complexity.HttpStatus.BAD_REQUEST;
import static ru.example.interf.complexity.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.example.interf.complexity.HttpStatus.NOT_FOUND;
import static ru.example.interf.complexity.HttpStatus.NOT_IMPLEMENTED;

@RequiredArgsConstructor
enum ErrorResponseType implements Function<Exception, ResponseEntity> {
	NOT_IMPLEMENTED_ERROR(NOT_IMPLEMENTED),
	ENTITY_NOT_FOUND(NOT_FOUND),
	ARGUMENT_MISMATCH(BAD_REQUEST),
	DEFAULT(INTERNAL_SERVER_ERROR);

	private final HttpStatus httpStatus;

	@Override
	public ResponseEntity apply(Exception e) {
		return new ResponseEntity(e.getMessage(), this.httpStatus);
	}
}