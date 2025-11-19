package aa;

import processing.core.PApplet;
import setup.IProcessing;
import tools.SubPlot;

public class FlockTestApp implements IProcessing {
    private Flock flock;
    private float[] sacWeights = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        flock = new Flock(200, .1f, 0.3f, p.color(0), sacWeights, p, plt);
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);
        flock.applyBehaviours(dt);
        flock.display(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {
    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
