package World.Plants;

import World.World;
import World.Organism;

public class Guarana extends Plant {
	public Guarana(int x, int y, int index, World World) {
		this.age = 0;
		this.initiative = 0;
		this.strength = 0;
		this.index = index;
		this.positionX = x;
		this.positionY = y;
		this.newPositionX = x;
		this.newPositionY = y;
		this.reproducing = false;
		this.World = World;
		this.moving = false;
		this.name = "Guarana";
	}

	public Guarana(int x, int y, int index, World World, int strength, int initiative, int age) {
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
		this.name = "Guarana";
	}

	@Override
	public void die(Organism Aggressor) {
		String aggressorName = Aggressor.getName();
		String message = "The organism " + name + " was eaten by " + aggressorName;
		World.addMessage(message);
		message = "The " + aggressorName + "'s strength has been increased";
		World.addMessage(message);
		Aggressor.increaseStrength(3);
		World.deleteOrganism(this);
	}
}
