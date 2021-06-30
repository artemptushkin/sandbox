package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardDetails;

@RequiredArgsConstructor
public class CardServiceWrapper implements CardService {
	private final CardService wrapped;

	@Override
	public Card getCard(Long cardId) {
		return wrapped.getCard(cardId);
	}

	@Override
	public CardDetails getCardDetails(Long cardId) {
		return new CardDetails();
	}

}
