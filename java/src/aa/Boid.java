package aa;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

public class Boid extends Body {
    private PShape shape;
    private SubPlot plt;
    protected DNA dna;
    protected Eye eye;
    private List<Behaviour> behaviours;

    protected Boid(PVector pos, PVector vel, float mass, float radius, int color, PApplet p, SubPlot plt) {
        super(pos, new PVector(), mass, radius, color);
        dna = new DNA();
        behaviours = new ArrayList<Behaviour>();
        this.plt = plt;
        setShape(p, plt);
    }

    public void setEye(Eye eye) {
        this.eye = eye;
    }

    public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getDimInPixel(radius, radius);
        shape = p.createShape();
        shape.beginShape();
        shape.noStroke();
        shape.fill(color);
        shape.vertex(-rr[0], rr[0]/2);
        shape.vertex(rr[0], 0);
        shape.vertex(-rr[0], -rr[0]/2);
        shape.vertex(-rr[0]/2, 0);
        shape.endShape(PConstants.CLOSE);
    }

    public void addBehaviour(Behaviour behaviour) {
        behaviours.add(behaviour);
    }

    public void removeBehaviour(Behaviour behaviour) {
        if(behaviours.contains(behaviour)) behaviours.remove(behaviour);
    }

    public void applyBehaviours(float dt) {
        eye.look();

        PVector vd = new PVector();
        for (Behaviour behaviour : behaviours) {
            PVector vdd = behaviour.getDesiredVelocity(this);
            vdd.mult(behaviour.getWeight());
            vd.add(vdd);
        }
        move(dt, vd);
    }

    private void move(float dt, PVector vd) {
        vd.normalize().mult(dna.maxSpeed);
        PVector fs = PVector.sub(vd, vel);
        applyForce(fs.limit(dna.maxForce));
        super.move(dt);
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());
        p.shape(shape);
    }
}
