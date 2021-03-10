package rccookie.rendering;

import java.util.ArrayList;
import java.util.List;

import rccookie.geometry.Ray3D;
import rccookie.geometry.Vector3D;

public class RenderPolygon extends RenderPanel {

    public static final double DEF_SIZE = 10;

    public static final double RANGE = 0.0000001;
    public static final double MIN_FOR_INSIDE = 360 - RANGE;

    private final List<Vector3D> vertexes = new ArrayList<>();



    public RenderPolygon() {
        this(defVertexes());
    }
    private static List<Vector3D> defVertexes() {
        List<Vector3D> vertexes = new ArrayList<>();
        vertexes.add(new Vector3D(0, -DEF_SIZE, -DEF_SIZE));
        vertexes.add(new Vector3D(0, -DEF_SIZE, DEF_SIZE));
        vertexes.add(new Vector3D(0, DEF_SIZE, DEF_SIZE));
        vertexes.add(new Vector3D(0, DEF_SIZE, -DEF_SIZE));
        return vertexes;
    }
    public RenderPolygon(List<Vector3D> vertexes) {
        if(vertexes.size() < 3) throw new IllegalArgumentException("a polygon cannot consist of only " + vertexes.size() + " vertexes");
        this.vertexes.addAll(vertexes);
    }




    @Override
    public Ray3D getReflection(Ray3D ray) {
        
        if(!intersects(ray)) return null;

        return new Ray3D(
            getHitOnPlane(ray),
            Vector3D.reflect(
                getNormal(),
                ray.direction()
            )
        );
    }

    @Override
    public Vector3D getIntersectionLoc(Ray3D ray) {
        Vector3D hit = getHitOnPlane(ray);
        return isInside(hit) ? hit : null;
    }


    private boolean isInside(Vector3D loc) {
        if(loc == null) return false;

        double angleSum = 0;

        for(int i=0; i<vertexes.size(); i++) {
            Vector3D v1 = Vector3D.between(loc, vertexes.get(i));
            Vector3D v2 = Vector3D.between(loc, vertexes.get((i+1) % vertexes.size()));
            angleSum += v1.angleTo(v2);
        }
        return angleSum >= MIN_FOR_INSIDE;
    }


    private Vector3D getHitOnPlane(Ray3D ray) {
        Vector3D normal = getNormal();
        double dotNormDir = Vector3D.dot(normal, ray.direction());
        if(Math.abs(dotNormDir) < RANGE) return null;

        return ray.get((Vector3D.dot(normal, location()) - Vector3D.dot(normal, ray.root())) / dotNormDir);
    }


    private Vector3D getNormal() {
        return Vector3D.cross(
            Vector3D.between(vertexes.get(0), vertexes.get(1)),
            Vector3D.between(vertexes.get(0), vertexes.get(2))
        );
    }



    @Override
    public boolean intersects(Ray3D ray) {
        return getIntersectionLoc(ray) != null;
    }
}
