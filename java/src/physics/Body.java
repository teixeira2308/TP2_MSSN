package physics;

import processing.core.PApplet;
import processing.core.PVector;

public class Body extends Mover{
    public int color;
    private PApplet p;

    public Body(PApplet p, PVector pos, PVector vel, float mass, float radius,int color) {
        super(pos, vel, mass, radius);
        this.color = color;
        this.p = p;
    }
    
    public void display(PApplet parent, SubPlot plt){
        p.pushStyle();
        p.fill(color);
        p.ellipse(pos.x, pos.y, 2*radius, 2*radius);
        p.popStyle();
    }
}