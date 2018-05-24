package World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Board extends JPanel implements MouseListener {
    private int x, y, radius;
    private int sizeX , sizeY, frameSize, sizePx, sizePy;
    private Window Window;

    Board(int sizeX, int sizeY, int sizePx, int sizePy, Window Window) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizePx = sizePx;
        this.sizePy = sizePy;
        this.frameSize = sizePx/sizeX;
        this.radius = frameSize/2 - 5;
        this.x = frameSize/2 - radius;
        this.y = frameSize/2 - radius;
        this.Window = Window;
        abc();
    }

    private void abc() {
        addMouseListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        for(int y = 0; y < sizePx; y+=frameSize) {
            g.drawLine(0, y, sizePx, y);
        }
        for(int x = 0; x < sizePy; x+=frameSize) {
            g.drawLine(x, 0, x, sizePy);
        }
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++){
                x = frameSize/2 - radius + i*frameSize;
                y = frameSize/2 - radius + j*frameSize;
                if(Window.check(i + sizeX * j)){
                    String name = Window.getName(i + sizeX*j);
                    switch(name){
                        case "Human": g.setColor(Color.BLACK);
                            break;
                        case "Fox": g.setColor(Color.BLUE);
                            break;
                        case "Grass": g.setColor(Color.GREEN);
                            break;
                        case "Guarana": g.setColor(Color.CYAN);
                            break;
                        case "Sheep": g.setColor(Color.WHITE);
                            break;
                        case "Sow thistle": g.setColor(Color.YELLOW);
                            break;
                        case "Turtle": g.setColor(Color.RED);
                            break;
                        case "Wolf": g.setColor(Color.GRAY);
                            break;
                        case "Wolf berries": g.setColor(Color.LIGHT_GRAY);
                            break;
                    }
                    g.drawRect(x,y,2*radius,2*radius);
                    g.fillRect(x,y,2*radius,2*radius);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+" "+y+" "+((x/frameSize)+sizeX*(y/frameSize)));
        x = x/frameSize;
        y = (y/frameSize);
        if(!Window.check(x + sizeX * y)){
            prepareChoice(x,y);
        }
        repaint();
    }

    private void prepareChoice( int positionX, int positionY) {
        JFrame newFrame = new JFrame();
        Object[] options = {"Fox", "Sheep", "Turtle", "Wolf", "Wolf berries", "Sow thistle", "Grass", "Guarana"};
        String organism = (String) JOptionPane.showInputDialog(
                newFrame, "Choose organism to add:", "New organism", JOptionPane.PLAIN_MESSAGE, null, options, "Fox"
        );
        Window.add(organism,positionX,positionY);
    }

}


class Window {
    private JFrame frame;
    private JPanel panel, panel2, panel3;
    private Board board;
    private JButton save, load, turn, newGame;
    private JLabel message;
    private int boardSizeX, boardSizeY;
    private World World;
    private Organism[] Organisms;

    Window(World World, Organism[] Organisms, int boardSizeX, int boardSizeY) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        this.World = World;
        this.Organisms = Organisms;
        prepareFrame();
    }

    boolean check(int i) {
        return Organisms[i] != null;
    }

    String getName(int i) {
        return Organisms[i].getName();
    }

    void add(String organism, int x, int y) {
        if(organism != null){
            World.addOrganism(x, y, organism);
        }
    }

    void re() {
        message.setText("<html>"+World.getMessages()+"</html>");
        message.revalidate();
        panel.repaint();
        board.repaint();
    }

    void delete() {
        frame.dispose();
    }

    private void prepareFrame() {
        frame = new JFrame();
        frame.setSize(800,900);
        frame.setTitle("Game");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        turn = new JButton("Next turn");
        turn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                World.nextTurn();
            }
        });
        turn.setFocusable(false);

        panel2.add(turn);
        panel2.setBounds(370, 470, 100, 70);
        frame.add(panel2);

        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    World.write();
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        save.setFocusable(false);

        load = new JButton("Load");
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    World.read();
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        load.setFocusable(false);

        newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                World.restart();

            }
        });
        newGame.setFocusable(false);

        panel3.add(load);
        panel3.add(save);
        panel3.add(newGame);

        panel3.setBounds(600, 200, 100, 100);
        frame.add(panel3);
        message = new JLabel("");
        panel.setBounds(20, 500, 750, 400);
        panel.add(message);

        frame.add(panel);
        int sizefieas = 451/boardSizeX;
        int sizePx = boardSizeX*sizefieas + 1;
        int sizePy = boardSizeY*sizefieas + 1;
        board = new Board(boardSizeX, boardSizeY, sizePx, sizePy, this);

        board.setBounds(20, 20, sizePx, sizePy);
        frame.add(board);



        frame.setVisible(true);
    }

}
