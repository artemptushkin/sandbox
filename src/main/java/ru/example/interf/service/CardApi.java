package ru.example.interf.service;

import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardSearchRequest;

public interface CardApi {
    Card findCard(CardSearchRequest cardSearchRequest);
}
