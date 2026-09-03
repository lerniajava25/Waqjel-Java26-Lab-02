package org.example;

public class Sphere implements Shape {
    private final Vector3D center;
    private final double radius;
    private final Material material;

    public Sphere(Vector3D center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public HitRecord hit(Ray ray) {
        Vector3D oc = ray.origin.sub(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2.0 * oc.dot(ray.direction);
        double c = oc.dot(oc) - radius * radius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return null;

        double t = (-b - Math.sqrt(discriminant)) / (2.0 * a);
        if (t < 0.001) t = (-b + Math.sqrt(discriminant)) / (2.0 * a);
        if (t < 0.001) return null;

        Vector3D p = ray.pointAt(t);
        Vector3D normal = p.sub(center).normalize();
        return new HitRecord(t, p, normal, this.material);
    }
}
