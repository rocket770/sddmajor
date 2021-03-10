package rccookie.ui.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import greenfoot.*;
import rccookie.game.AdvancedActor;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.util.ClassTag;

/**
 * The text class is used to store and display some text and to input
 * and modify the text in other objects.
 * 
 * @author RcCookie
 * @version 1.0
 */
@IgnoreOnRaycasts
public class Text extends AdvancedActor {

    private static final long serialVersionUID = -7943094443867868226L;

    static {
        ClassTag.tag(Text.class, "ui");
    }
    

    /**
     * The text of the text.
     */
    private String content = "";

    /**
     * The fontSize of the dext drawn.
     */
    private int fontSize;

    /**
     * The color of the letters of the text.
     */
    private Color textColor;

    /**
     * The color of the background image of the text.
     */
    private Color backgroundColor;


    private final List<Consumer<Object>> updateActions = new ArrayList<>();
    

    /**
     * Constructs an empty text with default settings.
     */
    public Text(){}

    /**
     * Constructs a new text with the given content.
     * 
     * @param content The text of the text
     */
    public Text(String content){
        this(content, 20, Color.BLACK, null);
    }

    /**
     * Constructs a new text with the given content written in the given
     * font size.
     * 
     * @param content The text of the text
     * @param fontSize The font size of the text
     */
    public Text(String content, int fontSize){
        this(content, fontSize, Color.BLACK, null);
    }

    /**
     * Constructs a new text with the given content written in the given
     * color.
     * 
     * @param content The text of the text
     * @param textColor The color of the text
     */
    public Text(String content, Color textColor){
        this(content, textColor, null);
    }

    /**
     * Constructs a new text with the given content written in the given
     * color and font size.
     * 
     * @param content The text of the text
     * @param fontSize The font size of the text
     * @param textColor The color of the text
     */
    public Text(String content, int fontSize, Color textColor){
        this(content, fontSize, textColor, null);
    }

    /**
     * Constructs a new text with the given content written in the given
     * color onto the given background color.
     * 
     * @param content The text of the text
     * @param textColor The color of the text
     * @param backgroundColor The color of the background
     */
    public Text(String content, Color textColor, Color backgroundColor) {
        this(content, 20, textColor, backgroundColor);
    }

    /**
     * Constructs a new text with the given content written in the given
     * color and font size. The background will be filled with the given
     * backgound color.
     * 
     * @param content The text of the text
     * @param fontSize The font size of the text
     * @param textColor The color of the text
     * @param backgroundColor The color of the background
     */
    public Text(String content, int fontSize, Color textColor, Color backgroundColor){
        this.content = content;
        this.fontSize = fontSize;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        updateImage();
    }
    
    
    /**
     * Updates the image of the text according to the current settings.
     */
    protected void updateImage(){
        if(content == null || content.equals("")) {
            GreenfootImage image = new GreenfootImage(1, 1);
            image.setColorAt(0, 0, backgroundColor);
            setImage(image);
        }
        else setImage(new GreenfootImage(content, fontSize, textColor, backgroundColor));
        for (Consumer<Object> action : updateActions) action.accept(null);
    }


    /**
     * Sets the text of the text to the given string.
     * 
     * @param content The new text
     */
    public void setContent(String content){
        this.content = content;
        updateImage();
    }

    /**
     * Sets the font size of the text (also of the already written stuff) to
     * the given one.
     * 
     * @param fontSize The new font size
     */
    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
        updateImage();
    }

    /**
     * Sets the color of the text (also of the already written stuff) to the
     * given one.
     * 
     * @param color The new text color
     */
    public void setColor(Color color){
        this.textColor = color;
        updateImage();
    }
    
    /**
     * Sets the background color to the given one.
     * 
     * @param background The new background color
     */
    public void setBackgroundColor(Color background){
        this.backgroundColor = background;
        updateImage();
    }
    


    
    /**
     * Returns the text of the text.
     * 
     * @return The text of the text
     */
    public String getContent(){
        return content;
    }

    /**
     * Returns the color of the text.
     * 
     * @return The color of the text
     */
    public Color getColor(){
        return textColor;
    }

    /**
     * Returns the background color of the text.
     * 
     * @return The background color of the text
     */
    public Color getBackgroundColor(){
        return backgroundColor;
    }

    /**
     * Returns the font size of the text.
     * 
     * @return The font size of the text
     */
    public int getFontSize(){
        return fontSize;
    }



    /**
     * Adds an action that will be executed whenever the image of the text
     * gets updated (usually meaning it changed).
     * 
     * @param nothing The action to add
     */
    public void addUpdateAction(Consumer<Object> nothing) {
        if(nothing == null) return;
        updateActions.add(nothing);
    }

    /**
     * Removes the given action from being excuted whenever the image
     * of the text gets updated.
     * 
     * @param action The action to remove
     */
    public void removeUpdateAction(Consumer<Object> action) {
        updateActions.remove(action);
    }
}