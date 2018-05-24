package World.Animals;

import World.World;

public class Wolf  extends Animal {
	public Wolf(int x, int y, int index, World World) {
		this.age = 0;
		this.initiative = 5;
		this.strength = 9;
		this.index = index;
		this.positionX = x;
		this.positionY = y;
		this.newPositionX = x;
		this.newPositionY = y;
		this.reproducing = false;
		this.World = World;
		this.moving = false;
		this.name = "Wolf";
	}

	public Wolf(int x, int y, int index, World World, int strength, int initiative, int age) {
		this.age = age;
		this.initiative = initiative;
		this.strength = strength;
		this.index = index;
		this.positionX = x;
		this.positionY = y;
		this.newPositionX = x;
		this.newPositionY = y;
		this.reproducing = false;
		this.World = World;
		this.moving = false;
		this.name = "Wolf";
	}
}
