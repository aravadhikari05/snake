import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Canvas extends JPanel  {

    public static final int TILE_SIZE = 20; //size of each tile
    public static final int GRID_SIZE = 19; // number of tiles on a board
    public static final int WINDOW_SIZE = TILE_SIZE * GRID_SIZE + (2 * TILE_SIZE); //size of window
    public static final int INTERVAL = 100;

    private Game game;
    private Timer timer;

    public Canvas() {
        game = new Game();
        initPanel();
        initTimer();
    }
    //setup panel
    private void initPanel() {
        addKeyListener(game. new Input());
        setBackground(new Color(30, 0, 0));
        setPreferredSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE));
        setFocusable(true);
    }
    //setup timer
    private void initTimer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                game.process();
                repaint();
            }
        };
        timer.scheduleAtFixedRate(task, 0, INTERVAL);
    }
    //graphics go here
    @Override
    public void paintComponent(Graphics g) {
        //must call super class
        super.paintComponent(g);
        //cast Graphics2D object to utillize basicStroke
        Graphics2D g2d = (Graphics2D) g;
        //draw the grid
        for(int i = 1; i <= GRID_SIZE; i++) {
            for(int j = 1; j <= GRID_SIZE; j++) {
                g2d.setColor(new Color(60, 10, 10));
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    g2d.fillRect(j * TILE_SIZE,i * TILE_SIZE,TILE_SIZE, TILE_SIZE);
                }
            }
        }
        //draw the apple
        g2d.setColor(new Color(10, 75, 200));
        g2d.fillRect(game.getAplX() * TILE_SIZE + 1 , game.getAplY() * TILE_SIZE + 1, TILE_SIZE-2, TILE_SIZE-2);
        //draw the snake
        g2d.setColor(new Color(20, 160, 15));
        for(int i = 0; i < game.getTailList().size(); i++) {
            g2d.fillRect(game.getTailList().get(i).x * TILE_SIZE + 1,game.getTailList().get(i).y * TILE_SIZE + 1, TILE_SIZE-2, TILE_SIZE-2);
        }
        //draw the border
        g2d.setColor(new Color(15, 5, 5));
        g2d.setStroke(new BasicStroke(TILE_SIZE));
        g2d.drawRect(TILE_SIZE/2,TILE_SIZE/2, WINDOW_SIZE-TILE_SIZE, WINDOW_SIZE-TILE_SIZE);
        //draw the text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Courier", Font.PLAIN, 18));
        g2d.drawString("" + game.getScore(), 5 , 20);

    }
}
