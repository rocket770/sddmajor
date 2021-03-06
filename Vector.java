import greenfoot.*;
public final class Vector
{
    public double dx;
    public double dy;
    public int direction;
    private double length;
    /**
     * Create a vector with given direction and length. The direction should be in
     * the range [0..359], where 0 is EAST, and degrees increase clockwise.
     */
    
    public Vector(){
        // Generate A new Empty Vector
    }
    
    public Vector(int direction, double length)
    {
        this.length = length;
        this.direction = direction;
        updateCartesian();
    }

    /**
     * Create a vector by specifying the x and y offsets from start to end points.
     */
    public Vector(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
        updatePolar();
    }
    
    public float getRotation(double x, double y){
        return (float) Math.toDegrees(Math.atan2(x, y));
    }

    /**
     * Set the direction of this vector, leaving the length intact.
     */
    public void setDirection(int direction) 
    {
        this.direction = direction;
        updateCartesian();
    }

    /**
     * Add another vector to this vector.
     */
    public void add(Vector other) 
    {
        dx += other.dx;
        dy += other.dy;
        updatePolar();
    }

    /**
     * Set the length of this vector, leaving the direction intact.
     */
    public void setLength(double length) 
    {
        this.length = length;
        updateCartesian();
    }

    /**
     * Scale this vector up (factor > 1) or down (factor < 1). The direction
     * remains unchanged.
     */
    public void scale(double factor) 
    {
        length = length * factor;
        updateCartesian();
    }

    public Vector fromAngle(float angle){
        float x = (float)Math.cos(angle);
        float y = (float)Math.sin(angle);
        Vector z = new Vector(x,y);
        return z;
    }

    /**
     * Set this vector to the neutral vector (length 0).
     */
    public void setNeutral() {
        dx = 0.0;
        dy = 0.0;
        length = 0.0;
        direction = 0;
    }

    /**
     * Revert to horizontal component of this movement vector.
     */
    public void revertHorizontal() {
        dx = -dx;
        updatePolar();
    }

    /**
     * Revert to vertical component of this movement vector.
     */
    public void revertVertical() {
        dy = -dy;
        updatePolar();
    }

    /**
     * Return the x offset of this vector (start to end point).
     */
    public double x() {
        return dx;
    }

    /**
     * Return the y offset of this vector (start to end point).
     */
    public double y() {
        return  dy;
    }

    /**
     * Return the direction of this vector (in degrees). 0 is EAST.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Return the length of this vector.
     */
    public double getLength() {
        return length;
    }

    /**
     * Update the direction and length fom the current dx, dy.
     */
    private void updatePolar() 
    {
        this.direction = (int) Math.toDegrees(Math.atan2(dy, dx));
        this.length = Math.sqrt(dx*dx+dy*dy);
    }   

    /**
     * Update dx and dy from the current direction and length.
     */
    private void updateCartesian() 
    {
        dx = length * Math.cos(Math.toRadians(direction));
        dy = length * Math.sin(Math.toRadians(direction));   
    }

    /**
     * Return a copy of this vector.
     */
    public Vector copy() 
    {
        Vector copy = new Vector();
        copy.dx = dx;
        copy.dy = dy;
        copy.direction = direction;
        copy.length = length;
        return copy;
    }    
}

