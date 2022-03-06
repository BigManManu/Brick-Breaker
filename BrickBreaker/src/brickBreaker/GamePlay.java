package brickBreaker;

import Items.Balls;
import Items.PowerUps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 28;
    private Timer timer;
    private int delay = 8;
    private int playerX = 260;
    private int ballPosX = 300, ballPosY = 530;
    private int ballXdir = -1, ballYdir = -4;
    private MapGenerator map;
    private Random rnd;
    private PowerUps[] power;
    private Balls[] ball;
    private int numberOfBalls = 1;
    private int paddleExtend = 0;
    private int pad = 0;
    private int paddleHit = 0;
    private int mapLevel = 1;
    private boolean isStart;
    
    public GamePlay() {
        isStart = true;
        map = new MapGenerator(4, 7, 1);
        rnd = new Random();
        power = new PowerUps[15];
        ball = new Balls[3];
        ball[0] = new Balls(ballPosX, ballPosY, ballXdir, ballYdir, false);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics g) {
        //Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        //drawing bricks
        map.draw((Graphics2D)g);
        
        //Power ups
        for (int i = 0; i < power.length; i++) {
            if (power[i] == null)
                break;
            else {
                if (power[i].getType() == 1) {
                    g.setColor(Color.yellow);
                    g.fillRect(power[i].getX(), power[i].getY(), 20, 20);
                }
                else if (power[i].getType() == 2) {
                    g.setColor(Color.red);
                    g.fillRect(power[i].getX(), power[i].getY(), 20, 20);
                }
                else if (power[i].getType() == 3) {
                    g.setColor(Color.green);
                    g.fillRect(power[i].getX(), power[i].getY(), 20, 20);
                }
            } 
        }
        
        //Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(681, 0, 3, 592);
        
        //Our paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100 + paddleExtend, 8);
        
        //The ball(s)
        for (int i = 0;  i < ball.length && ball[i] != null; i++) {
            if (ball[i].getIsFireBall())
                g.setColor(Color.red);
            else 
                g.setColor(Color.yellow);
            g.fillOval(ball[i].getX(), ball[i].getY(), 20, 20);
        }
        
        // Bottom Border UI
        g.setColor(Color.white);
        g.fillRect(0, 593, 700, 50);
        
        //Start screen
        if (isStart) {
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("Press [Eneter] To Play", 110, 300);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("1: Easy, 2: Medium, 3: Hard", 150, 340);
        }
        
        //score
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: ", 520, 625);
        g.drawString(""+score, 600, 625);
        
        //Game Over screen
        if (numberOfBalls == 0) {
            play = false;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("Game Over", 250, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press [Enter] to restart", 250, 330);
        }
        
        //Player Wins screen
        if (totalBricks == 0) {
            play = false;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("You WIN!", 250, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press [Enter] to restart", 240, 330);
        }
        
        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            for (int i = 0;  i < ball.length && ball[i] != null; i++) { //Checks collsion between the ball and paddle and where exactly it[ball] hit the paddle
                if (ball[i].getBallXdir() > 0) {
                    if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(1);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                    else if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX + 25 + pad, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(2);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                    else if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX + 50 + pad, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(3);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                    else if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX + 75 + pad, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(4);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                }
                if (ball[i].getBallXdir() < 0) {
                    if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(-4);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                    else if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX + 25 + pad, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(-3);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                    else if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX + 50 + pad, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(-2);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                    else if (new Rectangle(ball[i].getX(), ball[i].getY(), 20, 20).intersects(new Rectangle(playerX + 75 + pad, 550, 25 + pad, 8))) {
                        ball[i].setBallYdir();
                        ball[i].setBallXdir(-1);
                        if (ball[i].getIsFireBall()) {
                            paddleHit++;
                            if (paddleHit > 1) {
                                ball[i].setIsFireBall(false);
                                paddleHit = 0;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < power.length; i++) { //Make the power up fall down
                if (power[i] == null)
                    break;
                else {
                    power[i].setY();
                }
            }
            for (int i = 0; i < power.length; i++) { //Removes power up from array if player cathes it or fall off screen
                if (power[i] == null) 
                    break;
                else if (new Rectangle(power[i].getX(), power[i].getY(), 20, 20).intersects(new Rectangle(playerX, 550, 100 + paddleExtend, 8))) { //Power up collsion with paddle
                    if (power[i].getType() == 3) { //Extend paddle
                        paddleExtend = 40;
                        pad = 10;
                    }
                    else if (power[i].getType() == 2) { //Fireball that can smash through multiple bricks
                        ball[0].setIsFireBall(true);
                    }
                    else if (power[i].getType() == 1) { //Gives player two extra balls (pause)
                        numberOfBalls = 3;
                        int ballType = 0;
                        for (int b = 0; b < ball.length; b++) {
                            if (ball[b] == null) {
                                ball[b] = new Balls(ball[0].getX(), ball[0].getY(), Xdir(ballType), ball[0].getBallYdir(), false);
                                ballType++;
                            }
                        }
                    }
                    int x = i;
                    while (power[x] != null && x != power.length - 1) { //removes power up from array that was collected
                        power[x] = power[x + 1];
                        x++;
                        if (x == power.length - 1)
                            power[x + 1] = null;
                    }
                }
                else if (power[i].getY() > 630) { //If power up goes off screen then remove it from array
                    int x = i;
                    while (power[x] != null && x != power.length - 1) {
                        power[x] = power[x + 1];
                        x++;
                        if (x == power.length - 1)
                            power[x + 1] = null;
                    }
                }
            }
            A: for (int i = 0; i < map.map.length; i++) { //Brick collision detection
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        for (int b = 0;  b < ball.length && ball[b] != null; b++) {
                            int brickX = j * map.brickWidth + 80;
                            int brickY = i * map.brickHeight + 50;
                            int brickWidth = map.brickWidth;
                            int brickHeight = map.brickHeight;
                            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                            Rectangle ballRect = new Rectangle(ball[b].getX(), ball[b].getY(), 20, 20);
                            Rectangle brickRect = rect;
                            if (ballRect.intersects(brickRect)) {
                                if (ball[b].getIsFireBall())
                                    map.setBrickValue(0, i, j);
                                else 
                                    map.setBrickValue(map.map[i][j], i, j);
                                if (map.map[i][j] == 0) { 
                                    totalBricks--;
                                    int powerUpChance = rnd.nextInt(101); 
                                    if (powerUpChance >= 85) {
                                        int ranPowerType = rnd.nextInt(3) + 1;
                                        for (int k = 0; k < power.length; k++) {
                                            if (power[k] == null) {
                                                power[k] = new PowerUps(brickX + 38, brickY, ranPowerType);
                                                break;
                                            }
                                        }
                                    } 
                                }
                                score += 5;
                                if (ball[b].getX() + 16 <= brickX || ball[b].getX() + 3 >= brickX + brickWidth) {
                                    if (ball[b].getIsFireBall())
                                        break A;
                                    else 
                                        ball[b].setBallXdir();
                                }
                                else {
                                    if (ball[b].getIsFireBall())
                                        break A;
                                    else 
                                        ball[b].setBallYdir();
                                }
                                break A;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < ball.length && ball[i] != null; i++) { //Ball movement
                ball[i].setX(ball[i].getBallXdir());
                ball[i].setY(ball[i].getBallYdir());
                if (ball[i].getX() < 0) 
                    ball[i].setBallXdir();
                if (ball[i].getY() < 0) 
                    ball[i].setBallYdir();
                if (ball[i].getX() > 670) 
                    ball[i].setBallXdir();
                if (ball[i].getY() > 630) {
                    numberOfBalls--;
                    for (int b = i; b < ball.length; b++) { 
                        if (b == ball.length - 1) 
                            ball[b] = null;
                        else 
                            ball[b] = ball[b + 1];
                    }
                }
            }
        }
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) { //Player Controls
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 580 - paddleExtend) {
                playerX = 580 - paddleExtend;
            }
            else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 5) {
                playerX = 5;
            }
            else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { //Starts and resets the game
            if (!play) {
                isStart = false;
                play = true;
                ball[0] = new Balls(300, 530, -1, -4, false);
                numberOfBalls = 1;
                playerX = 260;
                pad = 0;
                paddleExtend = 0;
                score = 0;
                totalBricks = 28;
                map = new MapGenerator(4, 7, mapLevel);
                for (int i = 0; i < power.length && power[i] != null; i++)
                    power[i] = null;
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_1) //Selecting level of difficulty 
            mapLevel = 1; //Easy
        if (e.getKeyCode() == KeyEvent.VK_2) 
            mapLevel = 2; //Medium
        if (e.getKeyCode() == KeyEvent.VK_3) 
            mapLevel = 3; //Hard
    }
    
    public void moveRight() {
        if (play)
            playerX += 20;
    }
    
    public void moveLeft() {
        if (play)
            playerX -= 20;
    }
    
    public int Xdir(int ballType) { //Getting seperate X directions for the multi ball
        int direction = ball[0].getBallXdir();
        if (ballType == 0) {
            switch (direction) {
                case -4: return -3;
                case -3: return -4;
                case -2: return -3;
                case -1: return -2;
                case 1: return 2;
                case 2: return 3;
                case 3: return 4;
                case 4: return 3;              
            }
        }
        else {
            switch (direction) {
                case -4: return -2;
                case -3: return -2;
                case -2: return -1;
                case -1: return 1;
                case 1: return -1;
                case 2: return 1;
                case 3: return 2;
                case 4: return 2;  
            }
        }
        return -1;
    }

    @Override
    public void keyReleased(KeyEvent e) {}//Not used but it's required to have it here
    @Override
    public void keyTyped(KeyEvent e) {}//Not used but it's required to have it here
    
}