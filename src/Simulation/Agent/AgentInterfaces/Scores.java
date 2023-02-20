package Simulation.Agent.AgentInterfaces;

public interface Scores {

    void setEnergy(int hunger);
    int getEnergy();
    int getAge();
    void setAge(int age);
    int getMaxEnergy();
    int getMaxAge();
    void setMaxEnergy(int maxEnergy);
    void setMaxAge(int maxAge);
    int getCreationCounter();
    void setCreationCounter(int creationCounter);
    Scores copy();

}
