package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Card;
import ru.example.interf.properties.CardProperties;

@RequiredArgsConstructor
public class DefaultCardService implements CardService {
    private final CardProperties cardProperties;

    @Override
    public Card getCard(Long cardId) {
        ...
    }
}
