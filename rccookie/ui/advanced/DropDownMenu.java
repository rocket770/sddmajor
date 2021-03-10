package rccookie.ui.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import greenfoot.*;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.ui.basic.Button;
import rccookie.ui.basic.Text;
import rccookie.util.ClassTag;

/**
 * The drop.down menu opens a list of options when clicked and saves the selected one.
 * 
 * @author RcCookie
 * @version 1.0
 */
@IgnoreOnRaycasts
public class DropDownMenu extends Button {

    private static final long serialVersionUID = -3285971397401985009L;

    static {
        ClassTag.tag(Background.class, "ui");
    }
    


    private final List<Consumer<String>> menuClickActions = new ArrayList<>();

    /**
     * The different buttons of the options shown when opened.
     */
    Button[] options;

    /**
     * The background that pops up behind the opened menu.
     */
    Background background;

    /**
     * The name of the menu, shown as default selection.
     */
    String name;


    /**
     * Constructs a new drop-down menu with the given default name and the given options.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     */
    public DropDownMenu(String name, String[] options){
        this(name, options, 150, 35, Color.LIGHT_GRAY, 15);
    }

    /**
     * Constructs a new drop-down menu with the given default name and the given options in the given background color.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     * @param color The background color of the menu buttons
     */
    public DropDownMenu(String name, String[] options, Color color){
        this(name, options, 150, 35, color, 15);
    }

    /**
     * Constructs a new drop-down menu with the given default name and the given options in the given size.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     * @param x The width of the menu
     * @param y The height of the closed menu / of every option button
     */
    public DropDownMenu(String name, String[] options, int x, int y){
        this(name, options, x, y, Color.LIGHT_GRAY, 15);
    }

    /**
     * Constructs a new drop-down menu with the given default name and the given options in the given size
     * with the given background color. The text will be displayed in the specified font size.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     * @param x The width of the menu
     * @param y The height of the closed menu / of every option button
     * @param background The background color of the menu buttons
     * @param fontSize The font size of any text on the menu
     */
    public DropDownMenu(String name, String[] options, int x, int y, Color background, int fontSize){
        this(name, options, x, y, Color.BLACK, background, fontSize, true);
    }

    /**
     * Constucts a new drop-down-menu with the given properties.
     * 
     * @param name The name of the menu
     * @param options The options to shoose from, as strings
     * @param x The width of the menu
     * @param y The height of the menu
     * @param textColor The color of the menu's text
     * @param backgroundColor The background color of the menu
     * @param fontSize The font size for the menu's text
     * @param outline Weather an outline should be drawn
     */
    public DropDownMenu(String name, String[] options, int x, int y, Color textColor, Color backgroundColor, int fontSize, boolean outline){
        super(new Text(name, fontSize, textColor, backgroundColor), x, y, outline);
        addClickAction(info -> {
            if(clickCount % 2 == 1) open();
            else close();
        });
        
        this.name = name;
        this.options = new Button[options.length];
        for(int i=0; i<options.length; i++){
            String buttonName = options[i];
            this.options[i] = new Button(new Text(buttonName, fontSize, textColor, backgroundColor), x, y, outline);
            this.options[i].addClickAction(info -> buttonClicked(buttonName));
        }
        background = new Background(this);
    }
    

    /**
     * Checkes every frame weather the mouse has been pressed onto something else. If it has been, it
     * is going to close the menu.
     */
    public void run(){
        try{
            if(pressedOnSomethingElse()){
                clickCount++;
                close();
            }
        }catch(Exception e){}
    }


    /**
     * Checks weather the menu is open and if the mouse has been pressed. If it was, it will return
     * false if that press was on a part of the menu, otherwise it will return true.
     * 
     * @return If the mouse has been pressed onto something else while the menu was open
     */
    private boolean pressedOnSomethingElse(){
        if(background.getWorld() == null) return false;
        if(!Greenfoot.mousePressed(null)) return false;
        try{
            Actor touched = Greenfoot.getMouseInfo().getActor();
            if(touched == this) return false;
            if(touched == background) return false;
            for(Button b : options){
                if(touched == b) return false;
            }
        }catch(Exception e){}
        return true;
    }


    /**
     * Opens the selection of options below the menu button and underlays the background.
     */
    private void open(){
        setTitle(name);
        getWorld().addObject(background, 0, 0);
        for(int i=0; i<options.length; i++){
            getWorld().addObject(
                options[i],
                getX(),
                getY() + getImage().getHeight() * (i + 1)
            );
        }
    }


    /**
     * Closes the selection by removing the option buttons and the background. The menu
     * name will be set to the selected option.
     * 
     * @param selected The option that was selected
     */
    private void close(String selected){
        setTitle(selected);
        getWorld().removeObject(background);
        for(Button b : options) getWorld().removeObject(b);
    }

    /**
     * Closes the selection by removing the option buttons and the background. The menu
     * name will be set to the default option name.
     */
    private void close(){
        close(name);
    }


    /**
     * Sets the location of itself and, if neccecary, of the options buttons and the background.
     * 
     * @param x The new x location of the main button
     * @param y The new y location of the main button
     */
    @Override
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        if(background.getWorld() != null){
            close();
            open();
        }
    }


    /**
     * Closes the menu to the given option.
     * 
     * @param name The option name to close to
     */
    private void buttonClicked(String name){
        clickCount++;
        close(name);
        onClick(name);
    }


    /**
     * Executed whenever an option button was pressed. Override to do something depending on the
     * selected option.
     * 
     * @param buttonName The name of the option selected
     */
    private void onClick(String buttonName){
        for(Consumer<String> action : menuClickActions) action.accept(buttonName);
    }
    

    /**
     * Returns the name of the currently selected option. May return the default option name.
     * @return The currently selected option
     */
    public String selected(){
        return getTitle();
    }


    /**
     * Weather there has been an option selected, or it is (still) the default option.
     * 
     * @return False if an option was selected
     */
    public boolean answered(){
        return getTitle() != name;
    }
    
    

    /**
     * A class without functionality that serves as a background for the menu.
     */
    @IgnoreOnRaycasts
    public class Background extends Actor {
        /**
         * The menu this background belongs to.
         */
        DropDownMenu menu;

        /**
         * The number of pixels the background is standing out behind the buttons in every direction.
         */
        private static final int OUTLINE = 2;


        /**
         * Constructs a new background that automaticly generates a fitting image.
         * 
         * @param menu The menu this boackground belongs to
         */
        public Background(DropDownMenu menu){
            this.menu = menu;
            GreenfootImage image = new GreenfootImage(
                menu.getImage().getWidth() + OUTLINE * 2,
                menu.getImage().getHeight() * (options.length + 1) + OUTLINE * 2
            );
            image.setColor(Color.GRAY);
            image.fill();
            setImage(image);
        }


        /**
         * Called when added to the world. The background will automaticly go to the approptiate location.
         * 
         * @param w The world added to
         */
        protected void addedToWorld(World w){
            setLocation(
                menu.getX(),
                menu.getY() + menu.getImage().getHeight() * options.length / 2 + 1
            );
        }





        /**
         * Add an action that is run whenever an option is selected.
         * <p>When the action gets executed, the object parameter will contain a string with the name of the
         * option that was selected.
         * 
         * @param senectedOption The action to add
         */
        public void addMenuClickAction(Consumer<String> senectedOption) {
            if(senectedOption == null) return;
            menuClickActions.add(senectedOption);
        }
        public void removeMenuClickAction(Consumer<String> action) {
            menuClickActions.remove(action);
        }
    }
}