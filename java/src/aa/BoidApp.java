package aa;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;

public class BoidApp implements IProcessing{
    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        b = new Boid(new PVector(), new PVector(), 1, 0.5f, p.color(0), p, plt);
        target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);

        Seek seek = new Seek(1);
        b.addBehaviour(seek);
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        b.applyBehaviours(dt);

        b.display(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));
    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
