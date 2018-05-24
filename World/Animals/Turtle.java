package World.Animals;

import World.World;
import World.Organism;
import java.util.Random;

public class Turtle extends Animal {
	public Turtle(int x, int y, int index, World World) {
		this.age = 0;
		this.initiative = 1;
		this.strength = 2;
		this.index = index;
		this.positionX = x;
		this.positionY = y;
		this.newPositionX = x;
		this.newPositionY = y;
		this.reproducing = false;
		this.World = World;
		this.moving = false;
		this.name = "Turtle";
	}

	public Turtle(int x, int y, int index, World World, int strength, int initiative, int age) {
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
		this.name = "Turtle";
	}

	@Override
	public void action() {
		Random generator = new Random();
		int x, y;
		int isMoving = generator.nextInt(3);
		if (isMoving == 0) {
			int i;
			this.moving = true;
			do {
				x = 0;
				y = 0;
				while (x == 0 && y == 0 || ((x + positionX) < 0 || (y + positionY) < 0)) {
					i = generator.nextInt(2);
					if (i == 0) {
						x = generator.nextInt(3) - 1;
					}
					else {
						y = generator.nextInt(3) - 1;
					}
				}
				newPositionX = x + positionX;
				newPositionY = y + positionY;
			} while (!World.checkPosition(index));
			this.moving = false;
		}
		this.aging();
	}

	@Override
	public void die(Organism Aggressor){
		int aggressorStrength = Aggressor.getStrength();
		if (aggressorStrength >= 5) {
			super.die(Aggressor);
		}
		else {
			String aggressorName = Aggressor.getName();
			String message = "Organism " + name + " defended itself against " + aggressorName;
			World.addMessage(message);
		}
	}
}
