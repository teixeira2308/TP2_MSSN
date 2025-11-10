package physics;

import processing.core.PApplet;
import tools.SubPlot;

public class Physics {

    

    public boolean isDead() {
        return timer > lifespan;
    }

    @Override 
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float alpha = PApplet.map(timer, 0, lifespan, 255, 0);
        p.fill(color, alpha)

        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getVectorCoord(radius, radius);

        p.noStroke();
        p.circle(pp[0], pp[1], 2 * r[0]);

        p.popStyle();
    }
}
