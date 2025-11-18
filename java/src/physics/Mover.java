package physics;

import processing.core.PVector;

public abstract class Mover {
    protected PVector pos;
    protected PVector vel;
    protected PVector acc;
    protected float mass;
    private static double G = 6.67e-11;

    protected Mover(PVector pos, PVector vel, float mass) {
        this.pos = pos.copy();// copy() -> cria um objeto idêntico ao que está a ser copiado que não muda mesmo que o objeto inicial mude, pelo q percebi.
        this.vel = vel.copy();
        this.mass = mass;
        acc = new PVector();
    }
    public void applyForce(PVector f){
        acc.add(PVector.div(f,mass));
    }
    public void move(float dt){// tempo
        vel.add(acc.mult(dt));
        pos.add(PVector.mult(vel,dt));
        acc.mult(0);
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    public void setVel(PVector vel) {
        this.vel = vel;
    }

    public PVector getPos() {
        return pos;
    }

    public PVector getVel() {
        return vel;
    }

    public PVector attraction(Mover m) {
        PVector r = PVector.sub(pos, m.pos);        
        float dist = r.mag();
        float strength = (float) (G * mass * m.mass / Math.pow(dist, 2));
        return r.normalize().mult(strength);
    }
}