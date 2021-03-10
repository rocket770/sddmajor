package rccookie.rendering;

import rccookie.geometry.Ray3D;
import rccookie.geometry.Vector3D;

public class RenderSphere extends RenderPanel {
    public final double radius;
    public Vector3D offset;

    public RenderSphere(double radius) {
        this(radius, new Vector3D());
    }
    public RenderSphere(double radius, Vector3D offset) {
        this.radius = radius;
        this.offset = offset;
    }

    @Override
    public Ray3D getReflection(Ray3D ray) {
        Vector3D hitLoc = getIntersectionLoc(ray);
        if(hitLoc == null) return null;

        Vector3D reflectionNorm = Vector3D.between(location().added(offset), hitLoc);
        Vector3D reflected = Vector3D.reflect(ray.direction(), reflectionNorm);

        return new Ray3D(hitLoc, reflected);
    }

    @Override
    public Vector3D getIntersectionLoc(Ray3D ray) {
        Vector3D centerToCamera = Vector3D.between(location().added(offset).added(offset), ray.root());
        double dirXctc = Vector3D.dot(ray.direction(), centerToCamera);

        double delta = Math.pow(Vector3D.dot(ray.direction(), (centerToCamera)), 2) - (Math.pow(centerToCamera.abs(), 2) - radius * radius);

        if(delta < 0) return null;
        
        double rtDelta = Math.sqrt(delta);

        //Always smaller or equal to the smaller hit
        double hitIndex = -dirXctc - rtDelta;

        if(delta == 0 || hitIndex >= 0) {
            if(hitIndex < 0) return null;
            return ray.direction().scaled(hitIndex).add(ray.root());
        }

        //Only try if first point is behind camera (remove if no rendering from insides)
        hitIndex = -dirXctc + rtDelta;
        if(hitIndex < 0) return null;
        return ray.direction().scaled(hitIndex).add(ray.root());
    }


    @Override
    public boolean intersects(Ray3D ray) {
        return Vector3D.distanceBetween(ray, location().added(offset)) <= radius;
    }
}
