package ru.example.interf.plumbus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultPlumbus implements Plumbus {
	private final Cleaner cleaner;
	private final Warmer warmer;

	@Override
	public void clean(Food food) {
		if (food.weight > 0) {
			cleaner.clean(food);
		} else {
			System.out.println("I don't clean something that weight less then 0");
		}
	}

	@Override
	public void warm(Food food) {
		if (food.weight > 0) {
			warmer.warm(food);
		} else {
			System.out.println("I don't warm something that weight less then 0");
		}
	}
}
