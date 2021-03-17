import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Canvas extends JPanel  {
    private Game game;
    private int posX, posY;
    private int dirX, dirY;
    public static int BLOCK_SIZE = 20;
    private Timer timer;


    public Canvas() {
        initPanel();
    }

    private void initPanel() {
        addKeyListener(new Listener());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(400,400));
        initVariables();
    }

    private void initVariables() {
        dirX = 1;
        dirY = 0;
        posX = posY = 0;
        initTimer();
    }

    private void initTimer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                posX += 20 * dirX;
                posY += 20 * dirY;
                repaint();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 200);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(posX,posY, BLOCK_SIZE, BLOCK_SIZE);
    }

    public class Listener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_W) {
                dirY = -1;
                dirX = 0;
            } else if(key == KeyEvent.VK_A) {
                dirY = 0;
                dirX = -1;
            } else if(key == KeyEvent.VK_S) {
                dirY = 1;
                dirX = 0;
            } else if(key == KeyEvent.VK_D) {
                dirY = 0;
                dirX = 1;
            }
        }
    }


}
