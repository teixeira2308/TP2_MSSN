package aa;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.plugins.tiff.FaxTIFFTagSet;
import physics.Body;
import processing.core.PVector;

public class Eye {
    private List<Body> allTrackingBodies;
    private List<Body> farSight;
    private List<Body> nearSight;
    private Boid me;
    protected Body target;

    public Eye(Boid me, List<Body> allTrackingBodies) {
        this.me = me;
        this.allTrackingBodies = allTrackingBodies;
        target = allTrackingBodies.get(0);
    }

    public List<Body> getFarSight() {
        return farSight;
    }

    public List<Body> getNearSight() {
        return nearSight;
    }

    public void look() {
        farSight = new ArrayList<Body>();
        nearSight = new ArrayList<Body>();
        for (Body b : allTrackingBodies) {
            if (farSight(b.getPos())) farSight.add(b);
            if (nearSight(b.getPos())) nearSight.add(b);
        }
    }

    private boolean inSight(PVector t, float maxDistance, float maxAngle) {
        PVector r = PVector.sub(t, me.getPos());
        float d = r.mag();
        float angle = PVector.angleBetween(r, me.getVel());
        return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
    }

    private boolean farSight(PVector t) {
        return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
    }

    private boolean nearSight(PVector t) {
        return inSight(t, me.dna.visionSafeDistance, (float) Math.PI);
    }
}
