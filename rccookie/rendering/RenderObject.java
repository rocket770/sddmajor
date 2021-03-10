package rccookie.rendering;

import java.util.ArrayList;
import java.util.List;

import rccookie.geometry.Vector3D;

public class RenderObject {
    public final List<RenderPanel> panels = new ArrayList<>();


    public RenderObject() {}
    public RenderObject(RenderPanel panel) {
        if(panel != null) panels.add(panel);
    }
    public RenderObject(List<RenderPanel> panels) {
        if(panels != null) this.panels.addAll(panels);
    }






    public static RenderObject cube(double edgeLength) {
        double d = edgeLength * 0.5;

        List<RenderPanel> panels = new ArrayList<>();

        Vector3D[] vertexes = {
            new Vector3D(-d, -d, -d),
            new Vector3D( d, -d, -d),
            new Vector3D( d,  d, -d),
            new Vector3D(-d,  d, -d),
            new Vector3D(-d, -d,  d),
            new Vector3D( d, -d,  d),
            new Vector3D( d,  d,  d),
            new Vector3D(-d,  d,  d)
        };

        List<Vector3D> panelVtxs = new ArrayList<>();
        panelVtxs.add(vertexes[0]);
        panelVtxs.add(vertexes[3]);
        panelVtxs.add(vertexes[7]);
        panelVtxs.add(vertexes[4]);
        panels.add(new RenderPolygon(panelVtxs));

        panelVtxs.clear();
        panelVtxs.add(vertexes[0]);
        panelVtxs.add(vertexes[4]);
        panelVtxs.add(vertexes[5]);
        panelVtxs.add(vertexes[1]);
        panels.add(new RenderPolygon(panelVtxs));

        panelVtxs.clear();
        panelVtxs.add(vertexes[1]);
        panelVtxs.add(vertexes[2]);
        panelVtxs.add(vertexes[6]);
        panelVtxs.add(vertexes[5]);
        panels.add(new RenderPolygon(panelVtxs));

        panelVtxs.clear();
        panelVtxs.add(vertexes[4]);
        panelVtxs.add(vertexes[6]);
        panelVtxs.add(vertexes[7]);
        panelVtxs.add(vertexes[3]);
        panels.add(new RenderPolygon(panelVtxs));

        panelVtxs.clear();
        panelVtxs.add(vertexes[0]);
        panelVtxs.add(vertexes[1]);
        panelVtxs.add(vertexes[2]);
        panelVtxs.add(vertexes[3]);
        panels.add(new RenderPolygon(panelVtxs));

        panelVtxs.clear();
        panelVtxs.add(vertexes[7]);
        panelVtxs.add(vertexes[6]);
        panelVtxs.add(vertexes[5]);
        panelVtxs.add(vertexes[4]);
        panels.add(new RenderPolygon(panelVtxs));

        return new RenderObject(panels);
    }
}
