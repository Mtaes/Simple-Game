package World.Animals;

import World.World;

public class Sheep  extends Animal {
	public Sheep(int x, int y, int index, World World) {
		this.age = 0;
		this.initiative = 4;
		this.strength = 4;
		this.index = index;
		this.positionX = x;
		this.positionY = y;
		this.newPositionX = x;
		this.newPositionY = y;
		this.reproducing = false;
		this.World = World;
		this.moving = false;
		this.name = "Sheep";
	}

	public Sheep(int x, int y, int index, World World, int strength, int initiative, int age) {
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
		this.name = "Sheep";
	}
}
