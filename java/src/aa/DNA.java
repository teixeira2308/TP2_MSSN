package aa;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;
    public float deltaTPursuit;
    public float radiusArrive;
    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;

    public DNA() {
        maxSpeed = random(3, 5);
        maxForce = random(4, 7);
        visionDistance = random(2,2);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float) Math.PI * 0.8f;
        deltaTPursuit = random(0.5f, 1f);
        radiusArrive = random(3, 5);
        deltaTWander = random(1f, 1f);
        radiusWander = random(3f,3f);
        deltaPhiWander = (float) Math.PI/8;
    }

    public static float random(float min, float max) {
        return (float) (min + (max-min) * Math.random());
    }
}
