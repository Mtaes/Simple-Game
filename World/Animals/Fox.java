package World.Animals;

import World.World;
import World.Organism;
import java.util.Random;

public class Fox extends Animal {
    public Fox(int x, int y, int index, World World) {
        this.age = 0;
        this.initiative = 7;
        this.strength = 3;
        this.index = index;
        this.positionX = x;
        this.positionY = y;
        this.newPositionX = x;
        this.newPositionY = y;
        this.reproducing = false;
        this.World = World;
        this.moving = false;
        this.name = "Fox";
    }

    public Fox(int x, int y, int index, World World, int strength, int initiative, int age) {
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
        this.name = "Fox";
    }

    @Override
    public void action() {
        Random generator = new Random();
        int x, y, i;
        int tabX[] = new int[2];
        int tabY[] = new int[2];
        tabX[0] = 0;
        tabX[1] = 0;
        tabY[0] = 0;
        tabY[1] = 0;
        this.moving = true;
        do {
            x = 0;
            y = 0;
            while (x == 0 && y == 0 || ((x + this.positionX) < 0 || (y + this.positionY) < 0)) {
                i = generator.nextInt(2);
                if (i == 0) {
                    x = generator.nextInt(3) - 1;
                    if(x != tabX[0] && x != tabX[1]){
                        if (tabX[0] == 0){
                            tabX[0] = x;
                        }
                        else if( tabX[1] == 0){
                            tabX[1] = x;
                        }
                    }
                }
                else {
                    y = generator.nextInt(3) - 1;
                    if(y != tabY[0] && y != tabY[1]) {
                        if (tabY[0] == 0){
                            tabY[0] = y;
                        }
                        else if( tabY[1] ==0) {
                            tabY[1] = y;
                        }
                    }
                }
            }
            this.newPositionX = x + this.positionX;
            this.newPositionY = y + this.positionY;
            if(tabX[0] != 0 && tabX[1] != 0 && tabY[0] != 0 && tabY[1] != 0) {
                break;
            }
        } while (!World.checkPosition(index));
        this.aging();
        this.moving = false;
    }

    @Override
    public void collision(Organism OtherOrganism){
        if (this.getClass() != OtherOrganism.getClass()) {
            if (this.strength == OtherOrganism.getStrength()) {
                if (this.age > OtherOrganism.getAge()) {
                    OtherOrganism.die(this);
                }
                else {
                    die(OtherOrganism);
                }
            }
            else if (this.strength > OtherOrganism.getStrength()) {
                OtherOrganism.die(this);
            }
        }
        else {
            reproduce();
        }
    }
}
