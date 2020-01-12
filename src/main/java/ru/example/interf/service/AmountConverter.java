package ru.example.interf.service;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.properties.AccountProperties;
import ru.example.interf.repository.domain.AccountEntity;

import java.util.function.Function;

@RequiredArgsConstructor
public class AmountConverter implements Function<AccountEntity, Account> {

    private final AccountProperties accountProperties;

    @Override
    public Account apply(AccountEntity accountEntity) {
        AmountEntity specificAmount = accountEntity.getAmount();
        specificAmount.setValue(
                specificAmount.getValue() * accountProperties.getMagicBusinessValue()
        );
        account.setAmount(specificAmount);
        return account;
    }
}
