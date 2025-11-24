package aa;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;
import physics.Body;

import java.util.ArrayList;
import java.util.List;

public class PredatorPreyApp implements IProcessing {
    private Flock flockPrey;
    private Boid predator;
    private float[] sacWeights = {1f, 0.5f, 0.5f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body targetPrey;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);

        flockPrey = new Flock(50, 0.1f, 0.2f, p.color(0, 100, 200), sacWeights, p, plt);

        predator = new Boid(new PVector(0, 0), 0.3f, 0.4f, p.color(255, 0, 0), p, plt);
        predator.addBehaviour(new Pursuit(1f));

        List<Body> allTrackingBodies = boidList2BodyList(flockPrey);
        targetPrey = allTrackingBodies.get(0);
        Eye eye = new Eye(predator, allTrackingBodies);
        predator.setEye(eye);
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        updateTargetPrey();

        flockPrey.applyBehaviour(dt);
        predator.applyBehaviours(dt);

        flockPrey.display(p, plt);
        predator.display(p, plt);

        displayTargetPrey(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {
        if (p.mouseButton == PApplet.LEFT) {
            double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
            predator.setPos(new PVector((float) ww[0], (float) ww[1]));
        }
    }

    @Override
    public void keyReleased(PApplet parent) {

    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == 'r' || p.key == 'R') {
            resetSimulation(p);
        }
        if (p.key == 't' || p.key == 'T') {
            changeTargetPrey();
        }
    }

    private List<Body> boidList2BodyList(Flock flock) {
        List<Body> bodies = new ArrayList<Body>();
        for (int i = 0; i < 50; i++) {
            bodies.add(flock.getBoid(i));
        }
        return bodies;
    }

    private void updateTargetPrey() {
        // logic to change target periodically
    }

    private void changeTargetPrey() {
        List<Body> allTrackingBodies = boidList2BodyList(flockPrey);
        int randomIndex = (int) (Math.random() * allTrackingBodies.size());
        targetPrey = allTrackingBodies.get(randomIndex);

        List<Body> newTargetList = new ArrayList<Body>();
        newTargetList.add(targetPrey);
        predator.getEye().setAllTrackingBodies(newTargetList);
        predator.getEye().target = targetPrey;
    }

    private void displayTargetPrey(PApplet p, SubPlot plt) {
        p.pushStyle();
        p.fill(255, 255, 0, 100);
        p.stroke(255, 255, 0);
        p.strokeWeight(2);

        float[] pp = plt.getPixelCoord(targetPrey.getPos().x, targetPrey.getPos().y);
        float[] dims = plt.getDimInPixel(0.3f, 0.3f);
        float r = dims[0];

        p.ellipse(pp[0], pp[1], 2 * r, 2 * r);
        p.popStyle();
    }

    private void resetSimulation(PApplet p) {
        double[] w = plt.getWindow();
        float x = p.random((float) w[0], (float) w[1]);
        float y = p.random((float) w[2], (float) w[3]);
        predator.setPos(new PVector(x, y));

        changeTargetPrey();
    }
}