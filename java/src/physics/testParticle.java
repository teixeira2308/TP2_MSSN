package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;

public class testParticle implements IProcessing{

    private Particle particle;
    private double[] window = {-5, 5, -5, 5};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        PVector pos = new PVector(0,0);
        PVector vel = new PVector(0,0);
        float radius = 1;
        int color = parent.color(255, 0, 0);
        float lifespan = 10;
        particle = new Particle(pos, vel, radius, color, lifespan);
        particle.display(parent, plt);
    }

    @Override 
    public void draw(PApplet parent, float dt) {
        parent.background(200);
        particle.move(dt);
        particle.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {

    }
}
