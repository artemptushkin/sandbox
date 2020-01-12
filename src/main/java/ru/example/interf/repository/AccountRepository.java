package ru.example.interf.repository;

import ru.example.interf.domain.Account;

public interface AccountRepository {
    Account find(Long id);
}
