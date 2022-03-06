package Items;

public class Balls {
    private int x;
    private int y;
    private int ballXdir;
    private int ballYdir; 
    private boolean isFireBall;
    
    public Balls(int x, int y, int ballXdir, int ballYdir, boolean isFireBall) {
        this.x = x;
        this.y = y;
        this.ballXdir = ballXdir;
        this.ballYdir = ballYdir;
        this.isFireBall = isFireBall;
    }
    
    public void setX(int x) {
        this.x += x;
    }
    
    public void setY(int y) {
        this.y += y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setBallXdir() {
        this.ballXdir = -this.ballXdir;
    }
    
    public void setBallXdir(int ballXdir) {
        this.ballXdir = ballXdir;
    }
    
    public void setBallYdir() {
        this.ballYdir = -this.ballYdir;
    }
    
    public int getBallXdir() {
        return ballXdir;
    }
    
    public int getBallYdir() {
        return ballYdir;
    }
    
    public void setIsFireBall(boolean isFireBall) {
        this.isFireBall = isFireBall;
    }
    
    public boolean getIsFireBall() {
        return isFireBall;
    }
}