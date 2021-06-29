package ru.example.interf.plumbus;

public class PlumbusRaw {

	public void clean(Food food) {
		if (food.weight > 0) {
			System.out.println("I'm cleaning " + food);
		} else {
			System.out.println("I don't clean something that weight less then 0");
		}
	}

	public void warm(Food food) {
		if (food.weight > 0) {
			System.out.println("I'm warming " + food);
		} else {
			System.out.println("I don't warm something that weight less then 0");
		}
	}
}
