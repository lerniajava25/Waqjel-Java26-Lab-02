package org.example;

public class Plane implements Shape {
    private final Vector3D point;
    private final Vector3D normal;
    private final Material material;

    public Plane(Vector3D point, Vector3D normal, Material material) {
        this.point = point;
        this.normal = normal.normalize();
        this.material = material;
    }

    @Override
    public HitRecord hit(Ray ray) {
        double denominator = ray.direction.dot(normal);
        if (Math.abs(denominator) < 1e-6) return null;

        double t = point.sub(ray.origin).dot(normal) / denominator;
        if (t < 0.001) return null;

        return new HitRecord(t, ray.pointAt(t), normal, this.material);
    }
}
