package ru.example.interf.complexity.polym.iface;

import java.util.function.Function;

import ru.example.interf.complexity.ResponseEntity;

import static ru.example.interf.complexity.HttpStatus.BAD_REQUEST;
import static ru.example.interf.complexity.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.example.interf.complexity.HttpStatus.NOT_FOUND;
import static ru.example.interf.complexity.HttpStatus.NOT_IMPLEMENTED;

interface ErrorResponse extends Function<Exception, ResponseEntity> {
	ErrorResponse NOT_IMPLEMENTED_ERROR = e -> new ResponseEntity(e.getMessage(), NOT_IMPLEMENTED);
	ErrorResponse ENTITY_NOT_FOUND = e -> new ResponseEntity(e.getMessage(), NOT_FOUND);
	ErrorResponse ARGUMENT_MISMATCH = e -> new ResponseEntity(e.getMessage(), BAD_REQUEST);
	ErrorResponse DEFAULT = e -> new ResponseEntity(e.getMessage(), INTERNAL_SERVER_ERROR);
}
