package physics;

public class PSControl {
    private float averageAngle;
    private float dispersionAngle;
    private float minVelocity;
    private float maxVelocity;
    private float minLifetime;
    private float maxLifetime;
    private float minRadius;
    private float maxRadius;
    private float flow;
    private int color;

    public PSControl(float[] velControl, float[] lifetime, float[] ) {

    }

    public float getRndLifetime() {
        return getRnd(minLifetime, maxLifetime);
    }

    public void setVelParams(float[] velControl) {
        averageAngle = velControl[0];
        dispersionAngle = velControl[1];
    }
}
