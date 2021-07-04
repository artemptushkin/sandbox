package ru.example.interf.plumbus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomesticService {
	private final Warmer warmer;
	private final Cleaner cleaner;

	public void doDomesticWork(Food food) {
		cleaner.clean(food);
		warmer.warm(food);
	}
}
