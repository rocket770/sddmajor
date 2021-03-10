package rccookie.rendering;

import rccookie.geometry.Rotation;
import rccookie.geometry.Transform3D;
import rccookie.geometry.Vector3D;

public abstract class Body implements GameObject {
    private Transform3D transform;
    private RenderObject renderObject;

    public Body() {
        transform = new Transform3D();
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


    @Override
    public RenderObject renderObject() {
        return renderObject;
    }

    @Override
    public void setRenderObject(RenderObject renderObject) {
        this.renderObject = renderObject;
    }


    public void earlyUpdate(double deltaTime) {}
    public void update(double deltaTime) {}
    public void lateUpdate(double deltaTime) {}
}
