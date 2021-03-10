package rccookie.geometry;

import java.util.Objects;

import rccookie.data.Saveable;

public abstract class Edge<E extends Edge<E,R,V>, R extends Ray<R,V>, V extends AbstractVector<V>> implements Cloneable, Saveable {

    private static final long serialVersionUID = 3414466976248623695L;


    private final R ray;


    public Edge(Edge<E,R,V> copy) {
        this(copy.ray());
    }
    public Edge(Ray<R,V> ray) {
        this.ray = ray.clone();
    }


    public R ray() { return ray; }

    public V root() { return ray().root(); }

    public V edge() { return ray().direction(); }

    public V get(double index) { return ray.get(index); }

    public double length() { return edge().abs(); }


    @Override
    protected abstract E clone();


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Edge3D)) return false;
        return Objects.equals(ray(), ((Edge3D)obj).ray());
    }

    @Override
    public int hashCode() {
        return ray().hashCode() + 1;
    }

    @Override
    public String toString() {
        return "{root: " + root() + ", edge: " + edge() + " (" + edge().abs() + " units)}";
    }
}
