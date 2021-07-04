package ru.example.interf.plumbus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomesticService {
	private final Plumbus plumbus;

	public void doDomesticWork(Food food) {
		plumbus.clean(food);
		plumbus.warm(food);
	}
}
