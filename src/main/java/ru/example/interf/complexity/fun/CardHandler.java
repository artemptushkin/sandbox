package ru.example.interf.complexity.fun;

import java.util.function.Function;

import ru.example.interf.complexity.ResponseEntity;
import ru.example.interf.domain.Card;

public class CardHandler {
	public ResponseEntity handle(Card card, Function<Card, ResponseEntity> cardFunction) {
		return cardFunction.apply(card);
	}
}
