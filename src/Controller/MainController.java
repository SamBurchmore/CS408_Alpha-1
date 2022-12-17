package Controller;

import Model.ModelController;
import View.MainView;

public class MainController {

    // The Graphical User Interface
    private MainView view;
    // The world model
    private ModelController modelController;

    private int counter = 0;

    private boolean cycleToggle = false;

    public MainController(int size_, int starting_food_level, int minFoodLevel, int maxFoodLevel) {
        this.modelController = new ModelController(size_, starting_food_level, minFoodLevel, maxFoodLevel);
        this.view = new MainView(size_);
        this.initView();
        this.initController();
    }

    public void updateWorldImage() {
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(), this.counter);
    }

    public void populateWorld() {
        int[] agentDensityRatio = this.getAgentDensityRatio();
        this.modelController.populate(agentDensityRatio[0], agentDensityRatio[2]);
        this.updateDiagnostics();
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(), this.counter);
    }

    public void runStep() {
        this.counter += 1;
        this.modelController.cycle();
        this.updateDiagnostics();
        this.updateWorldImage();
    }

    public int[] getAgentDensityRatio() {
        int[] agentRatios = new int[3];
        agentRatios[0] = (int) this.view.getAgent0Ratio().getValue();
        agentRatios[1] = (int) this.view.getAgent1Ratio().getValue();
        agentRatios[2] = (int) this.view.getAgentDensity().getValue();
        return agentRatios;
    }

    public void clear() {
        this.modelController.clear();
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(), 0);
    }

    private void updateDiagnostics() {
        this.view.setagent1DensityValue(this.modelController.getDiagnostics().agent1Density());
        this.view.setagent0DensityValue(this.modelController.getDiagnostics().agent0Density());
    }

    public void setMaxFoodLevel(int newMax) {
        this.modelController.setEnvironmentMaxFoodLevel(newMax);
    }

    public void setMinFoodLevel(int newMin) {
        this.modelController.setEnvironmentMaxFoodLevel(newMin);
    }

    public void initView() {
        this.updateWorldImage();
    }

    public void initController() {
        this.view.getRunOneStepButton().addActionListener(e -> this.runStep());
        this.view.getPopulateButton().addActionListener(e -> this.populateWorld());
        this.view.getClearBoardButton().addActionListener(e -> this.clear());
    }
}
