package ru.example.interf.repository;

import ru.example.interf.repository.domain.AccountEntity;

public interface AccountRepository {
    AccountEntity find(Long id);
}
