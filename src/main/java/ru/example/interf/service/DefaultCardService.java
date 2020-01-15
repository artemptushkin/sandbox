package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardSearchRequest;
import ru.example.interf.properties.CardProperties;

@RequiredArgsConstructor
public class DefaultCardService implements CardService {
    private final CardStorageService cardStorageService;

    @Override
    public Card getCard(Long cardId) {
        CardSearchRequest cardSearchRequest = createCardRequest(cardId);
        return cardStorageService.findCard(cardSearchRequest);
    }

    private CardSearchRequest createCardRequest(Long cardId) {
        CardSearchRequest cardSearchRequest = new CardSearchRequest();
        cardSearchRequest.setId(cardId * 100);
        return cardSearchRequest;
    }
}
