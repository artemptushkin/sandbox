package ru.example.interf.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.example.interf.domain.AccountRequest;
import ru.example.interf.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AccountServiceTests {

	private CardService cardService;
	private AccountRepository accountRepository;
	private AccountConverter accountConverter;

	private AccountService victim;

	@BeforeEach
	void setup() {
		cardService = new DefaultCardService(mock(CardApi.class));
		accountRepository = mock(AccountRepository.class);
		accountConverter = mock(AccountConverter.class);

		victim = new AccountService(cardService, accountRepository, accountConverter);
	}

	@Test
	void testGetAccount() {
		AccountRequest accountRequest = AccountRequest.builder().id(1L).build();

		victim.getAccount(accountRequest, 20L);
	}
}