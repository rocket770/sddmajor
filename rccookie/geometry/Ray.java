package rccookie.geometry;

import java.util.Objects;

import rccookie.data.Saveable;

public abstract class Ray<R extends Ray<R,V>, V extends AbstractVector<V>> implements Cloneable, Saveable {

    private static final long serialVersionUID = 9028705762521913495L;

    private final V root;
    private final V direction;

    public Ray(Ray<R,V> copy) {
        this(copy.root(), copy.direction());
    }

    public Ray(V root, V direction) {
        this.root = root.clone();
        this.direction = direction.clone();
    }



    public V get(double r) {
        return direction().scaled(r).add(root());
    }

    public V root() { return root; }
    public V direction() { return direction; }

    public int size() {
        return root.size();
    }

    public Ray2D get2D() {
        return new Ray2D(root().get2D(), direction().get2D());
    }

    public Ray3D get3D() {
        return new Ray3D(root.get3D(), direction.get3D());
    }



    @Override
    protected abstract R clone();



    @Override
    public String toString() {
        return "ray from " + root() + " towards " + direction();
    }

    @Override
    public int hashCode() {
        return root().hashCode() * direction().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Ray3D)) return false;
        return Objects.equals(root(), ((Ray3D)obj).root()) && Objects.equals(direction(), ((Ray3D)obj).direction());
    }
}
