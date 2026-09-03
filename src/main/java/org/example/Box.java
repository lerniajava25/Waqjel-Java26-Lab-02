package org.example;

public class Box implements Shape {
    private final Vector3D minCorner;
    private final Vector3D maxCorner;
    private final Material material;

    public Box(Vector3D minCorner, Vector3D maxCorner, Material material) {

        this.minCorner = new Vector3D(
                Math.min(minCorner.x, maxCorner.x),
                Math.min(minCorner.y, maxCorner.y),
                Math.min(minCorner.z, maxCorner.z)
        );
        this.maxCorner = new Vector3D(
                Math.max(minCorner.x, maxCorner.x),
                Math.max(minCorner.y, maxCorner.y),
                Math.max(minCorner.z, maxCorner.z)
        );
        this.material = material;
    }

    @Override
    public HitRecord hit(Ray ray) {
        double tMin = 0.001;
        double tMax = Double.MAX_VALUE;

        // Track normal orientation components
        double hitNormalX = 0, hitNormalY = 0, hitNormalZ = 0;

        // X-Axis check
        if (Math.abs(ray.direction.x) > 1e-6) {
            double t0 = (minCorner.x - ray.origin.x) / ray.direction.x;
            double t1 = (maxCorner.x - ray.origin.x) / ray.direction.x;
            if (t0 > t1) { double temp = t0; t0 = t1; t1 = temp; }
            if (t0 > tMin) { tMin = t0; hitNormalX = ray.direction.x > 0 ? -1 : 1; hitNormalY = 0; hitNormalZ = 0; }
            if (t1 < tMax) tMax = t1;
            if (tMin > tMax) return null;
        } else if (ray.origin.x < minCorner.x || ray.origin.x > maxCorner.x) {
            return null;
        }

        // Y-Axis check
        if (Math.abs(ray.direction.y) > 1e-6) {
            double t0 = (minCorner.y - ray.origin.y) / ray.direction.y;
            double t1 = (maxCorner.y - ray.origin.y) / ray.direction.y;
            if (t0 > t1) { double temp = t0; t0 = t1; t1 = temp; }
            if (t0 > tMin) { tMin = t0; hitNormalX = 0; hitNormalY = ray.direction.y > 0 ? -1 : 1; hitNormalZ = 0; }
            if (t1 < tMax) tMax = t1;
            if (tMin > tMax) return null;
        } else if (ray.origin.y < minCorner.y || ray.origin.y > maxCorner.y) {
            return null;
        }

        // Z-Axis check
        if (Math.abs(ray.direction.z) > 1e-6) {
            double t0 = (minCorner.z - ray.origin.z) / ray.direction.z;
            double t1 = (maxCorner.z - ray.origin.z) / ray.direction.z;
            if (t0 > t1) { double temp = t0; t0 = t1; t1 = temp; }
            if (t0 > tMin) { tMin = t0; hitNormalX = 0; hitNormalY = 0; hitNormalZ = ray.direction.z > 0 ? -1 : 1; }
            if (t1 < tMax) tMax = t1;
            if (tMin > tMax) return null;
        } else if (ray.origin.z < minCorner.z || ray.origin.z > maxCorner.z) {
            return null;
        }

        // Return a valid HitRecord containing calculated normal orientation directions
        Vector3D p = ray.pointAt(tMin);
        Vector3D normal = new Vector3D(hitNormalX, hitNormalY, hitNormalZ);
        return new HitRecord(tMin, p, normal, this.material);
    }
}
