package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Card;

@RequiredArgsConstructor
public class CardServiceWrapper implements CardService {
	private final CardService wrapped;

	@Override
	public Card getCard(Long cardId) {
		return wrapped.getCard(cardId);
	}
}
