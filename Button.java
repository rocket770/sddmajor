import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.*;
import java.awt.FileDialog;
import java.awt.Frame;
import java.util.List;
/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor {
    /**
     * Act - do whatever the Button wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int dimensions = 50;
    private int size = 0;
    private int speed = 0;
    private int Population = 0;
    private int coolDown = 0;
    public String text;
    private Color color;
    public int value;
    private int x, y;
    private int reccomended;
    World world;
    MyWorld thisWorld;
    MouseInfo mouse;
    private String type;
    private boolean hasSpeed = false;
    private boolean hasPop = false;
    private boolean hasSize = false;
    private boolean waitForStop = false;
    public boolean pause = false;
    private Color incorrectColor;
    public Text t;
    private Value v;
    private String selectedFile = "";
    Text fileText;
    private File file;
    private static boolean[] fileOutput;
    private final int THREAD_TIME_OUT = 700;
    private final int SCALE_OFFSET = 3;
    public Button(World world, Color color, int x, int y, String text, String type, int reccomended, int min) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.value = min;
        this.reccomended = reccomended;
        getImage().setTransparency(0);
        this.color = color;
        this.text = text;
        this.type = type;
        world.getBackground().setColor(color);
        world.getBackground().fillRect(x, y, dimensions * 3, dimensions);
        world.showText(text, x + dimensions * SCALE_OFFSET / 2, y + dimensions * 1 / SCALE_OFFSET);
        updateText();
    }

    public Button(World world, Color color, int x, int y, String text, String type, Color incorrectColor, int dimensions) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.color = color;
        getImage().setTransparency(0);
        this.text = text;
        this.type = type;
        this.incorrectColor = incorrectColor;
        this.dimensions = dimensions;
        world.getBackground().setColor(color);
        world.getBackground().fillRect(x, y, dimensions * SCALE_OFFSET, dimensions);
        t = new Text(this.text);
        world.addObject(t, x + dimensions * SCALE_OFFSET / 2, y + dimensions * 2 / 5);
        if(type == "switchWorld") updateBox();
        if (text == "Import Map") {
            fileText = new Text("Selected File: Null", 22);
            world.addObject(fileText, x + 70, y + 70);
            fileOutput = null;
        }
    }

    public Button(World world, Color color, int x, int y, String text, int dimensions, int fontSize) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.color = color;
        getImage().setTransparency(0);
        this.text = text;
        this.type = "Var";
        this.dimensions = dimensions;
        world.getBackground().setColor(color);
        world.getBackground().fillRect(x, y, dimensions * SCALE_OFFSET, dimensions);
        t = new Text(this.text, fontSize);
        world.addObject(t, x + dimensions * SCALE_OFFSET / 2, y + dimensions * 2 / 5);
        if(type == "switchWorld") updateBox();

    }

    protected void addedToWorld(World world) {
        if (type == "Var") {
            v = new Value();
            getWorld().addObject(v, 0, 0);
            v.setID(text);
        }
    }

    public void act() {
        checkClick();
        updateSlider();
        reDraw();
        if (type == "Var") v.setValue(value);
    }

    private void checkClick() {
        try {
            mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int mx = mouse.getX();
                int my = mouse.getY();
                if (world instanceof Menu || world instanceof CustomLevel || world instanceof SettingsWorld) { // call different methods depending on the world
                    clickOnMenu(mx, my);
                } else {
                    clickOnGame(mx, my);
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    private void clickOnMenu(int mx, int my) {
        if (mx > x && mx < x + dimensions * SCALE_OFFSET && my > y && my < y + dimensions && Greenfoot.mouseClicked(null)) {
            switch (text) {
                case "Map Size":
                hasSize = false;
                checkSize();
                break;
                case "Population":
                hasPop = false;
                checkPop();
                break;
                case "Speed":
                hasSpeed = false;
                checkSpeed();
                break;
                case "Set Recc":
                setReccomended();
                break;
                case "Import Map":
                getMap();
                break;
                case "Enter World":
                checkEnterWorld();
                break;
                case "New Map":
                levelEditor();
                break;
                case "Custom Level":
                customLevel();
                break;
                case "Load Map":
                loadLevel();
                break;
                case "Back":
                goBack();
                break;
                case "Settings":
                Greenfoot.setWorld(new SettingsWorld());
                break;
            }
            updateSlider();
        }
        if(type == "switchWorld") updateBox();

        updateText();
    }

    private void clickOnGame(int mx, int my) {
        thisWorld = (MyWorld) world; // get vars from object
        Network runners = (Network) thisWorld.network;
        String out = null;
        if (mx > x && mx < x + dimensions * SCALE_OFFSET && my > y && my < y + dimensions) {
            switch (text) {
                case "Info":
                if (world.getClass().equals(MyWorld.class)) {
                    out = ("Generation: " + runners.gen + "\nFit Sum: " + runners.fitnessSum + "\nDot Amount: " + runners.genomes.size() + "\nAvg Fit: " + (runners.fitnessSum / runners.genomes.size()) + "\nBest Fit: " + runners.bestFitness + "\nLowest Step: " + runners.lowest);
                } else {
                    out = "Click on or near a line or edge to toggle that wall!\nSave the map when you're finished!";
                }
                world.showText(out, 250, 250);
                break;
                case "Save Map":
                save();
                break;
                case "Show":
                if (Greenfoot.mouseClicked(null)) {
                    thisWorld.showingBest = !thisWorld.showingBest;
                }
                break;
                case "Exit":
                exit();
                break;
                case "Settings":
                showSettings();
                break;
            }
        } else if (text == "Info") { //here if we call this from the save button object, that one will never be selected while the info button is, so we must on override the text from the info button class
            world.showText(null, 250, 250);
        }
        if (t.getText() == "Exiting...") onThreadStop(); // only call from 1 button
    }

    public void reDraw() {
        world.getBackground().setColor(color);
        world.getBackground().fillRect(x, y, dimensions * SCALE_OFFSET, dimensions);
        //world.showText(text, x+dimensions*3/2,y+dimensions*1/3);
    }

    //Update our values
    private void updateSlider() {
        for (int i = 0; i < world.getObjects(Slider.class).size(); i++) {
            if (world.getObjects(Slider.class).get(i).type == text) {
                world.getObjects(Slider.class).get(i).setValue(value);
            }
        }
    }

    private void updateBox() {
        Color c = hasEntered() ? color : incorrectColor;       
        world.getBackground().fillRect(x, y, dimensions * SCALE_OFFSET, dimensions);
    }

    private void updateText() {
        if (type != "switchWorld" && type != "Util" && text != "Set Recc") world.showText("Value: " + value, x + dimensions * SCALE_OFFSET / 2, y + dimensions * 2 / SCALE_OFFSET);
    }

    private void exit() {
        Greenfoot.setSpeed(100);
        if (Greenfoot.mouseClicked(null)) {
            if (world.getClass().equals(MyWorld.class)) {
                thisWorld.network.colBox.stopThread(); // tell the thread to stop
                waitForStop = true; // Wait for thread to finish
                t.changeText("Exiting...");
                world.showText("Waiting for threads to stop, force quitting \nmay cause corruption \nand require a restart.", 350, 250);
            } else {
                Greenfoot.setWorld(new Menu());
            }
        }
    }

    // Kill the thread and remove its traces, if not done correctly will create major CPU issues and require a taskmanager quit on open SDK
    private void onThreadStop() {
        if (waitForStop && thisWorld.network.colBox.stopped) { // wait for stop flag
            switchOnThread();
        }
        if (++coolDown % THREAD_TIME_OUT == 0 && waitForStop) { // if a thread has failed to stop prematurely, the java vm will die
            System.out.println("Warning: Couldn't kill the thread in time, force quitting instead!");
            switchOnThread();
        }
        world.showText("Timing Out: " + coolDown, 100, 300);
    }

    private void switchOnThread() {
        thisWorld.network.col.interrupt(); // kill background process
        thisWorld.network.col.stop(); // kill the thread 
        thisWorld.removeObjects(thisWorld.getObjects(CollisionRayCast.class)); // remove object trace (just an extra precaution)
        Greenfoot.setWorld(new Menu()); // switch world;
    }

    private void save() {
        if (Greenfoot.mouseClicked(null)) {
            thisWorld.saveMap();
            text = "Saved!";
            t.changeText(text);
        }
    }

    private void showSettings() {
        if (Greenfoot.mouseClicked(null)) {
            pause = true;
            if (pause && Greenfoot.mouseClicked(null) && ++coolDown > 1) { // activatr on toggle clicks anywhere
                pause = false;
                coolDown = 0;
            }
            thisWorld.getObjects(Overlay.class).get(0).changeImage();
        }
    }

    // Update our vairables
    private void setReccomended() {
        for (int i = 0; i < world.getObjects(Button.class).size() - 1; i++) {
            if (world.getObjects(Button.class).get(i).type != "switchWorld") world.getObjects(Button.class).get(i).value = world.getObjects(Button.class).get(i).reccomended;
        }
        for (int i = 0; i < world.getObjects(Slider.class).size(); i++) {
            if (!world.getObjects(Slider.class).get(i).LinkedToButton) world.getObjects(Slider.class).get(i).setReccomended();
        }
        updateText();
    }
    
    private void goBack() {
        if(world instanceof CustomLevel) {
            Greenfoot.setWorld(new Menu());
        } else {
            Greenfoot.setWorld(new Menu(getValues()));
        }
    }

    private void checkEnterWorld() {
        if (hasEntered()) {
            Greenfoot.setWorld(new MyWorld(((Menu)getWorld()).values));
        } else world.showText("Please enter valid values in each box!", 250, 450);
    }

    private void levelEditor() {
        if (fileOutput == null) {
            Greenfoot.setWorld(new LevelEditor(((CustomLevel) getWorld()).values));
        } else {
            Greenfoot.setWorld(new LevelEditor(((CustomLevel) getWorld()).values, fileOutput));
        }
    }

    private void customLevel() {
        if (hasEntered()) {
            Greenfoot.setWorld(new CustomLevel(((Menu)getWorld()).values));
        } else {
            world.showText("Please enter valid values in each box!", 250, 450);
        }
    }

    private void loadLevel() {
        if (fileOutput != null) {
            MyWorld world = new MyWorld(((CustomLevel) getWorld()).values, fileOutput);
            Greenfoot.setWorld(world);
        } else {
            System.out.println("Please select a map");
        }
    }

    private void checkSpeed() {
        if (!hasSpeed) {
            do {
                try {
                    speed = Integer.parseInt(Greenfoot.ask("Enter Speed here! Make sure it is between 20 and 100 (Reccomended is " + reccomended + ")"));
                } catch (Exception e) {
                    speed = -1; // Aviod runtime error if a invalid value is entered, instead, ask again.
                }
            } while (!validateSpeed());
            hasSpeed = true;
            value = speed;
        }
    }

    private void checkSize() {
        if (text == "Map Size" && !hasSize) {
            do {
                try {
                    size = Integer.parseInt(Greenfoot.ask("Enter Size here! Must be a factor of world size and insure it is between 25 and 100 (Reccomened is " + reccomended + ")" + "                              World Size: " + world.getWidth() + "x" + world.getHeight()));
                } catch (Exception e) {
                    size = -1; // Aviod runtime error if a invalid value is entered, instead, ask again.
                }
            } while (!validateSize());
            hasSize = true;
            value = size;
        }
    }

    private void checkPop() {
        if (!hasPop) {
            do {
                try {
                    Population = Integer.parseInt(Greenfoot.ask("Enter Population Size here! Make sure it is between 50 and 2500 (Reccomened is " + reccomended + ")"));
                } catch (Exception e) {
                    Population = -1; // Aviod runtime error if a invalid value is entered, instead, ask again.
                }
            } while (!validatePop());
            hasPop = true;
            value = Population;
        }
    }

    // Validate our values
    private boolean validatePop() {
        if (Population < 50 || Population > 2500) return false;
        return true;
    }

    private boolean validateSpeed() {
        if (speed < 20 || speed > 100) return false;
        return true;
    }

    private boolean validateSize() {
        if (size != 0 && (!(world.getWidth() % size == 0 && world.getHeight() % size == 0) || size % 40 == 0) || size < 25 || size > 100) { // Validate data entry so the maze is not disproportionate, any size of the factor of 40 does not havbe an even middle
            return false;
        }
        return true;
    }

    private boolean hasEntered() { // validate all buttons have correct values
        for (int i = 0; i < world.getObjects(Button.class).size() - 4; i++) {
            if (world.getObjects(Button.class).get(i).value == 0) {
                return false;
            }
        }
        return true;
    }

    private List < Value > getValues() {
        List vals = getWorld().getObjects(Value.class);
        return vals;
    }

    // For map change
    private void getMap() {
        FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.LOAD); // Use Library to open file slection dialog
        fd.setVisible(true);
        file = new File(fd.getDirectory() + fd.getFile()); // save chosen file to variable)
        fileText.changeText("Selected File: " + fd.getFile());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int fileLength = (int) file.length(); // Save its length to determine byte array size
            byte[] data = new byte[fileLength]; // raw output
            fileOutput = new boolean[fileLength]; // processed output
            inputStream.read(data); // Set data byte array to contents in file    -- done by reading the file and dumping contents into data array
            for (int i = 0; i < data.length; i++) { // Convert byte data to boolean to process it into useful information
                if (data[i] != 0) {
                    fileOutput[i] = true; // if it is a 1, set that wall to true and vise verca 
                    continue;
                }
                fileOutput[i] = false;
            }
            // get the loaded maps size
            int size = Integer.parseInt(String.valueOf(fd.getFile().charAt(fd.getFile().length() - 6)) + String.valueOf(fd.getFile().charAt(fd.getFile().length() - 5))); // Reduce the name to only number by taking the 6th and 5th last values of the string and adding them.
            if (size == 00) {
                size = 100; // fix value for 100
            }
            // look for the object storing the size, update it to the maps size
            for (Value v: ((CustomLevel) getWorld()).values) {
                if (v.getID() == "Map Size") {
                    v.setValue(size); // Update the buttons value that contains the map size 
                }
            }
            ((CustomLevel) getWorld()).levelEditor.t.changeText("Edit Map");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}