import greenfoot.*;
public class Wall extends Actor {
    private Cell cell;
    private int index;
    public Wall(int length, boolean side ,Cell cell, int arrayIndex) {
        getImage().setColor(Color.BLACK);
        GreenfootImage img = side?new GreenfootImage(1,length):new GreenfootImage(length,1);
        setImage(img);
        getImage().fill();
        this.cell = cell;
        this.index = arrayIndex;
    }

    public Cell getCell() {
        return cell;
    }

    public int getIndex() {
        return index;
    }

    public void makeTransparnet() {
        getImage().setTransparency(10);
    }
}
