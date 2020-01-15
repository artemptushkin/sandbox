package ru.example.interf.service;

import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardSearchRequest;

public interface CardStorageService {
    Card findCard(CardSearchRequest cardSearchRequest);
}
