package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.domain.AccountRequest;
import ru.example.interf.domain.Amount;
import ru.example.interf.repository.domain.AmountEntity;
import ru.example.interf.domain.Card;
import ru.example.interf.properties.AccountProperties;
import ru.example.interf.repository.AccountRepository;
import ru.example.interf.repository.domain.AccountEntity;

@RequiredArgsConstructor
public class AccountService {

    private final CardService cardService;
    private final AccountRepository accountRepository;
    private final AccountProperties accountProperties;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        /* fetch entities */
        AccountEntity accountEntity = accountRepository.find(accountRequest.getId());
        Card card = cardService.getCard(cardId);

        /* calculate value */
        AmountEntity specificAmount = accountEntity.getAmount();
        long targetAmount = specificAmount.getValue() * accountProperties.getMagicBusinessValue();

        /* build expected object */
        Account account = new Account();
        account.setCard(card);
        account.setAmount(Amount
                .builder()
                .value(targetAmount)
                .build()
        );
        return account;
    }
}
