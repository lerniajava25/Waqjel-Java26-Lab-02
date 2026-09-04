package org.example;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final List<Shape> shapes = new ArrayList<>();
    private final Vector3D lightDirection = new Vector3D(0.2, 1.0, 0.2).normalize();

    public void add(Shape shape) { shapes.add(shape); }

    public int renderPixel(int x, int y, int width, int height) {
        double aspect = (double) width / height;
        double px = (2.0 * (x + 0.5) / width - 1.0) * aspect;
        double py = 1.0 - 2.0 * (y + 0.5) / height;

        Ray ray = new Ray(new Vector3D(0, 0, 0), new Vector3D(px, py, -1));
        HitRecord closestHit = null;

        for (Shape shape : shapes) {
            HitRecord hit = shape.hit(ray);
            if (hit != null) {
                if (closestHit == null || hit.t < closestHit.t) {
                    closestHit = hit;
                }
            }
        }

        if (closestHit != null) {

            Vector3D shadowRayOrigin = closestHit.p.add(closestHit.normal.multiply(0.001));
            Ray shadowRay = new Ray(shadowRayOrigin, lightDirection);
            boolean inShadow = false;

            for (Shape shape : shapes) {
                HitRecord shadowHit = shape.hit(shadowRay);
                if (shadowHit != null && shadowHit.t > 0.001) {
                    inShadow = true;
                    break;
                }
            }


            Color scatteredColor = closestHit.material.scatter(closestHit, lightDirection);
            double ambientStrength = 0.20; // 20% baseline light prevents absolute pitch-black underbellies

            Color finalColor;
            if (closestHit.material instanceof SolidColor) {
                finalColor = scatteredColor; // Solid color elements remain full-bright
            } else {
                // For Lambertian materials: Extract unshaded color by simulating light straight-on
                Color unshadedBaseColor = closestHit.material.scatter(
                        new HitRecord(closestHit.t, closestHit.p, lightDirection, closestHit.material),
                        lightDirection
                );

                Color ambientContribution = unshadedBaseColor.multiply(ambientStrength);

                if (inShadow) {
                    finalColor = ambientContribution;
                } else {
                    finalColor = scatteredColor.add(ambientContribution);
                }
            }

            return finalColor.toRGB();
        }

        // Sky gradient background
        double t = 0.5 * (ray.direction.y + 1.0);
        Color skyColor = new Color(1.0, 1.0, 1.0).multiply(1.0 - t).add(new Color(0.5, 0.7, 1.0).multiply(t));
        return skyColor.toRGB();
    }
}
