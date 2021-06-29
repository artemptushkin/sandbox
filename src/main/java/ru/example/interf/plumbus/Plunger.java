package ru.example.interf.plumbus;

public class Plunger implements Cleaner {
	@Override
	public void clean(Food food) {
		System.out.println("I'm cleaning " + food);
	}
}
