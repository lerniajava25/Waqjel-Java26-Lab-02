package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;


    void main() {
        BufferedImage renderImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Scene scene = new Scene();

        scene.add(new Sphere(
                new Vector3D(-1.2, 0, -4),
                1.0,
                new Lambertian(new Color(0.9, 0.2, 0.2))
        ));



        scene.add(new Triangle(
                new Vector3D(-2.0, -1.0, -5.0),
                new Vector3D(2.0, -1.0, -5.0),
                new Vector3D(0.0, 2.0, -6.0),
                new Lambertian(new Color(0.2, 0.8, 0.2))
        ));

        scene.add(new Plane(
                new Vector3D(0, -1.0, 0),
                new Vector3D(0, 1, 0),
                new Lambertian(new Color(0.4, 0.4, 0.4))
        ));

        scene.add(new Box(
                new Vector3D(1.8, -0.2, -5.0),
                new Vector3D(2.6,  0.6, -5.8),
                new Lambertian(new Color(0.9, 0.9, 0.1))
        ));

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                renderImage.setRGB(x, y, scene.renderPixel(x, y, WIDTH, HEIGHT));
            }
        }

        try {
            File outputFile = new File("raytrace_output.png");
            ImageIO.write(renderImage, "png", outputFile);
            IO.println("LYCKADES: Fil genererad och skriven till:" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("ALLVARLIGT FEL: Misslyckades med att skriva bilden till disklagring: " + e.getMessage());
        }
    }
}
