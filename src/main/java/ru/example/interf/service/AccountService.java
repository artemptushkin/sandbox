package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.domain.AccountRequest;
import ru.example.interf.domain.Card;
import ru.example.interf.repository.AccountRepository;
import ru.example.interf.repository.domain.AccountEntity;

@RequiredArgsConstructor
public class AccountService {

    private final CardService cardService;
    private final AccountRepository accountRepository;
    private final AmountConverter amountConverter;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        AccountEntity accountEntity = accountRepository.find(accountRequest.getId());
        Card card = cardService.getCard(cardId);
        Account account = amountConverter.apply(accountEntity);
        account.setCard(card);
        return account;
    }
}
