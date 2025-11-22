package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;

import static java.awt.SystemColor.window;


public class Comet {
    PVector position;
    PVector velocity;
    PVector tail;
    float size;
    int lifespan;
    int maxLifespan;

    public Comet(PApplet p, PVector pos, PVector vel, float cometSize) {
        position = pos.copy();
        velocity = vel.copy();
        size = cometSize;
        maxLifespan = (int)(p.random(300, 600));
        lifespan = maxLifespan;
        tail = new PVector();
    }

    public void update(Body sun) {
        PVector toSun = PVector.sub(sun.getPos(), position);
        float distance = toSun.mag();
        if (distance > 0) {
            PVector gravity = toSun.normalize().mult((float)(sun.mass * 0.0001f / (distance * distance)));
            velocity.add(gravity);
        }
        position.add(velocity);
        tail = PVector.mult(velocity, -0.3f);
        lifespan--;
    }

    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float[] headPixel = plt.getPixelCoord(position.x, position.y);
        float[] tailPixel = plt.getPixelCoord(position.x + tail.x, position.y + tail.y);

        p.strokeWeight(3);
        for (int i = 0; i < 5; i++) {
            float alpha = 100 - i * 20;
            p.stroke(200, 230, 255, alpha);
            float tailX = PApplet.lerp(headPixel[0], tailPixel[0], i * 0.2f);
            float tailY = PApplet.lerp(headPixel[1], tailPixel[1], i * 0.2f);
            p.line(headPixel[0], headPixel[1], tailX, tailY);
        }
        p.noStroke();
        p.fill(200, 230, 255);
        p.circle(headPixel[0], headPixel[1], size);

        p.popStyle();
    }

    boolean isDead(double[] window) {
        return lifespan <= 0 ||
                position.x < window[0] || position.x > window[1] ||
                position.y < window[2] || position.y > window[3];
    }
}
