package aa;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;
import physics.Body;
import physics.Particle;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveBoidApp implements IProcessing {
    private List<Boid> boids;
    private List<Particle> explosionParticles;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private float explosionTimer;
    private boolean explosionActive;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        boids = new ArrayList<Boid>();
        explosionParticles = new ArrayList<Particle>();

        for (int i = 0; i < 30; i++) {
            float x = p.random((float)window[0], (float)window[1]);
            float y = p.random((float)window[2], (float)window[3]);
            Boid b = new Boid(new PVector(x, y), 0.1f, 0.15f, p.color(0, 150, 255), p, plt);
            b.addBehaviour(new Wander(1f));
            boids.add(b);
        }

        explosionTimer = 0;
        explosionActive = false;
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        explosionTimer += dt;

        if (explosionTimer > 3f && !explosionActive) {
            triggerRandomExplosion(p);
            explosionTimer = 0;
        }

        for (int i = boids.size() - 1; i >= 0; i--) {
            Boid b = boids.get(i);
            b.applyBehaviours(dt);
            b.display(p, plt);
        }

        for (int i = explosionParticles.size() - 1; i >= 0; i--) {
            Particle part = explosionParticles.get(i);
            part.move(dt);
            part.display(p, plt);

            if (part.getRadius() < 0.05f || part.getVel().mag() < 0.1f) {
                explosionParticles.remove(i);
            }
        }

        if (explosionParticles.isEmpty()) {
            explosionActive = false;
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        if (plt.isInside(p.mouseX, p.mouseY)) {
            double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
            PVector mousePos = new PVector((float)ww[0], (float)ww[1]);

            Boid closestBoid = null;
            float minDist = Float.MAX_VALUE;

            for (Boid b : boids) {
                float dist = PVector.dist(b.getPos(), mousePos);
                if (dist < minDist && dist < b.getRadius() * 2) {
                    minDist = dist;
                    closestBoid = b;
                }
            }

            if (closestBoid != null) {
                createExplosion(closestBoid, p);
                boids.remove(closestBoid);
            }
        }
    }

    @Override
    public void keyReleased(PApplet parent) {

    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == ' ') {
            triggerRandomExplosion(p);
        }
        if (p.key == 'r' || p.key == 'R') {
            resetSimulation(p);
        }
    }

    private void triggerRandomExplosion(PApplet p) {
        if (!boids.isEmpty()) {
            int randomIndex = (int)(Math.random() * boids.size());
            Boid boidToExplode = boids.get(randomIndex);
            createExplosion(boidToExplode, p);
            boids.remove(randomIndex);
            explosionActive = true;
        }
    }

    private void createExplosion(Boid boid, PApplet p) {
        PVector pos = boid.getPos().copy();
        int baseColor = boid.getBoidColor();

        for (int i = 0; i < 20; i++) {
            float angle = p.random(0, PApplet.TWO_PI);
            float speed = p.random(2f, 8f);
            PVector vel = new PVector(PApplet.cos(angle) * speed, PApplet.sin(angle) * speed);
            float radius = p.random(0.08f, 0.15f);
            float lifespan = p.random(1f, 3f);

            int particleColor = p.color(
                    p.red(baseColor) + p.random(-50, 50),
                    p.green(baseColor) + p.random(-50, 50),
                    p.blue(baseColor) + p.random(-50, 50)
            );

            Particle part = new Particle(pos.copy(), vel, radius, particleColor, lifespan);
            explosionParticles.add(part);
        }
    }

    private void resetSimulation(PApplet p) {
        boids.clear();
        explosionParticles.clear();

        for (int i = 0; i < 30; i++) {
            float x = p.random((float)window[0], (float)window[1]);
            float y = p.random((float)window[2], (float)window[3]);
            Boid b = new Boid(new PVector(x, y), 0.1f, 0.15f, p.color(0, 150, 255), p, plt);
            b.addBehaviour(new Wander(1f));
            boids.add(b);
        }

        explosionTimer = 0;
        explosionActive = false;
    }
}