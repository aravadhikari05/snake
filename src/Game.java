import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private static final int STARTING_LENGTH = 2;
    private static final int STARTING_POSITION = Canvas.GRID_SIZE/2;

    private int posX, posY,
                dirX, dirY,
                aplX, aplY,
                tail, score;

    private ArrayList<Point> tailList;

    public Game() {
        reset();
        aplX = aplY = 5;
        tailList = new ArrayList<>();
    }

    public void process() {
        posX += dirX;
        posY += dirY;

        checkApl();
        manageList();
        if(wallCollide() || selfCollide()) {
            reset();
        }
    }

    public void checkApl() {
        if(posX == aplX && posY == aplY) {
            Random rand = new Random();
            aplX = rand.nextInt(Canvas.GRID_SIZE);
            aplY = rand.nextInt(Canvas.GRID_SIZE);
            tail++;
            score++;
        }
    }

    public void manageList() {
        tailList.add(0,new Point(posX, posY));
        while(tailList.size() > tail) {
            tailList.remove(tailList.size()-1);
        }
    }

    private boolean wallCollide() {
        return posY > Canvas.GRID_SIZE - 1 || posY < 0 || posX > Canvas.GRID_SIZE - 1 || posX < 0;
    }

    private boolean selfCollide() {
        for(int i = 1; i < tailList.size(); i++) {
            if(posX == tailList.get(i).x && posY == tailList.get(i).y) {
                return true;
            }
        }
        return false;
    }

    private void reset() {
        tail = STARTING_LENGTH;
        posX = posY = STARTING_POSITION;
        dirX = dirY = 0;
        score = 0;
    }

    public class Input extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_W && dirY != 1) {
                dirY = -1;
                dirX = 0;
            } else if(key == KeyEvent.VK_A && dirX != 1) {
                dirY = 0;
                dirX = -1;
            } else if(key == KeyEvent.VK_S && dirY != -1) {
                dirY = 1;
                dirX = 0;
            } else if(key == KeyEvent.VK_D && dirX != -1) {
                dirY = 0;
                dirX = 1;
            }
        }

    }

    public int getAplX() {
        return aplX;
    }

    public int getAplY() {
        return aplY;
    }

    public ArrayList<Point> getTailList() {
        return tailList;
    }

    public int getScore() {
        return score;
    }
}
