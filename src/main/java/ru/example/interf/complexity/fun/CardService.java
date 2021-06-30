package ru.example.interf.complexity.fun;

import lombok.RequiredArgsConstructor;
import ru.example.interf.complexity.HttpStatus;
import ru.example.interf.complexity.ResponseEntity;
import ru.example.interf.domain.Card;

@RequiredArgsConstructor
public class CardService {
	private final CardHandler cardHandler;

	public ResponseEntity processCard(Card card) {
		return cardHandler.handle(card, c -> new ResponseEntity(c, HttpStatus.OK));
	}
}
