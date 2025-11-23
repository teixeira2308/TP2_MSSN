package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;

import static java.awt.SystemColor.window;


public class Comet {
    private PVector position;
    private PVector velocity;
    private ParticleSystem tailParticles;
    private float size;
    private int lifespan;
    private int maxLifespan;
    private Body sun;

    public Comet(PApplet p, PVector pos, PVector vel, float cometSize, Body sun) {
        position = pos.copy();
        velocity = vel.copy();
        size = cometSize;
        maxLifespan = 500;
        this.sun = sun;
        lifespan = maxLifespan;

        initializeParticleSystem(p);
        System.out.println("Comet created at: " + position + " with velocity: " + velocity);
    }

    private void initializeParticleSystem(PApplet p) {
        float[] velControl = {
                PApplet.PI,
                PApplet.PI,
                20f,
                60f,
        };
        float[] lifetime = {0.8f, 2.0f};
        float[] radius = {2f, 4f};
        float flow = 150f;
        int color = p.color(180, 220, 255);

        PSControl psc = new PSControl(velControl, lifetime, radius, flow, color);
        tailParticles = new ParticleSystem(position, velocity, 1f, 1f, psc);
    }

    public void update(float dt) {
        float speedUp = 60 * 60 * 24 * 30;
        position.add(PVector.mult(velocity, dt * speedUp));

        if (tailParticles != null) {
            tailParticles.setPos(position);
            tailParticles.setVel(velocity);
            tailParticles.move(dt);
        }
        lifespan--;
    }

    public void display(PApplet p, SubPlot plt) {
        tailParticles.display(p, plt);
        p.pushStyle();
        float[] headPixel = plt.getPixelCoord(position.x, position.y);

        p.fill(255, 0, 0);
        p.circle(headPixel[0], headPixel[1], 5);

        p.noStroke();
        p.fill(255, 255, 255);
        p.circle(headPixel[0], headPixel[1], 8);

        p.fill(180, 220, 255, 100);
        p.circle(headPixel[0], headPixel[1], 12);

        p.popStyle();

        if (lifespan % 200 == 0) {
            System.out.println("Comet displayed at screen: " + headPixel[0] + ", " + headPixel[1]);
        }
    }

    public boolean isDead(double[] window) {
        return lifespan <= 0 ||
                position.x < window[0] || position.x > window[1] ||
                position.y < window[2] || position.y > window[3];
    }

    public PVector getPosition() {
        return position;
    }

}
