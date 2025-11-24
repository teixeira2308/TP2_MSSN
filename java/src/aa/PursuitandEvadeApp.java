package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PursuitandEvadeApp implements IProcessing {

    private Boid pursuer, evader;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private List<Body> pursuerTrackingBodies;
    private List<Body> evaderTrackingBodies;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);

        pursuerTrackingBodies = new ArrayList<>();
        evaderTrackingBodies = new ArrayList<>();

        pursuer = new Boid(new PVector(p.random((float)window[0], (float)window[1]), p.random((float)window[2], (float)window[3])), .1f, 0.5f, p.color(255, 0, 0), p, plt);
        pursuer.addBehaviour(new Pursuit(1f));

        evader = new Boid(new PVector(p.random((float)window[0], (float)window[1]), p.random((float)window[2], (float)window[3])), 0.3f, 0.5f, p.color(0, 255, 0), p, plt);
        evader.addBehaviour(new Evade(1f));

        pursuerTrackingBodies.add(evader);
        evaderTrackingBodies.add(pursuer);

        Eye pursuerEye = new Eye(pursuer, pursuerTrackingBodies);
        Eye evaderEye = new Eye(evader, evaderTrackingBodies);

        pursuer.setEye(pursuerEye);
        evader.setEye(evaderEye);

    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        pursuer.applyBehaviours(dt);
        evader.applyBehaviours(dt);

        pursuer.display(p, plt);
        evader.display(p, plt);
    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mousePressed(PApplet p) {
    }

    @Override
    public void keyReleased(PApplet parent) {

    }
}
