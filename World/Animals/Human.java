package World.Animals;

import World.World;
import World.Organism;
import World.Controller;

public class Human extends Animal {
    private boolean power;
    private int timer;

    public Human(int x, int y, int index, World World){
        this.age = 0;
        this.initiative = 4;
        this.strength = 5;
        this.index = index;
        this.positionX = x;
        this.positionY = y;
        this.newPositionX = x;
        this.newPositionY = y;
        this.reproducing = false;
        this.World = World;
        this.power = false;
        this.moving = false;
        this.name = "Human";
        this.timer = 0;
    }

    public Human(int x, int y, int index, World World, int strength, int initiative, int age, int timer) {
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
        this.power = false;
        this.moving = false;
        this.name = "Human";
        this.timer = timer;
    }

    @Override
    public void action() {
        this.moving = true;
        int x = 0;
        int y = 0;
        Controller read = new Controller();
        int key = 0;
        boolean end = false;
        do {
            while (x == 0 && y == 0 || ((x + positionX) < 0 || (y + positionY) < 0)) {
                key = read.getKey();
                switch (key) {
                    case 38: y = -1;
                        break;
                    case 39: x = 1;
                        break;
                    case 40: y = 1;
                        break;
                    case 37: x = -1;
                        break;
                }
                if (key == 67 && timer == 0) {
                    this.usePower();
                    end = true;
                    timer = 10;
                    break;
                }
                else if (key == 67 && timer != 0) {
                    String message = "Power isn't ready!";
                    World.addMessage(message);
                }
            }
            newPositionX = x + positionX;
            newPositionY = y + positionY;
            if (key != 99) {
                end = World.checkPosition(index);
            }
        } while (!end);
        if (timer <= 10 && timer > 5) {
            this.usePower();
        }
        this.aging();
        this.moving = false;
        if (timer > 0) {
            timer--;
        }
    }

    @Override
    public void collision(Organism OtherOrganism){
        if (!power) {
            if (this.getClass() != OtherOrganism.getClass()) {
                if (this.strength > OtherOrganism.getStrength()) {
                    OtherOrganism.die(this);
                }
                else if (this.strength == OtherOrganism.getStrength()) {
                    if (this.age > OtherOrganism.getAge()) {
                        OtherOrganism.die(this);
                    }
                    else {
                        die(OtherOrganism);
                    }
                }
                else {
                    die(OtherOrganism);
                }
            }
        }
        else {
            String message = "Organism " + this.name + " killed " + OtherOrganism.getName() + " using special power";
            World.addMessage(message);
            World.deleteOrganism(OtherOrganism);
        }
    }

    @Override
    public String toString() {
        return this.name+"-"+this.strength+"-"+this.initiative+"-"+this.index+"-"+this.positionX+"-"+this.positionY+"-"+this.age+"-"+this.timer;
    }

    private void usePower() {
        String message = "Organism " + name + " used special power.";
        World.addMessage(message);
        this.moving = false;
        this.power = true;
        newPositionX = 1 + positionX;
        newPositionY = positionY;
        World.checkPosition(index);
        newPositionX = (-1) + positionX;
        newPositionY = positionY;
        World.checkPosition(index);
        newPositionX = positionX;
        newPositionY = 1 + positionY;
        World.checkPosition(index);
        newPositionX = positionX;
        newPositionY = (-1) + positionY;
        World.checkPosition(index);
        this.moving = true;
        this.power = false;
    }
}
