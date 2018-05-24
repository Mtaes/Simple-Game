package World;
import java.util.Random;
import World.Animals.*;
import World.Plants.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class World {
    private Organism[] ContainerOfOrganisms;
    private Organism[] Board;
    private String[] species;
    private int numberOfSpecies;
    private int quantityOfOrganisms;
    private int capacity;
    private String messages;
    private int sizeX;
    private int sizeY;
    private Window Window;

    public World() {
        this.quantityOfOrganisms = 0;
        this.sizeX = 20;
        this.sizeY = 20;
        this.messages = "";
        this.capacity = sizeX * sizeX;
        this.Board = new Organism[sizeX * sizeX];
        this.ContainerOfOrganisms = new Organism[this.capacity];
        this.numberOfSpecies = 9;
        this.species = new String[numberOfSpecies];
        this.species[0] = "Human";
        this.species[1] = "Fox";
        this.species[2] = "Grass";
        this.species[3] = "Guarana";
        this.species[4] = "Sheep";
        this.species[5] = "Sow thistle";
        this.species[6] = "Turtle";
        this.species[7] = "Wolf";
        this.species[8] = "Wolf berries";
        for (int i = 0; i < sizeX * sizeY; i++) {
            Board[i] = null;
            if (i < this.capacity) {
                ContainerOfOrganisms[i] = null;
            }
        }
        this.Window = new Window(this, Board, sizeX, sizeY);
        spawn();
    }

    public void addOrganism(int x, int y, String type) {
        if (this.quantityOfOrganisms == this.capacity) {
            return;
        }
        int index = this.quantityOfOrganisms;
        Organism Organism = null;
        switch (type) {
            case "Fox": Organism = new Fox(x, y, index, this);
                break;
            case "Grass": Organism = new Grass(x, y, index, this);
                break;
            case "Guarana": Organism = new Guarana(x, y, index, this);
                break;
            case "Sheep": Organism = new Sheep(x, y, index, this);
                break;
            case "Sow thistle": Organism = new Sow_thistle(x, y, index, this);
                break;
            case "Turtle": Organism = new Turtle(x, y, index, this);
                break;
            case "Wolf": Organism = new Wolf(x, y, index, this);
                break;
            case "Wolf berries": Organism = new Wolf_berries(x, y, index, this);
                break;
            case "Human": Organism = new Human(x, y, index, this);
                break;
        }
        this.ContainerOfOrganisms[quantityOfOrganisms] = Organism;
        this.quantityOfOrganisms++;
        index = x + y*sizeX;
        this.Board[index] = Organism;
    }

    public void deleteOrganism(Organism OtherOrganism) {
        int index = OtherOrganism.getIndex();
        ContainerOfOrganisms[index] = null;
        int x = OtherOrganism.getX();
        int y = OtherOrganism.getY();
        index = x + y*sizeX;
        Board[index] = null;
        quantityOfOrganisms--;
    }

    public boolean checkPosition(int i) {
        if (i < 0 || i > this.capacity || ContainerOfOrganisms[i] == null) {
            return false;
        }
        int newX = ContainerOfOrganisms[i].getNewX();
        int newY = ContainerOfOrganisms[i].getNewY();
        if (newX >= sizeX || newY >= sizeY || newX < 0 || newY < 0) {
            return false;
        }
        int index = newX + newY*sizeX;
        if (Board[index] != null) {
            if (!ContainerOfOrganisms[i].isReproducing()) {
                ContainerOfOrganisms[i].collision(Board[index]);
            }
            else {
                return false;
            }
            if (ContainerOfOrganisms[i] != null && Board[index] == null && ContainerOfOrganisms[i].isMoving() && !ContainerOfOrganisms[i].isReproducing()) {
                moveOrganism(i, newX, newY);
                return true;
            }
            else {
                return true;
            }
        }
        else {
            if (!ContainerOfOrganisms[i].isReproducing() && ContainerOfOrganisms[i].isMoving()) {
                moveOrganism(i, newX, newY);
                return true;
            }
            else {
                return true;
            }
        }
    }

    public boolean canMove(Organism Organism, int radius) {
        int x = Organism.getX();
        int y = Organism.getY();
        int index = x + y*sizeX;
        if((index + radius) >= Board.length || (index - radius) < 0 || (index >= Board.length)) {
            return false;
        }
        else {
            if (x - radius < 0) {
                return Board[index + radius] == null || Board[index - radius * sizeX] == null || Board[index + radius * sizeX] == null;
            }
            else if (x + radius >= sizeX) {
                return Board[index - radius] == null || Board[index - radius * sizeX] == null || Board[index + radius * sizeX] == null;
            }
            else if (y + radius >= sizeY) {
                return Board[index - radius] == null || Board[index + radius] == null || Board[index - radius * sizeX] == null;
            }
            else if (y - radius < 0) {
                return Board[index - radius] == null || Board[index + radius] == null || Board[index + radius * sizeX] == null;
            }
            else {
                return Board[index + radius] == null || Board[index - radius] == null || Board[index - radius * sizeX] == null || Board[index + radius * sizeX] == null;
            }
        }
    }

    public void addMessage(String message) {
        this.messages += "<br>";
        this.messages += message;
        Window.re();
    }

    void nextTurn(){
        prepareContainer();
        for (int i = 0; i < quantityOfOrganisms; i++) {
            if (ContainerOfOrganisms[i] != null) {
                ContainerOfOrganisms[i].action();
            }
        }
        Window.re();
        messages = "";
    }

    public static void main(String args[]) {
        World World = new World();
    }

    String getMessages() {
        return this.messages;
    }

    void read() throws IOException {
        FileInputStream fs = null;

        try {
            fs= new FileInputStream("Save.txt");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            addMessage("File not found!");
            Window.re();
            return;
        }
        this.Window.delete();


        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        this.quantityOfOrganisms = 0;

        String tmp = br.readLine();
        tmp.trim();
        int quantityOfOrganisms = Integer.parseInt(tmp);
        System.out.println(quantityOfOrganisms+1);
        this.capacity = Integer.parseInt(br.readLine());
        this.sizeX = Integer.parseInt(br.readLine());
        this.sizeX = Integer.parseInt(br.readLine());
        this.Board = new Organism[this.sizeX * this.sizeX];
        this.ContainerOfOrganisms = new Organism[this.capacity];

        for(int i = 0; i < quantityOfOrganisms; ++i) {
            String name=br.readLine();

            System.out.println(name);
            if("Human".equals(name)) System.out.println("World.World.read()-----------------------------");
            int strength=Integer.parseInt(br.readLine());
            System.out.println(strength);
            System.out.println(strength+1);
            int initiative=Integer.parseInt(br.readLine());
            int index=Integer.parseInt(br.readLine());
            int x=Integer.parseInt(br.readLine());
            int y=Integer.parseInt(br.readLine());
            int age=Integer.parseInt(br.readLine());
            int timer = 0;
            if("Human".equals(name)) {
                timer=Integer.parseInt(br.readLine());
            }
            Organism Organism = null;

            switch (name) {
                case "Fox": Organism = new Fox(x, y, index, this, strength, initiative, age);
                    break;
                case "Grass": Organism = new Grass(x, y, index, this, strength, initiative, age);
                    break;
                case "Guarana": Organism = new Guarana(x, y, index, this, strength, initiative, age);
                    break;
                case "Sheep": Organism = new Sheep(x, y, index, this, strength, initiative, age);
                    break;
                case "Sow thistle": Organism = new Sow_thistle(x, y, index, this, strength, initiative, age);
                    break;
                case "Turtle": Organism = new Turtle(x, y, index, this, strength, initiative, age);
                    break;
                case "Wolf": Organism = new Wolf(x, y, index, this, strength, initiative, age);
                    break;
                case "Wolf berries": Organism = new Wolf_berries(x, y, index, this, strength, initiative, age);
                    break;
                case "Human": Organism = new Human(x, y, index, this, strength, initiative, age, timer);
                    break;
            }
            this.ContainerOfOrganisms[this.quantityOfOrganisms] = Organism;
            this.quantityOfOrganisms++;
            index = x + y*sizeX;
            this.Board[index] = Organism;

        }
        this.Window = new Window(this, Board, sizeX, sizeY);
    }

    void write() throws IOException {
        this.prepareContainer();
        File file = new File("Save.txt");
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        writer.println(this.quantityOfOrganisms);
        writer.println(this.capacity);
        writer.println(this.sizeX);
        writer.println(this.sizeX);
        for(int i =0; i<quantityOfOrganisms; i++) {
            String[] words = ContainerOfOrganisms[i].toString().split("-");
            for (String word: words) {
                writer.println(word);
            }
        }
        writer.close();
    }

    void restart() {
        this.quantityOfOrganisms = 0;
        this.Board = new Organism[sizeX * sizeX];
        this.ContainerOfOrganisms = new Organism[this.capacity];
        for (int i = 0; i < sizeX * sizeY; i++) {
            Board[i] = null;
            if (i < this.capacity) ContainerOfOrganisms[i] = null;
        }
        this.Window = new Window(this, Board, sizeX, sizeY);
        spawn();
    }

    private void spawn() {
        Random generator = new Random();
        int n = capacity / 6;
        int x, y;
        do {
            x = generator.nextInt(sizeX);
            y = generator.nextInt(sizeY);
        } while (Board[x + sizeY*y] != null);
        addOrganism(x, y, species[0]);
        for (int j = 1; j < numberOfSpecies; j++) {
            for (int i = 0; i < n / 9; i++) {
                do {
                    x = generator.nextInt(sizeX);
                    y = generator.nextInt(sizeY);
                } while (Board[x + sizeY*y] != null);
                addOrganism(x, y, species[j]);
            }
        }
    }

    private void prepareContainer() {
        int n = this.capacity;
        do {
            for (int i = 0; i < n - 1; i++) {
                if (ContainerOfOrganisms[i] == null && ContainerOfOrganisms[i + 1] != null) {
                    Organism tmp = ContainerOfOrganisms[i];
                    ContainerOfOrganisms[i] = ContainerOfOrganisms[i + 1];
                    ContainerOfOrganisms[i + 1] = tmp;
                }
            }
            n--;
        } while (n > 0);

        n = this.quantityOfOrganisms;
        for (int i = 0; i < n; i++) {
            if (ContainerOfOrganisms[i] == null) {
                this.quantityOfOrganisms--;
            }
        }

        n = this.quantityOfOrganisms;
        do {
            for (int j = 0; j < n - 1; j++) {
                if (ContainerOfOrganisms[j].getInitiative() < ContainerOfOrganisms[j + 1].getInitiative()) {
                    Organism tmp = ContainerOfOrganisms[j];
                    ContainerOfOrganisms[j] = ContainerOfOrganisms[j + 1];
                    ContainerOfOrganisms[j + 1] = tmp;
                }
                else if (ContainerOfOrganisms[j].getInitiative() == ContainerOfOrganisms[j + 1].getInitiative()) {
                    if (ContainerOfOrganisms[j].getAge() < ContainerOfOrganisms[j + 1].getAge()) {
                        Organism tmp = ContainerOfOrganisms[j];
                        ContainerOfOrganisms[j] = ContainerOfOrganisms[j + 1];
                        ContainerOfOrganisms[j + 1] = tmp;
                    }
                }
            }
            --n;
        } while (n > 0);
        for (int i = 0; i < this.quantityOfOrganisms; i++) {
            ContainerOfOrganisms[i].setIndex(i);
        }
    }

    private void moveOrganism(int i, int newX, int newY) {
        int index = ContainerOfOrganisms[i].getX() + ContainerOfOrganisms[i].getY()*sizeX;
        Board[index] = null;
        index = newX + newY*sizeX;
        Board[index] = ContainerOfOrganisms[i];
        ContainerOfOrganisms[i].moveYourself();
    }
}
