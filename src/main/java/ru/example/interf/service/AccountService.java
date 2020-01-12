package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.domain.AccountRequest;
import ru.example.interf.domain.Amount;
import ru.example.interf.domain.Card;
import ru.example.interf.properties.AccountProperties;
import ru.example.interf.repository.AccountRepository;
import ru.example.interf.repository.domain.AccountEntity;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class AccountService {

    private final CardService cardService;
    private final AccountRepository accountRepository;
    private final AmountConverter amountConverter;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        AccountEntity account = accountRepository.find(accountRequest.getId());
        Card card = cardService.getCard(cardId);
        account.setCard(card);
        Stream
        return amountConverter.apply(account);
    }

    /** some logic **/
    private Account doAccountMagic(AccountEntity accountEntity) {
        AmountEntity specificAmount = accountEntity.getAmount();
        specificAmount.setValue(
                specificAmount.getValue() * accountProperties.getMagicBusinessValue()
        );
        account.setAmount(specificAmount);
        return account;
    }
}
