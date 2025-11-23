package physics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;;

public class ParticleSystem extends Body {
    private List<Particle> particles;
    private PSControl psc;

    public ParticleSystem(PVector pos, PVector vel, float mass, float radius, PSControl psc) {
        super(pos, vel, mass, radius, psc.getColor());
        this.psc = psc;
        this.particles = new ArrayList<Particle>();

        System.out.println("ParticleSystem created:");
        System.out.println("  Position: " + pos);
        System.out.println("  Velocity: " + vel);
        System.out.println("  Flow: " + psc.getFlow());
        System.out.println("  Color: " + psc.getColor());
    }

    public PSControl getPSControl() {
        return psc;
    }

    @Override
    public void move(float dt) {
        super.move(dt);
        addParticles(dt);
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.move(dt);
            if (p.isDead()) 
                particles.remove(i);

        }
    }

    private void addParticles(float dt) {
        float particlesPerFrame = psc.getFlow() * dt;
        int n = (int) particlesPerFrame;
        float f = particlesPerFrame - n;
        for (int i = 0; i < n; i++) addOneParticle();
        if (Math.random() < f) addOneParticle();
    }

    public void removeDeadParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            if (p.isDead()) {
                iterator.remove();
            }
        }
    }

    public boolean isEmpty() {
        return particles.isEmpty();
    }

    private void addOneParticle() {
        Particle particle = new Particle(pos, psc.getRndVel(), psc.getRndRadius(), psc.getColor(), psc.getRndLifetime());
        particles.add(particle);
    }

    public int getParticleCount() {
        return particles.size();
    }


    @Override
    public void display(PApplet p, SubPlot plt) {
        for (Particle particle: particles) {
            particle.display(p, plt);
        }
    }
}
