package rccookie.ui.advanced;

import greenfoot.Color;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.ui.basic.Slider;

@IgnoreOnRaycasts
public class ExponentialSlider extends Slider {

    private static final long serialVersionUID = 8159489652228360876L;
    
    private final int exponent;


    public ExponentialSlider(double min, double max, int length, Color sliderCol, Color handleCol) {
        this(min, max, length, sliderCol, handleCol, 2);
    }

    public ExponentialSlider(double min, double max, int length, Color sliderCol, Color handleCol, int exponent) {
        super(Math.sqrt(min), Math.sqrt(max), length, sliderCol, handleCol);
        this.exponent = exponent;
    }

    @Override
    public double getValue() {
        return Math.pow(super.getValue(), exponent);
    }

    @Override
    public void setValue(double value) {
        super.setValue(Math.pow(value, 1d / exponent));
    }
}