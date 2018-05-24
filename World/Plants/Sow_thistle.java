package World.Plants;

import World.World;
import java.util.Random;

public class Sow_thistle extends Plant {
    public Sow_thistle(int x, int y, int index, World World) {
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
        this.name = "Sow thistle";
    }

    public Sow_thistle(int x, int y, int index, World World, int strength, int initiative, int age) {
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
        this.name = "Sow thistle";
    }

    @Override
    public void action() {
        Random generator = new Random();
        int isReproducing = -1;
        for (int i = 0; i < 3; i++) {
            isReproducing = generator.nextInt(30);
        }
        if (isReproducing == 0) {
            reproducing = true;
            int x;
            int y;
            int i;
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
            reproducing = false;
            String message = "The " + this.name + " population has increased";
            World.addMessage(message);
            World.addOrganism(newPositionX, newPositionY, this.name);
        }
        this.aging();
    }
}
