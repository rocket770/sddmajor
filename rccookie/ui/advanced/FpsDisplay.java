package rccookie.ui.advanced;

import rccookie.game.util.ActorTag;
import rccookie.game.util.Time;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.ui.basic.Button;
import rccookie.ui.basic.Text;
/**
 * The fps display shows the current fps in one of two modes: either
 * in realtime (based on only the last frame) or the average (of the
 * last second) (as calculated in packages.tools.Time). When clicked
 * on it it switches modes.
 * 
 * @see rccookie.tools.Time
 * @author RcCookie
 * @version 1.0
 */
@IgnoreOnRaycasts
public class FpsDisplay extends Button {

    private static final long serialVersionUID = -7312819523785019590L;

    /**
     * The time object that actually calculats the fps.
     */
    protected Time time;

    /**
     * Weather the output is the average(=true) or realtime (=false).
     */
    boolean stableMode;


    /**
     * Constructs a new fps display in stable mode.
     */
    public FpsDisplay(){
        this(true);
    }

    /**
     * Constructs a new fps display in the given mode.
     * 
     * @param stableMode Weather the display shows stable fps by default
     */
    public FpsDisplay(boolean stableMode){
        // TODO: WARNING: Does not compile properly without instantiating an instance explicitly!
        super(new Text("FPS: --", null), 70, 20);
        ActorTag.tag(this, "ignoreOnClear");
        time = new Time();
        this.stableMode = stableMode;
        addClickAction(info -> switchMode());
    }


    /**
     * Runs the time object and updates the text output.
     */
    @Override 
    public void run(){
        time.act();
        getText().setContent("FPS: " + currentModeFps());
    }
    

    /**
     * Returns the current realtime fps.
     * 
     * @return The current realtime fps
     */
    public int fps(){
        return time.fps();
    }

    /**
     * Returns the current average fps.
     * 
     * @return The current average fps
     */
    public int stableFps(){
        return time.stableFps();
    }

    /**
     * Returns the fps in the current mode.
     * @return The current fps in the current mode
     */
    public int currentModeFps(){
        if(stableMode) return stableFps();
        else return fps();
    }


    /**
     * Returns the duration of the last frame as a fraction of a second.
     * 
     * @return The duration of the last frame as a fraction of a second
     * @see packages.tools.Time.deltaTime()
     */
    public double deltaTime(){
        return time.deltaTime();
    }
    

    /**
     * Switches fps mode.
     */
    public void switchMode(){
        stableMode = !stableMode;
    }
}