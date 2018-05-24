package World.Plants;

import World.Organism;
import java.util.Random;

public class Plant extends Organism {
    @Override
    public void action() {
        Random generator = new Random();
        int isReproducing = generator.nextInt(30);
        if (isReproducing == 0) {
            if (World.canMove(this, 1)) {
                String message = "The " + this.name + " population has increased";
                World.addMessage(message);
                reproducing = true;
                int x, y, i;
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
                this.reproducing = false;
                World.addOrganism(newPositionX, newPositionY, this.name);
            }
        }
        this.aging();
    }

    @Override
    public boolean isReproducing() {
        return this.reproducing;
    }

    @Override
    public void reproduce() {
    }

    @Override
    public void collision(Organism OtherOrganism) {
    }

    @Override
    public void die(Organism Aggressor) {
        String aggressorName = Aggressor.getName();
        String message = "The organism " + name + " was eaten by " + aggressorName;
        World.addMessage(message);
        World.deleteOrganism(this);
    }
}