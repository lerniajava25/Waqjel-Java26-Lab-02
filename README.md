# 3D Raytracer i Java

Detta är en renodlad 3D-renderingsmotor (raytracer) skriven från grunden i modern Java (Java 26). Istället for att använda externa grafikbibliotek eller tunga spelmotorer bygger det här projektet på ren matematik. Programmet beräknar hur individuella ljusstrålar rör sig genom en virtuell rymd, krockar med objekt, hanterar material och skapar realistiska skuggor.

När man kör programmet beräknas bilden rad för rad i bakgrunden och sparas som en högupplöst PNG-bild direkt på dator. 

---

## Projektet Arkitektur

Koden är uppdelad i 11 separata filer för att vara lätt att förstå, underhålla och bygga ut:

*   **`Vector3D.java`**: Hanterar positioner och riktningar i rymden (x, y, z) samt viktig vektor-matematik som skalär- och kryssprodukter.
*   **`Color.java`**: Hanterar färger (rött, grönt, blått) och ser till att de inte blir överexponerade innan de sparas till en pixel.
*   **`Ray.java`**: Representerar en ljusstråle med en startpunkt och en färdriktning.
*   **`HitRecord.java`**: En databehållare som sparar information om en krock (avstånd, träffpunkt, ytans riktning och material).
*   **`Shape.java`**: Ett gemensamt gränssnitt (interface) som alla 3D-former måste implementera.
*   **`Sphere.java`, `Triangle.java`, `Plane.java`, `Box.java`**: De konkreta 3D-formerna som finns i vår värld.
*   **`Material.java`**: Bestämmer hur ytan på en form reagerar på ljus (innehåller `Lambertian` för matt färg och `SolidColor` för konstant ljus).
*   **`Scene.java`**: Samlar alla former i en lista och räknar ut hur ljus, skuggor och omgivningsljus (ambient light) ska blandas.
*   **`Main.java`**: Startpunkten för programmet. Den bygger världen, startar renderingen och sparar den färdiga bilden.

---

## Hur kan man lägga till en ny Shape

Renderingsmotorn är helt modulär och använder polymorfi via gränssnittet `Shape`. Det betyder att du kan lägga till helt nya tredimensionella former i scenen **utan att ändra någonting** i motorns kärna (`Scene.java`) eller i ljusberäkningarna.

Följ dessa två enkla steg för att skapa och använda en egen form:

### Steg 1: Skapa klassen och implementera `Shape`
Skapa en ny Java-fil (till exempel `Cone.java`) i mappen `org.example`. Låt klassen implementera gränssnittet `Shape` och överskrid metoden `hit(Ray ray)`.

Metodens uppgift är att räkna ut om ljusstrålen krockar med din form. Om strålen missar returnerar du `null`. Om den träffar returnerar du ett `HitRecord` med krockavståndet (t), träffpunkten, ytans normalvektor och formens material:

```java
package org.example;

public class Cone implements Shape {
    private final Vector3D tip;
    private final double height;
    private final Material material;

    public Cone(Vector3D tip, double height, Material material) {
        this.tip = tip;
        this.height = height;
        this.material = material;
    }

    @Override
    public HitRecord hit(Ray ray) {
        //Räkna ut krockavståndet (t) med geometriska formel
        double t = -1.0; // Implementera din matematiska formel här
        
        if (t < 0.001) return null; // Miss eller krock bakom kameran

        // Beräkna exakt träffpunkt och ytans riktning (normal)
        Vector3D p = ray.pointAt(t);
        Vector3D normal = new Vector3D(0, 1, 0); // Beräkna normalen här

        // Skicka tillbaka datan packad i ett HitRecord
        return new HitRecord(t, p, normal, this.material);
    }
}
```

### Steg 2: Registrera din form i `Main.java`
Öppna `Main.java` och leta upp stället där scenen byggs inuti `main()`-metoden. Lägg till din nya form med en enda rad kod genom att skicka med dess position och ett material (till exempel ett matt `Lambertian`-material):

```java
// Inuti Main.java
Scene scene = new Scene();

// Lägg till din nya form i listan
scene.add(new Cone(
    new Vector3D(0.0, 1.5, -4.5), // Position i rymden
    2.0,                          // Höjd på konen
    new Lambertian(new Color(0.8, 0.1, 0.8)) // Lila matt färg
));
```

När man startar programmet nästa gång kommer motorn automatiskt att rita ut den nya form. Den hanteras korrekt i djupled, får mjuk ljussättning på undersidan och kastar realistiska skuggor på marken helt automatiskt.

---

