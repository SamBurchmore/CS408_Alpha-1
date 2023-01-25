package Controller;

import Model.AgentEditor.AgentSettings;
import Model.ModelController;
import View.MainView;

public class MainController {

    // The Graphical User Interface
    private MainView view;
    // The world model
    private ModelController modelController;

    private int counter = 0;
    private int scale = 0;
    private int size;

    private int editingAgent = 0;

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

    public void clear() {
        this.modelController.clear();
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale), 0);
        this.counter = 0;
    }

    public void setMaxFoodLevel(int newMax) {
        this.modelController.setEnvironmentMaxFoodLevel(newMax);
    }

    public void setMinFoodLevel(int newMin) {
        this.modelController.setEnvironmentMinFoodLevel(newMin);
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

    public void setEditingAgent(int index) {

        // Store the old agent settings
        AgentSettings agentSettings = view.getAgentEditorPanel().getAgentSettings();
        modelController.getAgentEditor().setEditingAgentSettings(agentSettings);
        // Update the active agents panel
        view.getActiveAgentsPanel().setAgentSelector(modelController.getAgentEditor().getEditingAgentIndex(), agentSettings.getColor(), agentSettings.getName());

        // Update the agent editor
        modelController.getAgentEditor().setEditingAgentIndex(index);
        // update the agent editor panel);
        view.getAgentEditorPanel().setAgentSettings(modelController.getAgentEditor().getEditingAgentSettings());

    }

//    public void setEditingButtonColor() {
//        view.getActiveAgentsPanel().setAgentSelector(view.getAgentEditorPanel().getCurrentColour(), editingAgent);
//    }

    public void initController() {
        this.view.getRunStepButton().addActionListener(e -> this.runStep());
        this.view.getPopulateButton().addActionListener(e -> this.populateWorld());
        this.view.getClearButton().addActionListener(e -> this.clear());
        this.view.getRunNStepsButton().addActionListener(e -> this.runNSteps());
        this.view.getRefreshSettingsButton().addActionListener(e -> this.refreshEnvironmentSettings());
        this.view.getAgent0Button().addActionListener(e -> this.setEditingAgent(0));
        this.view.getAgent1Button().addActionListener(e -> this.setEditingAgent(1));
        this.view.getAgent2Button().addActionListener(e -> this.setEditingAgent(2));
        this.view.getAgent3Button().addActionListener(e -> this.setEditingAgent(3));
        this.view.getAgent4Button().addActionListener(e -> this.setEditingAgent(4));
        this.view.getAgent5Button().addActionListener(e -> this.setEditingAgent(5));
        this.view.getAgent6Button().addActionListener(e -> this.setEditingAgent(6));
        this.view.getAgent7Button().addActionListener(e -> this.setEditingAgent(7));
        //this.view.getColourChooserButton().addActionListener(e -> this.setEditingButtonColor());
    }

}
