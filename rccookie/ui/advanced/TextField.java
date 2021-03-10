package rccookie.ui.advanced;

import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.ui.basic.Button;
import rccookie.ui.basic.Text;

@IgnoreOnRaycasts
public class TextField extends Button {
    private static final long serialVersionUID = -874972441824558399L;
    
    public static final int BORDER = 1;
    public static final int GAP_BEFORE_TEXT = 2;
    public static final int Y_THIGHTENING = 1;
    public static final Color OUTLINE_COLOR = Color.DARK_GRAY;
    public static final Color DEF_BACKGROUND_COLOR = Color.GRAY;
    

    public TextField(Text defaultText) {
        this(defaultText, 100);
    }
    public TextField(Text defaultText, int width) {
        this(defaultText, width, defaultText.getImage().getHeight() + 2 * BORDER - 2 * Y_THIGHTENING);
    }
    public TextField(Text defaultText, int x, int y) {
        super(defaultText, x, y);
        setupAction(defaultText.getContent() + ":");
    }

    protected void setupAction(String question) {
        addClickAction(info -> text.setContent(Greenfoot.ask(question)));
    }


    @Override
    protected void createAndSetImages() {
        // Create correct sized image
        image = new GreenfootImage(minX, minY);

        // Fill background around text with text background color or default color, if null
        if(text.getBackgroundColor() == null) {
            image.setColor(DEF_BACKGROUND_COLOR);
            image.fillRect(BORDER, BORDER, minX - 2 * BORDER, minY - 2 * BORDER);
        }
        else {
            image.setColor(text.getBackgroundColor());
            if(minX > text.getImage().getWidth() + 2 * BORDER) {
                int delta = minX - text.getImage().getWidth() - BORDER - GAP_BEFORE_TEXT;
                image.fillRect(BORDER, BORDER, GAP_BEFORE_TEXT, text.getImage().getHeight());
                image.fillRect(image.getWidth() - delta, BORDER, delta, image.getHeight() - 2 * BORDER);
            }
            if(minY > text.getImage().getHeight() + 2 * BORDER){
                int deltaY = minY - 2 * BORDER - text.getImage().getHeight();
                image.fillRect(BORDER, BORDER, text.getImage().getWidth() - 2 * BORDER, deltaY / 2);
                image.fillRect(BORDER, image.getHeight() - deltaY / 2 - BORDER, text.getImage().getWidth() - 2 * BORDER, deltaY / 2);
            }
        }

        // Draw text onto background
        image.drawImage(text.getImage(), BORDER + GAP_BEFORE_TEXT, (minY - text.getImage().getHeight()) / 2);

        // Draw outline
        image.setColor(OUTLINE_COLOR);
        for(int i=0; i<BORDER; i++) image.drawRect(i, i, minX - 1 - 2 * i, minY - 1 - 2 * i);


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
}
