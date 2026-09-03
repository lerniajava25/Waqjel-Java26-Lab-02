package org.example;

public class Vector3D {
    public final double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D add(Vector3D v) { return new Vector3D(x + v.x, y + v.y, z + v.z); }
    public Vector3D sub(Vector3D v) { return new Vector3D(x - v.x, y - v.y, z - v.z); }
    public Vector3D multiply(double scalar) { return new Vector3D(x * scalar, y * scalar, z * scalar); }
    public double dot(Vector3D v) { return x * v.x + y * v.y + z * v.z; }

    public Vector3D cross(Vector3D v) {
        return new Vector3D(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    public double length() { return Math.sqrt(dot(this)); }

    public Vector3D normalize() {
        double len = length();
        return len == 0 ? this : multiply(1.0 / len);
    }
}
