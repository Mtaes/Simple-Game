package World;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Controller {
    private int key = 0;

    public int getKey() {
        JDialog dialog;
        JLabel text = new JLabel("Waiting for player...", SwingConstants.CENTER);
        dialog = new JDialog();
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setUndecorated(true);
        JPanel dialog_panel = new JPanel();
        dialog_panel.add(text);
        dialog.add(dialog_panel);
        dialog.setResizable(false);
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == 37 || e.getKeyCode() == 40 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 67) {
                    key = e.getKeyCode();
                    dialog.dispose();
                }
            }
        });
        dialog.setVisible(true);
        return key;
    }
}
