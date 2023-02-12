package Simulation.Agent.AgentBaseComponents;

import Simulation.Agent.AgentInterfaces.Motivation;

import java.io.Serializable;

public abstract class BaseMotivation implements Motivation, Serializable {

    int bias;
    int weight;

    public BaseMotivation(int bias, int weight) {
        this.bias = bias;
        this.weight = weight;
    }

    @Override
    public boolean equals(Motivation motivation) {
        return false;
    }

    @Override
    public int getBias() {
        return bias;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setBias(int bias) {
        this.bias = bias;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
