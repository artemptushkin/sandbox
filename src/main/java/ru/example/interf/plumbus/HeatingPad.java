package ru.example.interf.plumbus;

public class HeatingPad implements Warmer {
	@Override
	public void warm(Food food) {
		System.out.println("I'm warming " + food);
	}
}
