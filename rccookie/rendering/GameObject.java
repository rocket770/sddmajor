package rccookie.rendering;

import rccookie.geometry.Rotation;
import rccookie.geometry.Transform3D;
import rccookie.geometry.Vector3D;

public interface GameObject {

    /**
     * Returns a modidiable vector with the objects coordinates.
     * <p>Modifiable means that the vector returned is not actually the instance
     * that is this objects location, so changing it will not effect this object.
     * 
     * @return The objects current location
     */
    public Vector3D location();


    /**
     * Sets the location of this object to the given one.
     * <p>The location vector is not actually saved to the given instace so modifying it afterwards
     * will not have any effect.
     * 
     * @param location The new location of the object
     */
    public void setLocation(Vector3D location);


    /**
     * Returns a modidiable rotation object with the objects rotation.
     * <p>Modifiable means that the rotation returned is not actually the instance
     * that is this objects rotation, so changing it will not effect this object.
     * 
     * @return The objects current rotationn
     */
    public Rotation rotation();


    /**
     * Sets the rotation of this object to the given one.
     * <p>The rotation object is not actually saved to the given instace so modifying it afterwards
     * will not have any effect.
     * 
     * @param rotation The new rotation of the object
     */
    public void setRotation(Rotation rotation);


    /**
     * Returns a modidiable vector with the objects scale.
     * <p>Modifiable means that the vector returned is not actually the instance
     * that is this objects scale, so changing it will not effect this object.
     * 
     * @return The objects current scale
     */
    public Vector3D scale();


    /**
     * Sets the scale of this object to the given one.
     * <p>The scale vector is not actually saved to the given instace so modifying it afterwards
     * will not have any effect.
     * 
     * @param scale The new scale of the object
     */
    public void setScale(Vector3D scale);


    /**
     * Returns a modidiable transform object with the objects transform.
     * <p>Modifiable means that the transform object returned is not actually the instance
     * that is this objects transform, so changing it will not effect this object.
     * 
     * @return The objects current fransform
     */
    public Transform3D transform();


    /**
     * Sets the fransform of this object to the given one.
     * <p>The transform object is not actually saved to the given instace so modifying it afterwards
     * will not have any effect.
     * 
     * @param transform The new transform of the object
     */
    public void setTransform(Transform3D transform);


    /**
     * Returns the render object of this object. This may be {@code null}, if there should not be anything
     * rendered.
     * 
     * @return The render object of this object, or {@code null}
     */
    public RenderObject renderObject();


    /**
     * Sets the current rendering object of this object. May be {@code null} if nothing should be rendered.
     * 
     * @param renderObject The new render object for this object
     */
    public void setRenderObject(RenderObject renderObject);



    public void earlyUpdate(double deltaTime);
    public void update(double deltaTime);
    public void lateUpdate(double deltaTime);
}
