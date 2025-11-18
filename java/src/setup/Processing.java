/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package setup;

import aa.BoidApp;
import apps.TestApp;
import physics.ControlGUIApp;
import physics.FallingBodyApp;
import physics.Particle;
import physics.ParticleSystem;
import physics.SolarSystemApp;
import physics.testParticle;
import processing.core.PApplet;

/**
 *
 * @author tiagosteixeira
 */
public class Processing extends PApplet {

    private static IProcessing app;
    private int lastUpdateTime;

    public static void main(String[] args) {
        app = new BoidApp();
        PApplet.main(Processing.class.getName());
    }

    @Override
    public void settings() {
        size(800,600);
    }

    @Override
    public void setup() {
        app.setup(this);
        lastUpdateTime = millis();

    }
    @Override  
    public void draw() {
        int now = millis();
        float dt = (now - lastUpdateTime) / 1000.0f;
        app.draw(this, dt);
        lastUpdateTime = now;
    }
    @Override
    public void keyPressed() {
        app.keyPressed(this);
    }
    @Override
    public void mousePressed() {
        app.mousePressed(this);
    }
}
