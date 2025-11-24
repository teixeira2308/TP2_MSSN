package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Brakeapp implements IProcessing {

    private Boid b;
    private SubPlot plt;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private Body target;
    private List<Body> allTrackingBodies;

    private Brake brake;
    private boolean isBraking = false;
    private boolean isMoving = false;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        b = new Boid(new PVector(), .1f, 0.5f, p.color(255, 0, 0), p, plt);
        b.setVel(new PVector());
        target = new Body(new PVector(5,5), new PVector(), 1f, .3f, p.color(0));

        allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(target);
        b.setEye(new Eye(b, allTrackingBodies));

        Seek seek = new Seek(1.0f);
        brake = new Brake(1.0f);

        b.addBehaviour(seek);

        b.dna.maxSpeed = 0.1f;
        b.dna.maxForce = 0.01f;
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);


        b.applyBehaviours(dt);


        target.display(p, plt);
        b.display(p, plt);
    }

    @Override
    public void keyPressed(PApplet p) {

        if (p.key == 'w') {
            b.dna.maxSpeed += 0.2f;
            b.dna.maxForce += 0.01f;
        }
        if (p.key == 's') {
            b.dna.maxSpeed = Math.max(0.1f, b.dna.maxSpeed - 0.1f);
            b.dna.maxForce += Math.max(0.01f, b.dna.maxForce - 0.02f);
        }

        if (p.key == 'e') b.dna.maxForce += 0.02f;
        if (p.key == 'd') b.dna.maxForce = Math.max(0.001f, b.dna.maxForce - 0.02f);

        if (p.key == 'b') {
            b.addBehaviour(brake);
            isBraking = true;
        }
    }

    @Override
    public void keyReleased(PApplet p) {
        if (p.key == 'b') {
            b.removeBehaviour(brake);
            isBraking = false;
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        if (plt.isInside(p.mouseX, p.mouseY)) {
            double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
            target.setPos(new PVector((float)w[0], (float)w[1]));
        }
    }
}
