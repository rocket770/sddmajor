import greenfoot.*;
public class Wall extends Actor {
    private Cell cell;
    private int index;
    public Wall(int length, boolean side ,Cell cell, int arrayIndex) {
        getImage().setColor(Color.BLACK);
        GreenfootImage img = side?new GreenfootImage(1,length):new GreenfootImage(length,1); // draw a sraight lines either horizontally or vertically
        setImage(img);
        getImage().fill();
        this.cell = cell; /// give it the cell it represents
        this.index = arrayIndex; // give it the value in the array of the cell it represents
    }

    public Cell getCell() {
        return cell;
    }

    public int getIndex() {
        return index;
    }

    public void makeTransparnet() {
        getImage().setTransparency(10); // make image transparent for level editor 
    }
}
