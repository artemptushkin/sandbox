package ru.example.interf.complexity.functional.iface;

import ru.example.interf.complexity.ArgumentMismatchException;
import ru.example.interf.complexity.EntityNotFound;
import ru.example.interf.complexity.HttpStatus;
import ru.example.interf.complexity.NotImplemented;
import ru.example.interf.complexity.ResponseEntity;

public class ExceptionHandlerFunctionalBefore {

	public ResponseEntity handle(Exception e) {
		if (e instanceof NotImplemented) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
		}
		else if (e instanceof EntityNotFound) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		else if (e instanceof ArgumentMismatchException) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
