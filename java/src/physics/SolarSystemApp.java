package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;

public class SolarSystemApp implements IProcessing {

    private float sunMass = 1.989e30f;
    private float earthMass = 5.97e24f;
    private float distEarthSun = 1.496e11f;
    private float earthSpeed = 3e4f;
    private float mercuryMass = 3.30e23f;
    private float distMercurySun = 5.80e10f;
    private float mercurySpeed = 4.8e4f;
    private float venusMass = 4.9e24f;
    private float distVenusSun = 1.1e11f;
    private float venusSpeed = 3.5e4f;
    private float marsMass = 6.39e23f;
    private float distMarsSun = 2.28e11f;
    private float marsSpeed = 2.41e4f;
    private float jupiterMass = 1.90e27f;
    private float distJupiterSun = 7.79e11f;
    private float jupiterSpeed = 1.3e4f;
    private float saturnMass = 5.68e26f;
    private float distSaturnSun = 1.4e12f;
    private float saturnSpeed = 9640;
    private float uranusMass = 8.68e25f;
    private float distUranusSun = 2.87e12f;
    private float uranusSpeed = 6810;
    private float neptuneMass = 1.02e26f;
    private float distNeptuneSun = 4.50e12f;
    private float neptuneSpeed = 5400;

    private float[] viewport = {0.0f,0.0f,1.0f,0.9f};
    private double[] window = {-distNeptuneSun, distNeptuneSun, -distNeptuneSun, distNeptuneSun};

    private float[] planetOrbits = {distMercurySun, distVenusSun, distEarthSun, distMarsSun, distJupiterSun, distSaturnSun, distUranusSun, distNeptuneSun};

    private String[] planetNames = {"Mercúrio", "Vénus", "Terra", "Marte", "Jupiter", "Saturno", "Urano", "Neptuno"};

    private int currentPlanetIndex = 7;
    private SubPlot plt;
    private Body sun, earth, mercury, venus, mars, jupiter, saturn, uranus, neptune;

    private float speedUp = 60 * 60 * 24 * 30;


    
    @Override
    public void setup(PApplet p) {


        plt = new SubPlot(window, viewport, p.width, p.height);
        sun = new Body(new PVector(), new PVector(), sunMass, distEarthSun / 10, p.color(255, 128, 0));
        earth = new Body(new PVector(0, distEarthSun), new PVector(earthSpeed, 0), earthMass, distEarthSun/20, p.color(107, 147, 214));
        mercury = new Body(new PVector(0, distMercurySun), new PVector(mercurySpeed, 0), mercuryMass, distMercurySun/20, p.color(115, 115, 115));
        venus = new Body(new PVector(0, distVenusSun), new PVector(venusSpeed, 0), venusMass, distVenusSun/20, p.color(230, 230, 230));
        mars = new Body(new PVector(0, distMarsSun), new PVector(marsSpeed, 0), marsMass,  distMarsSun/20, p.color(153, 61, 0));
        jupiter = new Body(new PVector(0, distJupiterSun), new PVector(jupiterSpeed, 0), jupiterMass, distJupiterSun/20, p.color(176, 127, 53));
        saturn = new Body(new PVector(0, distSaturnSun), new PVector(saturnSpeed, 0), saturnMass, distSaturnSun/20, p.color(176, 143, 54));
        uranus = new Body(new PVector(0, distUranusSun), new PVector(uranusSpeed, 0), uranusMass, distUranusSun/20, p.color(85, 128, 170));
        neptune = new Body(new PVector(0, distNeptuneSun), new PVector(neptuneSpeed, 0), neptuneMass, distNeptuneSun/20, p.color(54, 104, 150));
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);

        p.fill(255);
        p.text("Visualização do planeta: " + planetNames[currentPlanetIndex] + " Órbita", 10, 20);
        p.text("Pressiona '+' para o próximo planeta, '-' para o planeta anterior", 10, 40);
        p.text("Pressiona '0' para uma visualização do planeta solar completo", 10, 60);

        sun.display(p, plt);

        PVector f = sun.attraction(earth);
        PVector f2 = sun.attraction(mercury);
        PVector f3 = sun.attraction(venus);
        PVector f4 = sun.attraction(mars);
        PVector f5 = sun.attraction(jupiter);
        PVector f6 = sun.attraction(saturn);
        PVector f7 = sun.attraction(uranus);
        PVector f8 = sun.attraction(neptune);
        earth.applyForce(f);
        mercury.applyForce(f2);
        venus.applyForce(f3);
        mars.applyForce(f4);
        jupiter.applyForce(f5);
        saturn.applyForce(f6);
        uranus.applyForce(f7);
        neptune.applyForce(f8);

        earth.move(dt * speedUp);
        mercury.move(dt * speedUp);
        venus.move(dt * speedUp);
        mars.move(dt * speedUp);
        jupiter.move(dt * speedUp);
        saturn.move(dt * speedUp);
        uranus.move(dt * speedUp);
        neptune.move(dt * speedUp);


        earth.display(p, plt);
        mercury.display(p, plt);
        venus.display(p, plt);
        mars.display(p, plt);
        jupiter.display(p, plt);
        saturn.display(p, plt);
        uranus.display(p, plt);
        neptune.display(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == '+') {
            zoomIn(p);
        } else if (p.key == '-') {
            zoomOut(p);
        } else if (p.key == '0') {
            resetZoom(p);
        }
    }

    private void zoomIn(PApplet p) {
        if (currentPlanetIndex > 0) {
            currentPlanetIndex--;
            zoomToPlanetOrbit(p, currentPlanetIndex);
        }
    }

    private void zoomOut(PApplet p) {
        if (currentPlanetIndex < planetNames.length - 1) {
            currentPlanetIndex++;
            zoomToPlanetOrbit(p, currentPlanetIndex);
        }
    }

    private void resetZoom(PApplet p) {
        currentPlanetIndex = planetOrbits.length - 1;
        zoomToPlanetOrbit(p, currentPlanetIndex);
    }

    private void zoomToPlanetOrbit(PApplet p, int planetIndex) {
        float orbit = planetOrbits[planetIndex];

        double range = orbit * 1.1f;

        window[0] = -range;
        window[1] = range;
        window[2] = -range;
        window[3] = range;

        plt = new SubPlot(window, viewport, p.width, p.height);
    }
}
