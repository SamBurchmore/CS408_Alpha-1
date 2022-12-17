package Model;

import java.util.EventListener;

public class Diagnostics {

    int environmentSize;
    int agent0Count;
    int agent1Count;

    int counter;

    public Diagnostics(int size) {
        this.agent0Count = 0;
        this.agent1Count = 0;
        this.environmentSize = size;
        this.counter = 0;
    }

    public int agent0Density() {
        return this.agent0Count;
    }

    public int agent1Density() {
        return this.agent1Count;
    }

    public int getAgent0Count() {
        return this.agent0Count;
    }

    public void setAgent0Count(int agent0Count) {
        this.agent0Count = agent0Count;
    }

    public int getAgent1Count() {
        return this.agent1Count;
    }

    public void setAgent1Count(int agent1Count) {
        this.agent1Count = agent1Count;
    }

    public void iterateCounter() {
        this.counter++;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public int getCounter() {
        return this.counter;
    }
}
