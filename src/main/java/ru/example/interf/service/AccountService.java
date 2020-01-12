package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.domain.AccountRequest;
import ru.example.interf.domain.Amount;
import ru.example.interf.domain.Card;
import ru.example.interf.properties.AccountProperties;
import ru.example.interf.repository.AccountRepository;

@RequiredArgsConstructor
public class AccountService {

    private final CardService cardService;
    private final AccountRepository accountRepository;
    private final AccountProperties accountProperties;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        Account account = accountRepository.find(accountRequest.getId());
        Card card = cardService.getCard(cardId);
        account.setCard(card);
        return doAccountMagic(account);
    }

    /** some logic **/
    private Account doAccountMagic(Account account) {
        Amount specificAmount = account.getAmount();
        specificAmount.setValue(
                specificAmount.getValue() * accountProperties.getMagicBusinessValue()
        );
        account.setAmount(specificAmount);
        return account;
    }
}
