package rccookie.rendering;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import rccookie.geometry.Ray3D;
import rccookie.geometry.Rotation;
import rccookie.geometry.Transform3D;
import rccookie.geometry.Vector3D;

public class Camera extends Actor implements GameObject {

    public static final double DEF_FOV = 90;
    public static final double DEF_VISION_RANGE = 1000;
    public static final Color SKYBOX_COLOR = Color.LIGHT_GRAY;

    /**
     * Saves the remaining time until each rendered world should be acted.
     */
    static HashMap<World3D, Integer> timeUntilAct = new HashMap<>();


    public final World3D world;

    private Transform3D transform;
    private double foV = DEF_FOV;
    private double visionRange = DEF_VISION_RANGE;


    public Camera(World3D world) {
        this(world, 600, 400);
    }
    public Camera(World3D world, int width, int height) {
        transform = new Transform3D();
        this.world = world;
        setImage(new GreenfootImage(width, height));
        render();
    }






    public void render() {
        updatePanelLocations();
        ArrayList<RenderPanel> panels = zSortedPanelsInRange();
        int width = getImage().getWidth(), height = getImage().getHeight();
        double foVX = foV, foVY = foV * (height / width);
        Vector3D rayStart = transform.location;
        Vector3D cameraDirection = Vector3D.angledVector(transform.rotation, 1);





        GreenfootImage renderdImage = new GreenfootImage(width, height);


        for(int x=0; x<width; x++) {
            double xAngle = (x / width - 0.5 * width) * foVX;
            for(int y=0; y<height; y++) {
                double yAngle = (y / height - 0.5 * height) * foVY;

                Vector3D rayDir = cameraDirection;
                Vector3D yOff = new Vector3D(0, 0, yAngle);
                Vector3D xOff = Vector3D.cross(rayDir, yOff).norm().scale(xAngle);
                //rayDir = yOff.add(xOff).add(rayDir);

                renderdImage.setColorAt(x, y, colorAt(new Ray3D(rayStart.added(xOff.scale(width)).add(yOff.scale(height)), rayDir), panels));
            }
        }


        setImage(renderdImage);
    }




    private Color colorAt(Ray3D ray, ArrayList<RenderPanel> panels) {
        RenderPanel hit = getHit(ray, panels);
        return hit != null ? hit.pureColor() : SKYBOX_COLOR;
    }


    private RenderPanel getHit(Ray3D ray, ArrayList<RenderPanel> panels) {
        for(RenderPanel panel : panels) {
            if(panel.intersects(ray)) return panel;
        }
        return null;
    }









    private ArrayList<RenderPanel> zSortedPanelsInRange() {
        ArrayList<RenderPanel> allPanels = zSortedPanels();

        for(int i=0; i<allPanels.size(); i++) {
            if(Vector3D.distance(allPanels.get(i).location(), transform.location) > visionRange)
                return (ArrayList<RenderPanel>)allPanels.subList(0, i);
        }

        return allPanels;
    }

    /**
     * Returns the object's panels of the world sorted in their distance to the camera.
     * 
     * @return All objects sorted by the distance to the camera
     */
    private ArrayList<RenderPanel> zSortedPanels() {
        ArrayList<RenderPanel> panels = renderPanels();
        panels.sort(new Comparator<RenderPanel>(){
            @Override
            public int compare(RenderPanel o1, RenderPanel o2) {
                double d1 = Vector3D.distance(transform.location, o1.location());
                double d2 = Vector3D.distance(transform.location, o2.location());
                if(d1 == d2) return 0;
                return d1 < d2 ? -1 : 1;
            }
        });
        return panels;
    }

    /**
     * Returns all render panels in the world.
     * 
     * @return All render panels
     */
    private ArrayList<RenderPanel> renderPanels() {
        ArrayList<RenderPanel> panels = new ArrayList<>();
        for(GameObject o : world.getObjects()) panels.addAll(o.renderObject().panels);
        return panels;
    }

    /**
     * Updates the render panel's locations to the proper location relative to their game object.
     */
    private void updatePanelLocations() {
        for(GameObject o : world.getObjects()) {
            for(RenderPanel p : o.renderObject().panels) {
                p.setLocation(o.location());
            }
        }
    }


















    /**
     * If the underlying world wasn't acted this frame, all its actors and finally itself will be acted. Also, the
     * container will be repainted.
     */
    @Override
    public void act() {

        //Make sure that having multiple containers pointing at the same map won't act them multiple times
        if(getTimeUntilAct(world) <= 0){
            timeUntilAct.put(world, numberOfActsPerFrame());
            world.act();
        }
        render();

        timeUntilAct.put(world, getTimeUntilAct(world) - 1);
    }


    /**
     * Returns the remaining time until the given world has to be acted again. Makes sure that the output is not {@code null}.
     * 
     * @param world The world to get the remaining time for
     * @return The remaining time
     */
    static int getTimeUntilAct(World3D world){
        Integer count = timeUntilAct.get(world);
        if(count == null) count = 0;
        return count;
    }

    /**
     * Returns the number of containers in this world that have the same underlying world, including itself.
     * 
     * @return The number of containers with this underlying world in this world
     */
    int numberOfActsPerFrame(){
        int count = 0;
        for(Camera c : getWorld().getObjects(Camera.class)){
            if(c.world == world) count++;
        }
        return count;
    }




































    @Override
    public Vector3D location() {
        return transform.location.clone();
    }

    @Override
    public void setLocation(Vector3D location) {
        if(location == null) return;
        transform.location = location.clone();
    }

    @Override
    public Rotation rotation() {
        return transform.rotation.clone();
    }

    @Override
    public void setRotation(Rotation rotation) {
        if(rotation == null) return;
        transform.rotation = rotation.clone();
    }

    @Override
    public Vector3D scale() {
        return transform.scale.clone();
    }

    @Override
    public void setScale(Vector3D scale) {
        if(scale == null) return;
        transform.scale = scale.clone();
    }

    @Override
    public Transform3D transform() {
        return transform.clone();
    }

    @Override
    public void setTransform(Transform3D transform) {
        if(transform == null) return;
        this.transform = transform;
    }


    /**
     * Cameras do not have a render object. Thus this will always return null.
     */
    @Override
    public RenderObject renderObject() {
        return null;
    }

    /**
     * Cameras do not have a render object. Doing this will have no effect.
     * 
     * @param renderObject The render object that will not be used for the camera
     */
    @Override
    public void setRenderObject(RenderObject renderObject) {}


    public void earlyUpdate(double deltaTime) {}
    public void update(double deltaTime) {}
    public void lateUpdate(double deltaTime) {}
}
