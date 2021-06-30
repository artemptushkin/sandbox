package ru.example.interf.service;

import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.domain.AccountRequest;
import ru.example.interf.domain.Card;
import ru.example.interf.domain.CardDetails;
import ru.example.interf.repository.AccountRepository;
import ru.example.interf.repository.domain.AccountEntity;

@RequiredArgsConstructor
public class AccountService {

    private final CardService cardService;
    private final AccountRepository accountRepository;
    private final Function<AccountEntity, Account> accountConverter;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        CardDetails cardDetails = cardService.getCardDetails(cardId);
        Card card = cardService.getCard(cardId);

        AccountEntity accountEntity = accountRepository.find(accountRequest.getId());

        Account account = accountConverter.apply(accountEntity);
        account.setCard(card);
        account.setCardDetails(cardDetails);
        return account;
    }
}
