package rccookie.ui.util;

import java.util.Objects;

import greenfoot.Color;

/**
 * Stores a color theme with differently prioritized colors. If a color lower
 * than the lowermost specified color of the theme is requested, the lowest
 * color will be returned.
 */
public class Theme {

    /**
     * A fully transparent colour. When not transparent this has the color
     * black.
     */
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /**
     * The different colors of this theme.
     */
    private final Color[] colors;

    /**
     * Creates a new theme. The main color must not be null, more colors may be
     * {@code null} or an empty array.
     * 
     * @param mainColor The main color of this theme. Must not be null
     * @param moreColors Less often used colors, sorted in priority
     */
    public Theme(Color mainColor, Color... moreColors) {
        this(generateArray(mainColor, moreColors));
    }

    private Theme(Color[] colors) {
        this.colors = colors;
    }

    private static final Color[] generateArray(Color mainColor, Color[] moreColors) {
        Objects.requireNonNull(mainColor);
        if(moreColors == null) return new Color[] {mainColor};
        Color[] colors = new Color[moreColors.length + 1];
        colors[0] = mainColor;
        System.arraycopy(moreColors, 0, colors, 1, moreColors.length);
        return colors;
    }

    /**
     * Returns the main color of this theme.
     * 
     * @return The main color
     */
    public Color main() {
        return colors[0];
    }

    /**
     * Returns the second color of this theme. If a second color was not
     * specified the main color will be returned.
     * 
     * @return The second color
     */
    public Color second() {
        return get(1);
    }

    /**
     * Returns the third color of this theme. If a third color was not
     * specified the second color will be returned. If that was not
     * specified either the main color will be returned.
     * 
     * @return The third color
     */
    public Color third() {
        return get(2);
    }

    /**
     * Returns the color at the specified index. If that color was not specified
     * the lowest specified color will be returned.
     * <p>The color at index {@code 0} will be the main color, the color at
     * index  {@code 1} will be the second color and so on.
     * 
     * @param colorIndex The index of the color
     * @return The color at that index
     */
    public Color get(int colorIndex) {
        if(colorIndex < colors.length) return colors[colorIndex];
        return colors[colors.length - 1];
    }

    /**
     * Returns weather the color at the specified index is specificly specified.
     * {@code 0} should always return {@code true}, negative numbers will return
     * {@code false} in any way.
     * 
     * @param index The color index
     * @return {@code true} if the color was specified
     */
    public boolean specified(int index) {
        if(index < 0) return false;
        return index < size();
    }

    /**
     * Returns the number of colors specificly specified in this theme.
     * 
     * @return The number of colors in this theme
     */
    public int size() {
        return colors.length;
    }

    /**
     * Returns an array containing all specificly specified colors of this theme.
     * 
     * @return An array with this theme's colors
     */
    public Color[] toArray() {
        Color[] array = new Color[colors.length];
        System.arraycopy(colors, 0, array, 0, colors.length);
        return array;
    }


    public Theme subTheme(int fromColor) {
        if(!specified(fromColor)) return new Theme(get(fromColor));
        Color[] colors = new Color[size() - fromColor];
        System.arraycopy(this.colors, fromColor, colors, 0, colors.length);
        return new Theme(colors);
    }
}
