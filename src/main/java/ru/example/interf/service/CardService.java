package ru.example.interf.service;

import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardDetails;

public interface CardService {
    Card getCard(Long cardId);
    CardDetails getCardDetails(Long cardId);
}
