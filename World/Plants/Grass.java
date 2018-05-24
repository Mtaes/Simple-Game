package World.Plants;

import World.World;

public class Grass extends Plant {
    public Grass(int x, int y, int index, World World) {
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
        this.name = "Grass";
    }

    public Grass(int x, int y, int index, World World, int strength, int initiative, int age) {
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
        this.name = "Grass";
    }
}