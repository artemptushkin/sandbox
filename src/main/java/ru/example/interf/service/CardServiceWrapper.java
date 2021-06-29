package ru.example.interf.service;

public class CardServiceWrapper extends DefaultCardService {
	public CardServiceWrapper(CardStorageService cardStorageService) {
		super(cardStorageService);
	}
}
