package ru.example.interf.service;

import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import ru.example.interf.domain.Account;
import ru.example.interf.domain.Amount;
import ru.example.interf.domain.AmountEntity;
import ru.example.interf.properties.AccountProperties;
import ru.example.interf.repository.domain.AccountEntity;

@RequiredArgsConstructor
public class AmountConverter implements Function<AccountEntity, Account> {

    private final AccountProperties accountProperties;

    @Override
    public Account apply(AccountEntity accountEntity) {
        Account account = new Account();
        AmountEntity specificAmount = accountEntity.getAmount();
        long targetValue = specificAmount.getValue() * accountProperties.getMagicBusinessValue();
        account.setAmount(Amount
                .builder()
                .value(targetValue)
                .build()
        );
        return account;
    }
}
