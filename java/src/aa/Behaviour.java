package aa;

import processing.core.PVector;

public abstract class Behaviour implements IBehaviour {
    private float weight;

    public Behaviour(float weight) {
        this.weight = weight;
    }

    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public float getWeight() {
        return weight;
    }
}
