package ru.example.interf.repository.domain;

import lombok.Data;
import ru.example.interf.domain.AmountEntity;

@Data
public class AccountEntity {
	private AmountEntity amount;
}
