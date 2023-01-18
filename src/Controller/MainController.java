package Controller;

import Model.ModelController;
import View.MainView;

import java.util.concurrent.TimeUnit;

public class MainController {

    // The Graphical User Interface
    private MainView view;
    // The world model
    private ModelController modelController;

    private int counter = 0;
    private int scale = 0;

    private boolean cycleToggle = false;

    public MainController(int size_, int starting_food_level, int minFoodLevel, int maxFoodLevel) {
        this.modelController = new ModelController(size_, starting_food_level, minFoodLevel, maxFoodLevel);
        this.view = new MainView();
        this.initView();
        this.initController();
    }

    public MainController(int size_, int starting_food_level, int minFoodLevel, int maxFoodLevel, int scale) {
        this.modelController = new ModelController(size_, starting_food_level, minFoodLevel, maxFoodLevel);
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

    public void initView() {
        this.updateWorldImage();
    }

    public void initController() {
        this.view.getRunStepButton().addActionListener(e -> this.runStep());
        this.view.getPopulateButton().addActionListener(e -> this.populateWorld());
        this.view.getClearButton().addActionListener(e -> this.clear());
        this.view.getRunNStepsButton().addActionListener(e -> this.runNSteps());
    }

}
