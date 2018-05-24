package World.Animals;

import World.Organism;
import java.util.Random;

public abstract class Animal extends Organism {
    @Override
    public void action() {
        Random generator = new Random();
        int x, y, i;
        this.moving = true;
        do {
            x = 0;
            y = 0;
            while (x == 0 && y == 0 || ((x + this.positionX) < 0 || (y + this.positionY) < 0)) {
                i = generator.nextInt(2);
                if (i == 0) {
                    x = generator.nextInt(3) - 1;
                }
                else {
                    y = generator.nextInt(3) - 1;
                }
            }
            this.newPositionX = x + this.positionX;
            this.newPositionY = y + this.positionY;
        } while (!World.checkPosition(index));
        this.aging();
        this.moving = false;
    }

    @Override
    public boolean isReproducing(){
        return this.reproducing;
    }

    @Override
    public void reproduce(){
        if (World.canMove(this, 1)) {
            String message = "The " + this.name + "'s population has increased";
            World.addMessage(message);
            reproducing = true;
            Random generator = new Random();
            while (!World.checkPosition(index)) {
                int x = 0;
                int y = 0;
                while (x == 0 && y == 0 || ((x + positionX) < 0 || (y + positionY) < 0)) {
                    x = generator.nextInt(3) - 1;
                    y = generator.nextInt(3) - 1;
                }
                newPositionX = x + positionX;
                newPositionY = y + positionY;
            }
            World.addOrganism(newPositionX, newPositionY, this.name);
            this.reproducing = false;
        }
    }

    @Override
    public void collision(Organism OtherOrganism){
        if (this.getClass() != OtherOrganism.getClass()) {
            if (this.strength > OtherOrganism.getStrength()) {
                OtherOrganism.die(this);
            }
            else if (this.strength == OtherOrganism.getStrength()) {
                if (this.age > OtherOrganism.getAge()) {
                    OtherOrganism.die(this);
                }
                else {
                    this.die(OtherOrganism);
                }
            }
            else {
                this.die(OtherOrganism);
            }
        }
        else {
            reproduce();
        }
    }

    @Override
    public void die(Organism Aggressor){
        String aggressorName = Aggressor.getName();
        String message = "Organism " + this.name + " was killed by " + aggressorName;
        World.addMessage(message);
        World.deleteOrganism(this);
    }
}
