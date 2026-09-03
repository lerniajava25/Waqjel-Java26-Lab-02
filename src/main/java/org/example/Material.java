package org.example;

// handles lighting interaction properties
public interface Material {
    Color scatter(HitRecord hit, Vector3D lightDirection);
}

class SolidColor implements Material {
    private final Color baseColor;

    public SolidColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    @Override
    public Color scatter(HitRecord hit, Vector3D lightDirection) {
        return baseColor; // Ignores light interaction mathematics entirely
    }
}

// diffuse surface utilizing proper Lambert's Cosine Law shading
class Lambertian implements Material {
    private final Color albedo;

    public Lambertian(Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public Color scatter(HitRecord hit, Vector3D lightDirection) {
        // Calculate the dot product between the surface normal vector and the incoming light path
        double cosTheta = Math.max(0.0, hit.normal.dot(lightDirection));

        // Scale the material's inherent color reflectively by the light falloff intensity
        return albedo.multiply(cosTheta);
    }
}
