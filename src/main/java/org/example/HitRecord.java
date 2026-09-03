package org.example;

public class HitRecord {
    public final double t;
    public final Vector3D p;
    public final Vector3D normal;
    public final Material material;

    public HitRecord(double t, Vector3D p, Vector3D normal, Material material) {
        this.t = t;
        this.p = p;
        this.normal = normal;
        this.material = material;
    }
}
