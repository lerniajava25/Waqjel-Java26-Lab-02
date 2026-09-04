package org.example;

public class Triangle implements Shape {
    private final Vector3D v0, v1, v2;
    private final Material material;
    private final Vector3D normal;

    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2, Material material) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.material = material;
        this.normal = (v1.sub(v0)).cross(v2.sub(v0)).normalize();
    }

    @Override
    public HitRecord hit(Ray ray) {
        Vector3D edge1 = v1.sub(v0);
        Vector3D edge2 = v2.sub(v0);
        Vector3D h = ray.direction.cross(edge2);
        double a = edge1.dot(h);
        if (Math.abs(a) < 1e-6) return null;

        double f = 1.0 / a;
        Vector3D s = ray.origin.sub(v0);
        double u = f * s.dot(h);
        if (u < 0.0 || u > 1.0) return null;

        Vector3D q = s.cross(edge1);
        double v = f * ray.direction.dot(q);
        if (v < 0.0 || u + v > 1.0) return null;

        double t = f * edge2.dot(q);
        if (t < 0.001) return null;

        return new HitRecord(t, ray.pointAt(t), normal, this.material);
    }
}
