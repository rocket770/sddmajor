package rccookie.geometry;

public class Edge2D extends Edge<Edge2D, Ray2D, Vector2D> {

    private static final long serialVersionUID = 463412350567874758L;

    public Edge2D(Vector2D root, Vector2D edge) {
        this(new Ray2D(root, edge));
    }
    public Edge2D(Edge2D copy) {
        this(copy.ray());
    }
    public Edge2D(Ray2D ray) {
        super(ray);
    }


    @Override
    protected Edge2D clone() {
        return new Edge2D(this);
    }



    public static double[] intersection(Edge2D e, Ray2D r) {
        double[] intersection = Ray2D.intersection(e.ray(), r);
        return (intersection == null || intersection[0] < 0 || intersection[0] > 1) ? null : intersection;
    }

    public static double[] intersection(Edge2D e, Edge2D f) {
        double[] intersection = Ray2D.intersection(e.ray(), f.ray());
        return (intersection == null || intersection[0] < 0 || intersection[0] > 1 || intersection[1] < 0 || intersection[1] > 1) ? null : intersection;
    }

    public static Double intersection(Edge2D e, Vector2D p) {
        double posA = (p.x() - e.root().x()) / e.edge().x();
        return (posA >= 0 && posA <= 1 && (p.y() - e.root().y()) / e.edge().y() == posA) ? posA : null;
    }
}
