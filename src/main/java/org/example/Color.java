package org.example;

public class Color {
    public final double r, g, b;

    public Color(double r, double g, double b) {
        this.r = Math.min(1.0, Math.max(0.0, r));
        this.g = Math.min(1.0, Math.max(0.0, g));
        this.b = Math.min(1.0, Math.max(0.0, b));
    }

    public Color multiply(double scalar) { return new Color(r * scalar, g * scalar, b * scalar); }
    public Color add(Color c) { return new Color(r + c.r, g + c.g, b + c.b); }

    public int toRGB() {
        int ir = (int) (255.99 * r);
        int ig = (int) (255.99 * g);
        int ib = (int) (255.99 * b);
        return (ir << 16) | (ig << 8) | ib;
    }
}
