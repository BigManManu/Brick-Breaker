package Items;

public class PowerUps {
    private int x, y;
    private int type;
    
    public PowerUps(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public int getX() {
        return x;
    }
    
    public void setY() {
        this.y += 2;
    }
    
    public int getY() {
        return y;
    }
    
    public int getType() {
        return type;
    }
}