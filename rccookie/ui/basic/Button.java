package rccookie.ui.basic;

import java.util.function.Consumer;

import greenfoot.*;
import rccookie.game.AdvancedActor;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.util.ClassTag;
/**
 * A button is an ui element that interacts with mouse input. The Button works completly
 * automaticly, you do not have to feed it any information after the
 * construction. It offers selveral methods to get information about
 * the users interactions with it. Additionally, it can call specified actions
 * on certain circumstances.
 * 
 * @author RcCookie
 * @version 2.1
 */
@IgnoreOnRaycasts
public class Button extends AdvancedActor {

    private static final long serialVersionUID = -5757911783317668472L;
    
    public static Color OUTLINE_COLOR = new Color(35, 35, 35);
    public static Color CLICK_COLOR = new Color(0, 0, 0, 80);
    public static double HOVER_SCALE = 1.05;

    protected static Color DEF_BACKGROUND_COLOR = Color.GRAY;
    protected static final int MIN_BORDER = 3;
    protected static final int MIN_X = 10, MIN_Y = 10;

    static {
        ClassTag.tag(Button.class, "ui");
    }

    // ------------------------------------------
    // Button properties
    // ------------------------------------------

    /**
     * The image given as input, or null if a color was chosen.
     */
    protected GreenfootImage background;

    /**
     * The text object representing all text properties.
     */
    protected Text text;

    /**
     * The minimum x and y size of the button.
     */
    protected int minX, minY;

    /**
     * Weather an outline should be drawn.
     */
    protected boolean outline;

    /**
     * Sets weather the button is enabled.
     */
    public boolean enabled = true;


    /**
     * Weather this button should always be repainted. This will have an 
     * impact on the performance.
     */
    public boolean alwaysRepaint = false;



    // ------------------------------------------
    // Button states
    // ------------------------------------------

    /**
     * The number of times this button has been clicked.
     */
    protected int clickCount;

    /**
     * The number of frames this button was held down.
     */
    protected int pressTime;

    /**
     * Weather the button is touched by the mouse right now.
     */
    protected boolean touched;


    private final Consumer<Object> textUpdateAction;



    // ------------------------------------------
    // Cashing
    // ------------------------------------------

    /**
     * The standart image of the button.
     */
    protected GreenfootImage image;

    /**
     * The buttons image when hovered over it with the mouse.
     */
    protected GreenfootImage hoveredImage;

    /**
     * The buttons image when it is being clicked.
     */
    protected GreenfootImage clickedImage;










    //--------------------------------------------------
    // String constructors
    //--------------------------------------------------


    

    /**
     * Constructs a new grey Button with the given title.
     * 
     * @param title The text printed onto the button
     */
    public Button(String title){
        this(new Text(title));
    }
    
    /**
     * Constructs a new grey Button with the given title and a the given width
     * and height.
     * 
     * @param title The text printed onto the button
     * @param minX The width of the button
     * @param minY The height of the button
     */
    public Button(String title, int minX, int minY){
        this(new Text(title), minX, minY);
    }


    //--------------------------------------------------
    // Plain button constructors
    //--------------------------------------------------


    /**
     * Constructs a new gray button with the given size.
     * <p>The size is labled as <i>minimum</i> because later changes to the text may change
     * the required size.
     * 
     * @param minX The width of the button
     * @param minY The height of the button
     */
    public Button(int minX, int minY) {
        this(minX, minY, DEF_BACKGROUND_COLOR);
    }

    /**
     * Constructs a new button in the given size and color.
     * <p>The size is labled as <i>minimum</i> because later changes to the text may change
     * the required size.
     * 
     * @param minX The width of the button
     * @param minY The height of the button
     * @param backgroundColor The color of the button
     */
    public Button(int minX, int minY, Color backgroundColor) {
        this(minX, minY, backgroundColor, true);
    }

    /**
     * Constructs a new button in the given size and color and if chosen with a dark outline.
     * <p>The size is labled as <i>minimum</i> because later changes to the text may change
     * the required size.
     * 
     * @param minX The width of the button
     * @param minY The height of the button
     * @param backgroundColor The color of the button
     * @param outline Weather a dark outline should be drawn
     */
    public Button(int minX, int minY, Color backgroundColor, boolean outline) {
        this(coloredImage(minX, minY, backgroundColor), outline);
    }

    private static final GreenfootImage coloredImage(int x, int y, Color color) {
        GreenfootImage image = new GreenfootImage(x, y);
        image.setColor(color);
        image.fill();
        return image;
    }



    //--------------------------------------------------
    // Image constructors
    //--------------------------------------------------


    /**
     * Constructs a new button with the size of the given image and that image as background.
     * <p>If {@code background} is {@code null}, the background will be filled with a gray background
     * color.
     * <p>The given image will not be cloned so changes to that instance after passing it into this button
     * will have an effect. HOWEVER, the image WILL NOT be updated automaticly by default. There are multiple
     * ways to update the buttons image though:
     * <ul>
     * <li>The button image gets updates for other reasons automatically (for example due to a change in text)
     * <li>Calling {@code createAndSetImages()} manually after adjusting the image.
     * <li>Setting the image using {@code setBackground(GreenfootImage)} (even though it is the same instance)
     * <li>Setting {@code alwaysRepaint} to {@code true}. This will automatically update the image every frame
     * </ul>
     * <p>It is not reccomended to always simply set {@code alwaysRepaint} to {@code true} as repainting takes
     * a long time and has a considerable impact on performance.
     * 
     * @param background The image for the button's background
     */
    public Button(GreenfootImage background) {
        this(background, false);
    }

    /**
     * Constructs a new button with the size of the given image and that image as background.
     * If chosen a dark outline will be drawn.
     * <p>If {@code background} is {@code null}, the background will be filled with a gray background
     * color.
     * <p>The given image will not be cloned so changes to that instance after passing it into this button
     * will have an effect. HOWEVER, the image WILL NOT be updated automaticly by default. There are multiple
     * ways to update the buttons image though:
     * <ul>
     * <li>The button image gets updates for other reasons automatically (for example due to a change in text)
     * <li>Calling {@code createAndSetImages()} manually after adjusting the image.
     * <li>Setting the image using {@code setBackground(GreenfootImage)} (even though it is the same instance)
     * <li>Setting {@code alwaysRepaint} to {@code true}. This will automatically update the image every frame
     * </ul>
     * <p>It is not reccomended to always simply set {@code alwaysRepaint} to {@code true} as repainting takes
     * a long time and has a considerable impact on performance.
     * 
     * @param background The image for the button's background
     * @param Weather a dark outline should be drawn
     */
    public Button(GreenfootImage background, boolean outline) {
        this(coloredText(new Color(0, 0, 0, 0)), background, outline);
    }

    private static final Text coloredText(Color color) {
        return new Text(null, 1, Color.BLACK, color);
    }




    //--------------------------------------------------
    // Text constructors
    //--------------------------------------------------




    /**
     * Constructs a new button from the given text. Content and size
     * are updated every frame.
     * 
     * @param text The text object the button should be based on
     */
    public Button(Text text){
        this(text, true);
    }

    /**
     * Constructs a new button with the given text and when chosen a dark outline.
     * 
     * @param text The text object for the button
     * @param outline Weather a dark outline should be drawn
     */
    public Button(Text text, boolean outline) {
        this(text, MIN_X, MIN_Y, outline);
    }
    
    /**
     * Constructs a new button from the given text that is at least
     * as big as inputed. Content and size are updated every frame.
     * 
     * @param text The text object the button should be based on
     * @param minX The minimum width of the button
     * @param minY The minimum height of the button
     */
    public Button(Text text, int minX, int minY){
        this(text, minX, minY, null);
    }

    /**
     * Constructs a new button that is at least a big as the given image. If the text is bigger than
     * the image, the text's background color will be used to fill the gap.
     * <p>If {@code background} is {@code null}, the background will be filled with the text's background
     * color.
     * <p>The given image will not be cloned so changes to that instance after passing it into this button
     * will have an effect. HOWEVER, the image WILL NOT be updated automaticly by default. There are multiple
     * ways to update the buttons image though:
     * <ul>
     * <li>The button image gets updates for other reasons automatically (for example due to a change in text)
     * <li>Calling {@code createAndSetImages()} manually after adjusting the image.
     * <li>Setting the image using {@code setBackground(GreenfootImage)} (even though it is the same instance)
     * <li>Setting {@code alwaysRepaint} to {@code true}. This will automatically update the image every frame
     * </ul>
     * <p>It is not reccomended to always simply set {@code alwaysRepaint} to {@code true} as repainting takes
     * a long time and has a considerable impact on performance.
     * 
     * @param text The text object for the button
     * @param background The background image for the button
     */
    public Button(Text text, GreenfootImage background) {
        this(text, background, true);
    }

    /**
     * Constructs a new button that is at least a big as the given image. If the text is bigger than
     * the image, the text's background color will be used to fill the gap.
     * <p>If {@code background} is {@code null}, the background will be filled with the text's background
     * color.
     * <p>The given image will not be cloned so changes to that instance after passing it into this button
     * will have an effect. HOWEVER, the image WILL NOT be updated automaticly by default. There are multiple
     * ways to update the buttons image though:
     * <ul>
     * <li>The button image gets updates for other reasons automatically (for example due to a change in text)
     * <li>Calling {@code createAndSetImages()} manually after adjusting the image.
     * <li>Setting the image using {@code setBackground(GreenfootImage)} (even though it is the same instance)
     * <li>Setting {@code alwaysRepaint} to {@code true}. This will automatically update the image every frame
     * </ul>
     * <p>It is not reccomended to always simply set {@code alwaysRepaint} to {@code true} as repainting takes
     * a long time and has a considerable impact on performance.
     * 
     * @param text The text object for the button
     * @param background The background image for the button
     * @param outline Weather a dark outline should be drawn
     */
    public Button(Text text, GreenfootImage background, boolean outline) {
        this(text, background != null ? background.getWidth() : MIN_X, background != null ? background.getHeight() : MIN_Y, background, outline);
    }

    /**
     * Constructs a new button from the given text that is at least
     * as big as inputed. Content and size are updated every frame.
     * <p>The given image will not be cloned so changes to that instance after passing it into this button
     * will have an effect. HOWEVER, the image WILL NOT be updated automaticly by default. There are multiple
     * ways to update the buttons image though:
     * <ul>
     * <li>The button image gets updates for other reasons automatically (for example due to a change in text)
     * <li>Calling {@code createAndSetImages()} manually after adjusting the image.
     * <li>Setting the image using {@code setBackground(GreenfootImage)} (even though it is the same instance)
     * <li>Setting {@code alwaysRepaint} to {@code true}. This will automatically update the image every frame
     * </ul>
     * <p>It is not reccomended to always simply set {@code alwaysRepaint} to {@code true} as repainting takes
     * a long time and has a considerable impact on performance.
     * 
     * @param text The text object the button should be based on
     * @param minX The minimum width of the button
     * @param minY The minimum height of the button
     * @param background The background image for the button. If {@code null},
     *                   the background color of the text will be used
     */
    public Button(Text text, int minX, int minY, GreenfootImage background) {
        this(text, minX, minY, background, true);
    }
    
    /**
     * Constructs a new button from the given text that is at least
     * as big as inputed. It will switch to the given world unless it
     * is null. Content and size are updated every frame.
     * 
     * @param text The text object the button should be based on
     * @param minX The minimum width of the button
     * @param minY The minimum height of the button
     * @param outline If a dark outline should be drawed around the button
     * @param onClick The world to switch to when clicked
     */
    public Button(Text text, int minX, int minY, boolean outline){
        this(text, minX, minY, null, outline);
    }

    /**
     * Constructs a new Button with the specified properties.
     * <p>The given image will not be cloned so changes to that instance after passing it into this button
     * will have an effect. HOWEVER, the image WILL NOT be updated automaticly by default. There are multiple
     * ways to update the buttons image though:
     * <ul>
     * <li>The button image gets updates for other reasons automatically (for example due to a change in text)
     * <li>Calling {@code createAndSetImages()} manually after adjusting the image.
     * <li>Setting the image using {@code setBackground(GreenfootImage)} (even though it is the same instance)
     * <li>Setting {@code alwaysRepaint} to {@code true}. This will automatically update the image every frame
     * </ul>
     * <p>It is not reccomended to always simply set {@code alwaysRepaint} to {@code true} as repainting takes
     * a long time and has a considerable impact on performance.
     * 
     * @param text The text object for the button
     * @param minX The minimum width of the button
     * @param minY The minimum height of the button
     * @param background The background image for the button. If {@code null},
     *                   the background color of the text will be used
     * @param outline Weather a dark outline should be drawn
     */
    public Button(Text text, int minX, int minY, GreenfootImage background, boolean outline) {
        this.text = text;
        this.minX = minX;
        this.minY = minY;
        this.background = background;
        this.outline = outline;

        textUpdateAction = info -> createAndSetImages();
        text.addUpdateAction(textUpdateAction);

        touched = false;
        clickCount = 0;
        pressTime = 0;

        addClickAction(mouse -> clickCount++);

        createAndSetImages();
        updateAnimations();
        super.setImage(image);
    }

















    /**
     * Creates the image, hoveredImage and clickedImage for the button using
     * the latest settings and aplies the matching image to the button.
     */
    protected void createAndSetImages(){

        if(text == null) return;

        // Create correct sized image
        int x = this.minX, y = this.minY;
        if(x < text.getImage().getWidth() + 2 * MIN_BORDER) x = text.getImage().getWidth() + 2 * MIN_BORDER;
        if(y < text.getImage().getHeight()) y = text.getImage().getHeight();
        image = new GreenfootImage(x, y);

        // Fill background around text with text background color or default color, if null
        if(background == null || background.getWidth() < x || background.getHeight() < y) {
            if(text.getBackgroundColor() == null) {
                image.setColor(DEF_BACKGROUND_COLOR);
                image.fill();
            }
            else {
                image.setColor(text.getBackgroundColor());
                int delta = x - text.getImage().getWidth();
                image.fillRect(0, 0, delta / 2, image.getHeight());
                image.fillRect(image.getWidth() - (delta - delta / 2), 0, delta / 2, image.getHeight());
                if(y > text.getImage().getHeight()){
                    int deltaX = x - text.getImage().getWidth();
                    int deltaY = y - text.getImage().getHeight();
                    image.fillRect(deltaX / 2, 0, text.getImage().getWidth(), deltaY / 2);
                    image.fillRect(deltaX / 2, image.getHeight() - (deltaY - deltaY / 2), text.getImage().getWidth(), deltaY / 2);
                }
            }
        }

        // Draw image onto background if not null
        if(background != null) {
            image.drawImage(background, x / 2 - background.getWidth() / 2, y / 2 - background.getHeight() / 2);
        }

        // Draw text onto background
        image.drawImage(text.getImage(), (x - text.getImage().getWidth()) / 2, (y - text.getImage().getHeight()) / 2);

        // Draw outline if needed
        if(outline) {
            image.setColor(OUTLINE_COLOR);
            image.drawRect(0, 0, x - 1, y - 1);
        }


        // Modified images


        // Create hover image
        hoveredImage = new GreenfootImage(image);
        hoveredImage.scale((int)(image.getWidth() * HOVER_SCALE), (int)(image.getHeight() * HOVER_SCALE));

        // Create clicked image
        clickedImage = new GreenfootImage(image);
        clickedImage.setColor(CLICK_COLOR);
        //for(int i=0; i<clickedImage.getWidth(); i++) for(int j=0; j<clickedImage.getHeight(); j++) {
            //if(clickedImage.getColorAt(i, j).getAlpha() != 0) clickedImage.fillRect(i, j, 1, 1);
        //}
        clickedImage.fill();

        updateAnimations();
    }




    /**
     * Sets the image of the button to the currently matching animation state.
     */
    protected void updateAnimations() {
        if(!enabled) super.setImage(image);
        else if(touched){
            if(pressed()) super.setImage(clickedImage);
            else super.setImage(hoveredImage);
        }
        else super.setImage(image);
    }

    /**
     * Analyses the mouse interactions each frame. Do not remove
     * any code here! Use the run method instead!
     * 
     * @deprecated If this method is not executed, the button will not work!
     */
    public void physicsUpdate()
    {
        updateTouchState();

        // Repainting animations
        updateAnimations();
        
        if(pressed()) pressTime++;
        run();
    }




    /**
     * Simulates clicking the button for the given time. The button will not
     * actually animate the click, however, click count and press time will
     * be updated and the actions for presses, clicks and releases
     * will be executed.
     * 
     * @param time The number of frames the button should (virtually) be pressed down.
     */
    public void click(int time) {
        if(!enabled) return;
        pressTime += time >= 0 ? time : 1;

        MouseInfo mouse = MouseInfoVisitor.newMouseInfo();
        MouseInfoVisitor.setActor(mouse, this);
        MouseInfoVisitor.setButton(mouse, 1);
        MouseInfoVisitor.setClickCount(mouse, 0);
        int cell = getWorld() != null ? getWorld().getCellSize() : 1;
        MouseInfoVisitor.setLoc(mouse, getX(), getY(), getX() * cell + cell / 2, getY() * cell + cell / 2);

        onPress(mouse);
        MouseInfoVisitor.setClickCount(mouse, 1);
        onRelease(mouse);
    }

    /**
     * Simulates clicking the button for a single frame. The button will not
     * actually animate the click, however, click count and press time will
     * be updated and {@code onPress()}, {@code onClick()} and {@code onRelease()}
     * will be executed.
     */
    public void click(){
        click(1);
    }


    /**
     * Checkes weather the mouse is touching the button right now. Is rather
     * cpu-intense so don't use it more than once per frame.
     * 
     * @return Wheather the mouse is touching the button
     */
    public void updateTouchState(){
        try{
            MouseInfo mouse = Greenfoot.getMouseInfo();
            touched = ActorVisitor.containsPoint(this, mouse.getX() * getWorld().getCellSize(), mouse.getY() * getWorld().getCellSize());
        }catch(Exception e){
            touched = false;
        }
    }


    /**
     * Executed once per frame as a replacement of the act method. Override
     * it to use.
     */
    public void run(){}

    @Override
    protected void onPress(MouseInfo mouse) {
        if(enabled) super.onPress(mouse);
    }

    @Override
    protected void onRelease(MouseInfo mouse) {
        if(enabled) super.onRelease(mouse);
    }


    /**
     * Resets the stats of the button.
     */
    public void reset(){
        clickCount = 0;
        pressTime = 0;
    }
    
    /**
     * Returns the number of times the button was clicked since the last
     * reset.
     * 
     * The Button is considered to be clicked when the mouse was pressed on
     * the button and then released on it again. It still counts as a click
     * if the mouse was dragged outside of the button while being pressed
     * but is released on the button.
     * 
     * @return The number of clicks
     */
    public int getClickCount(){
        return clickCount;
    }

    /**
     * Returns if the button is being touched by the mouse right now.
     * 
     * @return If the button is touched right now
     */
    public boolean touched(){
        return touched;
    }
    
    /**
     * Returns the number of frames the button was pressed since the last
     * reset.
     * 
     * It is considered as pressed if the boolean pressed() is true.
     * 
     * @return the time the button was pressed
     */
    public int getTimePressed(){
        return pressTime;
    }
    
    /**
     * Returns the title written onto the button.
     * 
     * @return The buttons text
     */
    public String getTitle(){
        return text.getContent();
    }

    /**
     * Returns the text object used in this button
     * 
     * @return The text object of this button
     */
    public Text getText(){
        return text;
    }


    /**
     * Override the title of the button.
     * 
     * @param title The new title
     */
    public void setTitle(String title){
        text.setContent(title);
        createAndSetImages();
    }

    /**
     * Sets the image of the button to the given one. If image is null,
     * the latest color will be used.
     * 
     * @param background The new image
     */
    public void setBackground(GreenfootImage background){
        this.background = background;
        createAndSetImages();
    }

    /**
     * Sets the (background) image of the button to the given one.
     */
    @Override
    public void setImage(GreenfootImage image) {
        setBackground(image);
    }

    /**
     * Sets the (background) image of the button to the given one.
     */
    @Override
    public void setImage(String filename) throws IllegalArgumentException {
        setBackground(new GreenfootImage(filename));
    }

    /**
     * Sets the text object the button is based on to the given one.
     * 
     * @param text The text object to set to
     */
    public void setText(Text text){
        if(text == null || this.text == text) return;
        this.text.removeUpdateAction(textUpdateAction);
        text.addUpdateAction(textUpdateAction);
        this.text = text;
        createAndSetImages();
    }

    /**
     * Sets weather the outline should be drawn or not
     * 
     * @param outline Weather the outline should be drawn
     */
    public void useOutline(boolean outline){
        this.outline = outline;
        createAndSetImages();
    }


    /**
     * Sets the images displayed to custom ones to allow more customisation.
     * 
     * @param image The image to be showm normally
     * @param hoveredImage The image to be showm when the mouse hovers over the button
     * @param clickedImage The image to be shown when the button is pressed down
     */
    public void setCustomImages(GreenfootImage image, GreenfootImage hoveredImage, GreenfootImage clickedImage){
        this.image = image;
        this.hoveredImage = hoveredImage;
        this.clickedImage = clickedImage;
        updateAnimations();
    }








    /**
     * Adds an action that will be executed whenever the button was clicked.
     * <p><b>Example:<b>
     * <p>{@code Button b = new Button("Hello World!");}
     * <p>{@code b.addClickAction(info -> System.out.println("Hello!"));}
     * 
     * @param mouse The action to add
     */
    @Override
    public Button addClickAction(Consumer<MouseInfo> mouse) {
        return (Button)super.addClickAction(mouse);
    }


    /**
     * Removes the given action from those that are ran whenever the button is clicked.
     * 
     * @param action The action to remove
     */
    public Button removeClickAction(Consumer<MouseInfo> action) {
        return (Button)super.removeClickAction(action);
    }

    @Override
    public Button addPressAction(Consumer<MouseInfo> mouse) {
        return (Button)super.addPressAction(mouse);
    }

    @Override
    public Button addReleaseAction(Consumer<MouseInfo> mouse) {
        return (Button)super.addReleaseAction(mouse);
    }

    @Override
    public Button addAddedAction(Consumer<World> world) {
        return (Button)super.addAddedAction(world);
    }

    @Override
    public Button removePressAction(Consumer<MouseInfo> action) {
        return (Button)super.removePressAction(action);
    }

    @Override
    public Button removeReleaseAction(Consumer<MouseInfo> action) {
        return (Button)super.removeReleaseAction(action);
    }
    @Override
    public AdvancedActor removeAddedAction(Consumer<World> action) {
        return (Button)super.removeAddedAction(action);
    }
}