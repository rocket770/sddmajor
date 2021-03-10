import greenfoot.Color;
import greenfoot.GreenfootImage;
import rccookie.game.AdvancedActor;

public class Wall extends AdvancedActor {
    private static final long serialVersionUID = 1L;
    Cell cell;
    int index;
    public Wall(int length, boolean side ,Cell cell, int arrayIndex) {
        getImage().setColor(Color.BLACK);//new greenfoot.Color(0, 0, 0, 80));
        if(!side){
            setImage(new GreenfootImage(length, 1));
        } else {
            setImage(new GreenfootImage(1, length));
        }
        getImage().fill();
        this.cell = cell;
        this.index = arrayIndex;
    }
    
    public Cell getCell(){
        return cell;
    }
    
    public int getIndex(){
        return index;
    }
    
    public void makeTransparnet(){
        getImage().setTransparency(0);
    }
}
