package Controller;

import Model.AgentBuilder.ActiveAgents;
import Model.AgentBuilder.AgentBuilder;
import Model.Agents.AgentInterfaces.Agent;
import Model.ModelController;
import View.MainView;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class MainController {

    // The Graphical User Interface
    private MainView view;
    // The world model
    private ModelController modelController;

    private ActiveAgents activeAgents;

    private int counter = 0;
    private int scale = 0;
    private int size;

    private boolean cycleToggle = false;

    public MainController(int size_, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount) {
        this.modelController = new ModelController(size_, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.view = new MainView();
        this.initView();
        this.initController();
        this.size = size_;
    }

    public MainController(int size_, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount, int scale) {
        this.modelController = new ModelController(size_, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.scale = scale;
        this.view = new MainView();
        this.initView();
        this.initController();
    }

    private void cycleController() {
        while (true) {
            if (cycleToggle) {
                runStep();
            }
        }
    }

    public void updateWorldImage() {
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale), this.counter);
    }

    public void populateWorld() {
        this.modelController.populate(0, 1);
        this.updateWorldImage();
    }

    public void runStep() {
        this.counter += 1;
        this.modelController.cycle();
        //this.updateDiagnostics();
        this.updateWorldImage();
    }

    public void runNSteps() {
        int n = (int) this.view.getRunNStepsSpinner().getValue();
        for (int i = 0; i < n; i++) {
            runStep();
        }
    }

    public void refreshEnvironmentSettings() {
        if (size != (int) this.view.getEnvironmentSize().getValue()) {
            size = (int) this.view.getEnvironmentSize().getValue();
            this.modelController = new ModelController(size, (int) this.view.getMaxEnergyLevel().getValue(), (int) this.view.getMinEnergyLevel().getValue(), (int) this.view.getMaxEnergyLevel().getValue(), (double) this.view.getEnergyRegenChance().getValue(), (int) this.view.getEnergyRegenAmount().getValue());
        }
        else {
            setMaxFoodLevel((int) this.view.getMaxEnergyLevel().getValue());
            setMinFoodLevel((int) this.view.getMinEnergyLevel().getValue());
            setFoodRegenAmount((int) this.view.getEnergyRegenAmount().getValue());
            setFoodRegenChance((double) this.view.getEnergyRegenChance().getValue());
        }
        scale = 600 / size;
        updateWorldImage();

    }

//    public int[] getAgentDensityRatio() {
//        int[] agentRatios = new int[3];
//        agentRatios[0] = (int) this.view.getAgent0Ratio().getValue();
//        agentRatios[1] = (int) this.view.getAgent1Ratio().getValue();
//        agentRatios[2] = (int) this.view.getAgentDensity().getValue();
//        return agentRatios;
//    }

    public void clear() {
        this.modelController.clear();
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale), 0);
        this.counter = 0;
    }

//    private void updateDiagnostics() {
//        this.view.setagent1DensityValue(this.modelController.getDiagnostics().agent1Density());
//        this.view.setagent0DensityValue(this.modelController.getDiagnostics().agent0Density());
//    }

    public void setMaxFoodLevel(int newMax) {
        this.modelController.setEnvironmentMaxFoodLevel(newMax);
    }

    public void setMinFoodLevel(int newMin) {
        this.modelController.setEnvironmentMaxFoodLevel(newMin);
    }

    public void setFoodRegenChance(double chance) {
        this.modelController.setFoodRegenChance(chance);
    }

    public void setFoodRegenAmount(int amount) {
        this.modelController.setFoodRegenAmount(amount);
    }

    public void initView() {
        this.updateWorldImage();
    }

    public void setActiveAgents(int index) {
        view.getAgentEditorPanel().setAgent(modelController.getAgentBuilder().getOpenAgentView(index));
        if (index == 0) {
            view.getActiveAgentsPanel().setAgent0ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 1) {
            view.getActiveAgentsPanel().setAgent1ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 2) {
            view.getActiveAgentsPanel().setAgent2ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 3) {
            view.getActiveAgentsPanel().setAgent3ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 4) {
            view.getActiveAgentsPanel().setAgent4ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 5) {
            view.getActiveAgentsPanel().setAgent5ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 6) {
            view.getActiveAgentsPanel().setAgent6ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
        if (index == 7) {
            view.getActiveAgentsPanel().setAgent7ButtonColour(modelController.getAgentBuilder().getOpenAgentView(index).getColor());
        }
    }

    public void initController() {
        this.view.getRunStepButton().addActionListener(e -> this.runStep());
        this.view.getPopulateButton().addActionListener(e -> this.populateWorld());
        this.view.getClearButton().addActionListener(e -> this.clear());
        this.view.getRunNStepsButton().addActionListener(e -> this.runNSteps());
        this.view.getRefreshSettingsButton().addActionListener(e -> this.refreshEnvironmentSettings());
        this.view.getAgent0Button().addActionListener(e -> this.setActiveAgents(0));
        this.view.getAgent1Button().addActionListener(e -> this.setActiveAgents(1));
        this.view.getAgent2Button().addActionListener(e -> this.setActiveAgents(2));
        this.view.getAgent3Button().addActionListener(e -> this.setActiveAgents(3));
        this.view.getAgent4Button().addActionListener(e -> this.setActiveAgents(4));
        this.view.getAgent5Button().addActionListener(e -> this.setActiveAgents(5));
        this.view.getAgent6Button().addActionListener(e -> this.setActiveAgents(6));
        this.view.getAgent7Button().addActionListener(e -> this.setActiveAgents(7));
    }

}
