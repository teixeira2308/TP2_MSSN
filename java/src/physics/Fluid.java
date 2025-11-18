package physics;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Fluid {
    private float density;

    protected Fluid(float density) {
        this.density = density;
    }

    public PVector drag(Body b){
        float area = PApplet.pow(b.getRadius(),2.0f)*PApplet.PI;
        float mag = b.getVel().mag();
        return PVector.mult(b.vel,-0.5f*density*area*mag);
    }

}