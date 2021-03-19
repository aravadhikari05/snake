package snake;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private static final int START_LENGTH = 2;
    private static final int START_POSITION = (int) Math.ceil(1.0 * Canvas.GRID_SIZE/2);
    private static final int START_APPLE = 5;

    private int posX, posY,
                dirX, dirY,
                aplX, aplY,
                tail, score;

    private boolean keyPressed;

    private ArrayList<Point> tailList;

    private File beep;
    private File death;


    public Game() {
        reset();
        beep = new File("src/res/beep.wav");
        death = new File("src/res/death.wav");

        aplX = aplY = START_APPLE;
        tailList = new ArrayList<>();
    }

    public void process() {
        posX += dirX;
        posY += dirY;

        keyPressed = false;

        checkApl();
        manageLength();
        if(wallCollide() || selfCollide()) {
            try {
                if(dirX != 0 || dirY != 0) {
                    playSound(death);
                }
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            reset();
        }
    }

    public void checkApl() {
        if(posX == aplX && posY == aplY) {
            Random rand = new Random();
            try {
                playSound(beep);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            aplX = rand.nextInt(Canvas.GRID_SIZE) + 1;
            aplY = rand.nextInt(Canvas.GRID_SIZE) + 1;

            tail++;
            score++;
        }
    }

    public void manageLength() {
        tailList.add(0,new Point(posX, posY));
        while(tailList.size() > tail) {
            tailList.remove(tailList.size()-1);
        }
    }

    private boolean wallCollide() {
        return posY > Canvas.GRID_SIZE || posY < 1 || posX > Canvas.GRID_SIZE || posX < 1;
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
        keyPressed = false;
        tail = START_LENGTH;
        posX = posY = START_POSITION;
        dirX = dirY = 0;
        score = 0;
    }

    private void playSound(File file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public class Input extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if(keyPressed) {
                return;
            }
            int key = e.getKeyCode();
            if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && dirY != 1) {
                dirY = -1;
                dirX = 0;
            } else if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && dirX != 1) {
                dirY = 0;
                dirX = -1;
            } else if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && dirY != -1) {
                dirY = 1;
                dirX = 0;
            } else if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && dirX != -1) {
                dirY = 0;
                dirX = 1;
            } else {
                return;
            }
            keyPressed = true;
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
