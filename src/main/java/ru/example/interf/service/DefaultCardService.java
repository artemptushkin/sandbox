package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardDetails;
import ru.example.interf.domain.CardSearchRequest;

@RequiredArgsConstructor
public class DefaultCardService implements CardService {
    private final CardApi cardApi;

    @Override
    public Card getCard(Long cardId) {
        CardSearchRequest cardSearchRequest = createCardRequest(cardId);
        return cardApi.findCard(cardSearchRequest);
    }

    @Override
    public CardDetails getCardDetails(Long cardId) {
        throw new UnsupportedOperationException("I can't provide card details!");
    }

    private CardSearchRequest createCardRequest(Long cardId) {
        return CardSearchRequest
                .builder()
                .id(cardId * 100)
                .build();
    }
}
