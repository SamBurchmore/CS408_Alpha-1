package Model.Agents.AgentInterfaces;

public interface Scores {

    void setHunger(int hunger_);
    int getHunger();
    int getAge();
    void setAge(int age_);
    int getHealth();
    void setHealth(int health_);
    int getMAX_HUNGER();
    int getMAX_AGE();
    int getMAX_HEALTH();
    void setMAX_HUNGER(int MAX_HUNGER_);
    void setMAX_AGE(int MAX_AGE_);
    void setMAX_HEALTH(int MAX_HEALTH_);
    int getCreationDelay();
    void setCreationDelay(int creationDelay_);
    int getCreationCounter();
    void setCreationCounter(int creationCounter);

}
