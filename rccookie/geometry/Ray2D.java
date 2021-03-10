package rccookie.geometry;

public class Ray2D extends Ray<Ray2D, Vector2D> {

    private static final long serialVersionUID = -8650630289610786798L;

    public Ray2D(Vector2D direction) {
        this(new Vector2D(), direction);
    }

    public Ray2D(Ray2D copy) {
        this(copy.root(), copy.direction());
    }

    public Ray2D(Vector2D root, Vector2D direction) {
        super(root, direction);
    }


    @Override
    protected Ray2D clone() {
        return new Ray2D(this);
    }



    /**
     * Calculates weather and if so where the two given rays intersect.
     * 
     * @param a The first ray
     * @param b The second ray
     * @return A two dimensional array containing the two distances from the rays' roots measured
     * in their direction vectors, or {@code null} if they are parallel
     */
    public static double[] intersection(Ray2D a, Ray2D b) {
        if(Vector2D.angleBetween(a.direction(), b.direction()) == 0) return null;

        double aHit = a.direction().x() * a.root().y() - a.direction().y() * a.root().x();
        aHit += a.direction().y() * b.root().x() - a.direction().x() * b.root().y();
        aHit /= a.direction().x() * b.direction().y() - a.direction().y() * b.direction().x();

        double bHit = b.direction().x() * b.root().y() - b.direction().y() * b.root().x();
        bHit += b.direction().y() * a.root().x() - b.direction().x() * a.root().y();
        bHit /= b.direction().x() * a.direction().y() - b.direction().y() * a.direction().x();

        return new double[] {bHit, aHit};
    }
}
