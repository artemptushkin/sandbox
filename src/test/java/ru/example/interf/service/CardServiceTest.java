package ru.example.interf.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardSearchRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CardServiceTest {

    CardService cardService;

    CardApi cardApi;

    @BeforeEach
    void setUp() {
        cardApi = mock(CardApi.class);
        cardService = new CardService(cardApi);
    }

    @Test
    void getCard() {
        Long id = 200L;
        Card expected = Card.builder().build();
        CardSearchRequest cardSearchRequest = prepareCardSearchRequest(id);
        when(cardApi.findCard(eq(cardSearchRequest))).thenReturn(expected);

        Card actual = cardService.getCard(id);

        assertEquals(expected, actual);
    }

    private CardSearchRequest prepareCardSearchRequest(Long id) {
        return CardSearchRequest
                .builder()
                .id(id * 100)
                .build();
    }
}
