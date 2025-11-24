package aa;

import processing.core.PVector;

public class Brake extends Behaviour {
    private float brakeStrength;

    public Brake(float weight) {
        super(weight);
        this.brakeStrength = 1.0f;
    }

    public Brake(float weight, float brakeStrength) {
        super(weight);
        this.brakeStrength = brakeStrength;
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector currentVel = me.getVel();

        if (currentVel.mag() < 0.01f) {
            return new PVector();
        }

        PVector brakeForce = currentVel.copy();
        brakeForce.mult(-1);

        brakeForce.normalize();
        brakeForce.mult(me.dna.maxSpeed * brakeStrength * getWeight());

        return brakeForce;
    }

    public void setBrakeStrength(float brakeStrength) {
        this.brakeStrength = brakeStrength;
    }

    public float getBrakeStrength() {
        return brakeStrength;
    }
}