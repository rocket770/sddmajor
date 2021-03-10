package rccookie.game.util;

import greenfoot.GreenfootImage;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;

@IgnoreOnRaycasts
public class Time extends greenfoot.Actor{
    public static double MAX_DELTA_TIME = 0.08;
    public static double AVERAGE_DELTA_TIME = 0.01;
    long lastNanos;
    double deltaTime = AVERAGE_DELTA_TIME;
    public double timeScale = 1;
    public boolean useStaticFramelength = false;
    public double staticFramelength = 0.001;
    long frameIndex = 0;

    double timeSinceFpsUpdate = 0;
    int frameNum;
    int frameCount;
    int stableFps;
    
    public Time(){
        setImage((GreenfootImage)null);
    }
    public void act(){
        long currentNanos = System.nanoTime();
        deltaTime = (currentNanos - lastNanos) / 1000000000d;
        lastNanos = currentNanos;
        deltaTime %= 1;


        timeSinceFpsUpdate += deltaTime;
        frameNum++;
        frameCount += fps();
        if(timeSinceFpsUpdate >= 1){
            timeSinceFpsUpdate %= 1;
            stableFps = (int)(frameCount / (double)frameNum);
            frameNum = frameCount = 0;
        }
        frameIndex++;
    }
    
    /**
     * Fraction of time since the last frame
     */
    public double deltaTime(){
        if(useStaticFramelength) return staticFramelength;
        if(deltaTime < MAX_DELTA_TIME) return deltaTime * timeScale;
        return MAX_DELTA_TIME * timeScale;
    }

    public void setTimeScale(double scale){
        timeScale = scale;
    }
    
    /**
     * Updated once per frame
     */
    public int fps(){
        if(deltaTime == 0) return 2000;
        return (int)(1 / deltaTime);
    }
    
    /**
     * Updated once per second
     */
    public int stableFps(){
        return stableFps;
    }
    
    public long frameIndex(){
        return frameIndex;
    }
    
    public void resetFrameIndex(){
        frameIndex = 0;
    }
}