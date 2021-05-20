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
    private int population = 0;
    private int coolDown = 0;
    public String text;
    private Color color;
    public int value;
    private int x, y;
    private int reccomended;
    private World world;
    private MyWorld thisWorld;
    private MouseInfo mouse;
    private String type;
    private boolean hasSpeed = false;
    private boolean hasPop = false;
    private boolean hasSize = false;
    private boolean waitForStop = false;
    public static boolean pause = false;
    private Color incorrectColor;
    public Text t;
    private Value v;
    private String selectedFile = "";
    private File file;
    private static boolean[] fileOutput;
    private final int THREAD_TIME_OUT = 700;
    private int SCALE_OFFSET = 3;
    GreenfootImage img;
    private static List<Button> settingsButtons = new ArrayList<Button>();
    private static Text showState;
    private static boolean FPS_Visible = true;
    private Text timeOutText = new Text("");
    private Text timeOutCountdown = new Text("");
    private int xOut = 0, yOut = 0;
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
        t = new Text(this.text);
        world.addObject(t, x + dimensions * SCALE_OFFSET / 2, y + dimensions * 2 / 5);
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
        fileOutput = null;
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

    protected void addedToWorld(World world) { // some buttons need some addons 
        if (type == "Var") { // if it is a var, give it a text object that can display its value
            v = new Value();
            getWorld().addObject(v, 0, 0);
            v.setID(text);
        } else if(text == "Show") { // if it is the show button, get the new world ands change its dimensions to fit 
            thisWorld = (MyWorld) world;
            updateShowText();           
        }
    }

    public void act() {
        checkClick();
        updateSlider();
        reDraw();
        if (type == "Var") v.setValue(value); // update the value incase a new one has been chosen
    }

    private void checkClick() {
        try {
            mouse = Greenfoot.getMouseInfo();
            if (mouse != null) { // get mouse positions
                int mx = mouse.getX();
                int my = mouse.getY();
                reSize(mx,my);
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

    private void clickOnMenu(int mx, int my) throws Exception {
        if (checkHover(mx, my) && Greenfoot.mouseClicked(null)) {
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
                case "Help":
                try { // this needs a try catch as it thrwos an exception, 
                    new ProcessBuilder("cmd.exe", "/C", "./User Manual Help Screen.pdf").start(); // open the help pdf     
                }catch(Exception e) {
                    throw new Exception("File not found... Contact the developer for more information or support!");
                }
                break;
            }
            updateSlider();
        }
        if(type == "switchWorld") updateBox();
        updateText(); // update all text on the buttons 
    }

    private void clickOnGame(int mx, int my) {
        thisWorld = (MyWorld) world; // get vars from object
        Network runners = (Network) thisWorld.network;
        String out = null;
        if (checkHover(mx, my)) {
            switch (text) {
                case "Info": // the quickest way to have text only appear when it hovered and dispear after was this, I tried to put it in another method but it got messy trying to reset variables
                if(pause && type != "Util") return;
                if (world.getClass().equals(MyWorld.class)) {
                    out = ("Summary: \n"+"Generation: " + runners.gen + "\nFit Sum: " + runners.fitnessSum + "\nDot Amount: " + runners.genomes.size() + "\nAvg Fit: " + (runners.fitnessSum / runners.genomes.size()) + "\nBest Fit: " + runners.bestFitness + "\nLowest Step: " + runners.lowest);
                    world.getBackground().fillRect(30, 265,235, 180);   // put a backdrop around the test, had to hard code these values as text lengths are dynamic with greenfoot. There would be no "exact" offset when the text changes
                    xOut = 150;
                    yOut = 350;
                    world.showText(out, xOut, yOut);
                } else { 
                    out = "Click on or near a line or edge to toggle that wall!\nEnsure there is a clear path from the starting cells\n to the goal!\nSave the map when you're finished!";
                    world.getBackground().fillRect(105, 265,485, 180);
                    xOut = 350;
                    yOut = 350; 
                    world.showText(out, xOut, yOut);
                }
                break;
                case "Save Map":
                save();
                break;
                case "Show":
                toggleShowing();
                break;
                case "Exit":
                exit();
                break;
                case "Settings":
                showSettings();
                break;
                case "Toggle Path": 
                togglePath();
                break;
                case "Toggle FPS": 
                toggleFPS();
                break; 
                case "Resume": 
                if(Greenfoot.mouseClicked(null)) showSettings();
                break;    
            }
        } else if (text == "Info") { //here if we call this from the save button object, that one will never be selected while the info button is, so we must on override the text from the info button class
            world.showText(null,xOut, yOut);
        }
        if (t.getText() == "Exiting...") onThreadStop(); // only call from 1 button
    }

    private boolean checkHover(int mx, int my) {
        return (mx > x && mx < x + dimensions * SCALE_OFFSET && my > y && my < y + dimensions); // return if the mouse is within the boundaries of the box or not
    }

    private void reSize(int mx, int my){
        if(checkHover(mx, my)) {
            world.getBackground().setColor(color); // if the mouse is hovering over the box draw it bigger otherwise, draw it regualrly
            world.getBackground().fillRect(x-2, y-2, dimensions * SCALE_OFFSET+2, dimensions+2);
        } else { 
            reDraw();
        }
    }

    public void reDraw() {
        world.getBackground().setColor(color); // draw it regulary
        world.getBackground().fillRect(x, y, dimensions * SCALE_OFFSET, dimensions);
    }

    //Update our values
    private void updateSlider() {
        for (int i = 0; i < world.getObjects(Slider.class).size(); i++) { // for all the sldiers in the world, set thier value to this buttons value if they have the same "id"
            if (world.getObjects(Slider.class).get(i).type == text) {
                world.getObjects(Slider.class).get(i).setValue(value);
            }
        }
    }

    private void updateBox() {
        Color c = hasEntered() ? color : incorrectColor; // if the input is valid, set it to the nomral color otherwise grey it out       
        world.getBackground().fillRect(x, y, dimensions * SCALE_OFFSET, dimensions);
    }

    private void updateText() {
        if (type != "switchWorld" && type != "Util" && text != "Set Recc") { // update text boundarys for specific types of boxes
            t.setText(text+"\nValue: " + value);
            t.setFontSize(23); // a little bit smaller then current
            t.setBoundarySize(15, 53); // just a number thats really big, no point in giving vairables specifcally for these values as theyre kind of useless for most other buttons
        }
    }

    private void exit() {
        if (Greenfoot.mouseClicked(null) && !pause) {
            Greenfoot.setSpeed(100); // set speed to 100 to make exiting quicker
            if (world.getClass().equals(MyWorld.class)) { // if we are in the world class, stop the thread
                setAllTransparency(5);
                world.addObject(timeOutText,290,280);
                world.addObject(timeOutCountdown, 290, 350);
                thisWorld.network.colBox.stopThread(); // tell the thread to stop
                waitForStop = true; // Wait for thread to finish
                t.setText("Exiting...");
                timeOutText.setText("Waiting for threads to stop, force quitting \nmay cause corruption \nand require a restart.");
                timeOutText.setBoundarySize(0, 90);
            } else {
                Greenfoot.setWorld(new Menu()); // otherwise, the level editor doesnt use a thread so we can exit instantly
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
        timeOutCountdown.setText("Timing Out: " + coolDown);
    }

    private void switchOnThread() {
        thisWorld.network.col.interrupt(); // kill background process
        thisWorld.network.col.stop(); // kill the thread 
        thisWorld.removeObjects(thisWorld.getObjects(CollisionRayCast.class)); // remove object trace (just an extra precaution)
        Greenfoot.setWorld(new Menu()); // switch world;
    }

    private void save() {
        if (!pause && Greenfoot.mouseClicked(null)) { // update text and call save map method
            thisWorld.saveMap();
            text = "Saved!";
            t.setText(text);
        }
    }

    private void showSettings() {
        if (Greenfoot.mouseClicked(null)) {
            pause = !pause;         // toggle pause state
            if(pause) {
                addSettingsButtons();  // if pausing, add all options to the screen               
            } else {
                removeSettingsButtons(); // otherwinse if we unpause we want to remove them
                settingsButtons.clear();
            } 
        }
    }

    private void addSettingsButtons() {
        thisWorld.addObject(new Overlay(), thisWorld.getWidth()/2,thisWorld.getHeight()/2); // toggle overlay
        setAllTransparency(50); // fade all objects into the background
        settingsButtons.add(new Button(thisWorld, Color.ORANGE, 232, 90, "Toggle Path", "Util", new Color(128, 128, 128), 45)); // add all needed buttons
        settingsButtons.add(new Button(thisWorld, Color.ORANGE, 232, 190, "Show", "Util", new Color(128, 128, 128), 45));
        settingsButtons.add(new Button(thisWorld, Color.ORANGE, 232, 290, "Toggle FPS", "Util", new Color(128, 128, 128), 45));
        settingsButtons.add(new Button(thisWorld, Color.ORANGE, 232, 390, "Resume", "Util", new Color(128, 128, 128), 45));
        settingsButtons.forEach(b -> 
                world.addObject(b,0,0)  // add all of the settings buttons in the list to the world
        );

    }

    private void removeSettingsButtons() {
        for(Button b :settingsButtons) {
            world.removeObject(b.t); // remove all the text boxes for the buttons
            world.removeObject(b);   // remove all the actual objects
        }
        setAllTransparency(255);    // make all objects clear again
        thisWorld.removeObjects(thisWorld.getObjects(Overlay.class));
    }

    private void setAllTransparency(int t) {    // set all the actors transparency that isnt a button in use 
        List<Object> objs = world.getObjects(null); // grab every single object int he world
        for(Object obj: objs) {
            Actor actor = (Actor)obj; // for each object, get its actor
            if(actor.getClass() != Button.class ) { // if its not a button, set it to the transparency given in the constructor
                actor.getImage().setTransparency(t);
            }
        }
    }

    private void togglePath() {
        if (Greenfoot.mouseClicked(null)) {
            thisWorld.toggleBestPath(); // tell the world to toggle the best path
        }
    }

    private void toggleFPS() {
        if (Greenfoot.mouseClicked(null)) {
            FPS_Visible = !FPS_Visible; // toggle wether or not the FPS should be displayed
            if(FPS_Visible) {
                thisWorld.addObject(new FramesPerSecond(),thisWorld.getWidth() - 70, 30); // add a new one
            } else {
                thisWorld.removeObjects(thisWorld.getObjects(FramesPerSecond.class)); // or remove the current one
            }
        }
    }

    private void toggleShowing() {
        if (Greenfoot.mouseClicked(null)) {
            thisWorld.showingBest = !thisWorld.showingBest;
            thisWorld.network.act(); // we have to update the frame here even if its paused to clear the background
            thisWorld.network.show(); // actually apply the settings by updating the screen with the correct viewing
            updateShowText();
        }   
    }

    private void updateShowText() {
        String showText = (thisWorld.showingBest) ? "Show: Best" : "Show: All";  // used to set the text according to the state of what is being shown
        t.setText(showText);
    }

    // Update our vairables
    private void setReccomended() { // set all values to the reccomened for both the buttons and sliders, ignore it if its a button is not linked to a lsider or a change World type 
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
            Greenfoot.setWorld(new Menu()); // return to menu 
        } else {
            Greenfoot.setWorld(new Menu(getValues())); // but if the user was in the settings world, return to it with the new values
        }
    }

    private void checkEnterWorld() {
        if (hasEntered()) { // if all values are validated correctly start the simulation
            Greenfoot.setWorld(new MyWorld(Menu.values));
        } else world.showText("Please enter valid values in each box!", 250, 450);
    }

    private void levelEditor() {
        if (fileOutput == null) {
            Greenfoot.setWorld(new LevelEditor(((CustomLevel) getWorld()).values)); // make a blank level if no file is selected
        } else {
            Greenfoot.setWorld(new LevelEditor(((CustomLevel) getWorld()).values, fileOutput)); // otherwise load the map in so it can be edited
        }
    }

    private void customLevel() throws Exception {
        if (hasEntered()) {
            Greenfoot.setWorld(new CustomLevel(((Menu)getWorld()).values));
        } else {
            throw new Exception("Values have been loaded incorectly, please try re-entering them again");
        }
    }

    private void loadLevel() throws Exception {
        if (fileOutput != null) {
            MyWorld world = new MyWorld(((CustomLevel) getWorld()).values, fileOutput);
            Greenfoot.setWorld(world);
        } else {
            throw new Exception("Please select a map");
        }
    }

    private void checkSpeed() {
        if (!hasSpeed) {
            do { // keep asking for a speed value if it has been entered 
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
        if (!hasSize) {
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
                    population = Integer.parseInt(Greenfoot.ask("Enter population Size here! Make sure it is between 50 and 2500 (Reccomened is " + reccomended + ")"));
                } catch (Exception e) {
                    population = -1; // Aviod runtime error if a invalid value is entered, instead, ask again.
                }
            } while (!validatePop());
            hasPop = true;
            value = population;
        }
    }

    // Validate our values
    private boolean validatePop() {
        if (population < MyWorld.MIN_POP_SIZE || population > MyWorld.MAX_POP_SIZE) return false;
        return true;
    }

    private boolean validateSpeed() {
        if (speed < MyWorld.MIN_SPEED || speed > MyWorld.MAX_SPEED) return false;
        return true;
    }

    private boolean validateSize() {
        if (size != 0 && (!(world.getWidth() % size == 0 && world.getHeight() % size == 0) || size % 40 == 0) || size < MyWorld.MIN_MAZE_SCALE || size > MyWorld.MAX_MAZE_SCALE) { // Validate data entry so the maze is not disproportionate,
            return false; // the only acceptable values are those of a factor of the world size. The only ecpetion is 40, whihc is a factor but does not contain a definite middle when used to scale the walls.
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
        List vals = getWorld().getObjects(Value.class); // return all the worlds values
        return vals;
    }

    // For map set
    private void getMap() {
        FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.LOAD); // Use Library to open file slection dialog
        fd.setVisible(true);
        file = new File(fd.getDirectory() + fd.getFile()); // save chosen file to variable)
        CustomLevel.fileText.setText("Selected File: " + fd.getFile());
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
            int size = Integer.parseInt(
                    String.valueOf(fd.getFile().charAt(fd.getFile().length() - 6)) 
                    + String.valueOf(fd.getFile().charAt(fd.getFile().length() - 5))
            ); // Reduce the name to only number by taking the 6th and 5th last values of the string and adding them. Much more efficient to hard code the location rather then checking through every value and looking for the digits after a "."
            if (size == 00) {
                size = 100; // fix value for 100
            }
            // look for the object storing the size, update it to the maps size
            for (Value v: ((CustomLevel) getWorld()).values) {
                if (v.getID() == "Map Size") {
                    v.setValue(size); // Update the buttons value that contains the map size 
                }
            }
            ((CustomLevel) getWorld()).levelEditor.t.setText("Edit Map");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}