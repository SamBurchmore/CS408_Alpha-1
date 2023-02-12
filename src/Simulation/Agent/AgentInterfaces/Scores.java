package Simulation.Agent.AgentInterfaces;

public interface Scores {

    void setEnergy(int hunger_);
    int getEnergy();
    int getAge();
    void setAge(int age_);
    int getHealth();
    void setHealth(int health_);
    int getMaxEnergy();
    int getMaxAge();
    int getMaxHealth();
    void setMaxEnergy(int MAX_HUNGER_);
    void setMaxAge(int MAX_AGE_);
    void setMaxHealth(int MAX_HEALTH_);
    int getCreationDelay();
    void setCreationDelay(int creationDelay_);
    int getCreationCounter();
    void setCreationCounter(int creationCounter);

}
