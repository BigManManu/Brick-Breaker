package brickBreaker;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();
        obj.setBounds(10, 10, 700, 680);
        obj.setTitle("Brick Breaker (Prototype)");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }
}
