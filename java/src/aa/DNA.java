package aa;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;

    public DNA() {
        maxSpeed = random(3, 5);
        maxForce = random(4, 7);
        visionDistance = random(2,4);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float) Math.PI;
    }

    public static float random(float min, float max) {
        return (float) (min + (max-min) * Math.random());
    }
}
