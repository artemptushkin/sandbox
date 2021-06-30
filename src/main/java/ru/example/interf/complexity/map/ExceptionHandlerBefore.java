package ru.example.interf.complexity.map;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.HttpStatus;
import ru.example.interf.complexity.NotImplemented;
import ru.example.interf.complexity.ResponseEntity;

public class ExceptionHandlerBefore {

	public ResponseEntity handle(Exception e) {
		if (e instanceof NotImplemented) {
			return new ResponseEntity("Not implemented", HttpStatus.NOT_IMPLEMENTED);
		}
		else if (e instanceof EntityNotFound) {
			return new ResponseEntity("Entity not found", HttpStatus.NOT_FOUND);
		}
		else if (e instanceof ArgumentMismatchException) {
			return new ResponseEntity("Invalid request param", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
