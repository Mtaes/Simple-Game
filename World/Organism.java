package World;

public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected int index;
    protected World World;
    protected int positionX;
    protected int positionY;
    protected int newPositionX;
    protected int newPositionY;
    protected int age;
    protected boolean reproducing;
    protected String name;
    protected boolean moving;

    public abstract void action();

    public abstract void collision(Organism OtherOrganism);

    public abstract boolean isReproducing();

    public abstract void reproduce();

    int getNewX() {
        return this.newPositionX;
    }
    int getNewY() {
        return this.newPositionY;
    }

    public int getX() {
        return this.positionX;
    }

    public int getY() {
        return this.positionY;
    }

    public int getStrength() {
        return this.strength;
    }

    public void increaseStrength(int increase) {
        this.strength += increase;
    }

    public int getInitiative() {
        return this.initiative;
    }

    void moveYourself() {
        positionX = newPositionX;
        positionY = newPositionY;
    }

    public int getIndex() {
        return this.index;
    }

    public int getAge() {
        return this.age;
    }

    public abstract void die(Organism Aggressor);

    public boolean isMoving() {
        return this.moving;
    }

    public String getName() {
        return this.name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.name+"-"+this.strength+"-"+this.initiative+"-"+this.index+"-"+this.positionX+"-"+this.positionY+"-"+this.age;
    }

    protected void aging() {
        this.age++;
    }
}
