package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardSearchRequest;

@RequiredArgsConstructor
public class DefaultCardService implements CardService {
    private final CardStorageService cardStorageService;

    @Override
    public Card getCard(Long cardId) {
        CardSearchRequest cardSearchRequest = createCardRequest(cardId);
        return cardStorageService.findCard(cardSearchRequest);
    }

    private CardSearchRequest createCardRequest(Long cardId) {
        return CardSearchRequest
                .builder()
                .id(cardId * 100)
                .build();
    }
}
