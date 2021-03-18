package snake;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Canvas extends JPanel  {

    public static final int TILE_SIZE = 20; //size of each tile
    public static final int GRID_SIZE = 19; // number of tiles on a board
    public static final int WINDOW_SIZE = TILE_SIZE * GRID_SIZE; //size of window

    private Game game;
    private Timer timer;

    public Canvas() {
        game = new Game();
        initPanel();
        initTimer();
    }

    private void initPanel() {
        addKeyListener(game. new Input());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE));
        setFocusable(true);
    }

    private void initTimer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                game.process();
                repaint();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                g.setColor(Color.DARK_GRAY);
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    g.fillRect(j * TILE_SIZE,i * TILE_SIZE,TILE_SIZE, TILE_SIZE);
                }
            }
        } */

        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.PLAIN, 20));
        g.drawString("" + game.getScore(), 5 , 20);

        g.setColor(Color.RED);
        g.fillRect(game.getAplX() * TILE_SIZE + 1 , game.getAplY() * TILE_SIZE + 1, TILE_SIZE-2, TILE_SIZE-2);

        g.setColor(Color.GREEN);
        for(int i = 0; i < game.getTailList().size(); i++) {
            g.fillRect(game.getTailList().get(i).x * TILE_SIZE + 1,game.getTailList().get(i).y * TILE_SIZE + 1, TILE_SIZE-2, TILE_SIZE-2);
        }
    }
}
