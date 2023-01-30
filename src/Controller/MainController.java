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

    private boolean runFlag = false;


    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount) {
        this.modelController = new ModelController(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.view = new MainView();
        this.initView();
        this.initController();
        this.size = size;
    }

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount, int scale) {
        this.modelController = new ModelController(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.scale = scale;
        this.view = new MainView();
        this.initView();
        this.initController();
    }

    public void updateWorldImage() {
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale), this.counter);
    }

    public void populateWorld() {
        this.modelController.populate( (int) view.getSimulationControlPanel().getPopulationDensitySpinner().getValue());
        this.updateWorldImage();
        this.updateAgentStats();
    }

    public void runStep() {
        this.counter += 1;
        this.modelController.cycle();
        this.updateAgentStats();
        this.updateWorldImage();
    }

    public void runNSteps() {
        int n = (int) this.view.getSimulationControlPanel().getRunNStepsSpinner().getValue();
        for (int i = 0; i < n; i++) {
            runStep();
        }
    }

    public void replenishEnvironment() {
        modelController.replenishEnvironmentEnergy();
        updateWorldImage();
    }

    public void refreshEnvironment() {
        if (size != (int) this.view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().getValue()) {
            size = (int) this.view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().getValue();
            this.modelController = new ModelController(size, (int) this.view.getEnvironmentSettingsPanel().getMaxEnergySpinner().getValue(),
                                                             (int) this.view.getEnvironmentSettingsPanel().getMinEnergySpinner().getValue(),
                                                             (int) this.view.getEnvironmentSettingsPanel().getMaxEnergySpinner().getValue(),
                                                             (double) this.view.getEnvironmentSettingsPanel().getEnergyRegenChanceSpinner().getValue(),
                                                             (int) this.view.getEnvironmentSettingsPanel().getEnergyRegenAmountSpinner().getValue());
            modelController.setEnvironmentColors(this.view.getEnvironmentSettingsPanel().getColors());
        }
        else {
            modelController.updateEnvironmentSettings((int) this.view.getEnvironmentSettingsPanel().getMinEnergySpinner().getValue(),
                                                      (int) this.view.getEnvironmentSettingsPanel().getMaxEnergySpinner().getValue(),
                                                      (double) this.view.getEnvironmentSettingsPanel().getEnergyRegenChanceSpinner().getValue(),
                                                      (int) this.view.getEnvironmentSettingsPanel().getEnergyRegenAmountSpinner().getValue());
            modelController.setEnvironmentColors(this.view.getEnvironmentSettingsPanel().getColors());
        }
        scale = (int) Math.ceil(600.0 / size);
        updateWorldImage();
        view.getDiagnosticsPanel().addLogMessage(modelController.getEnvironment().toString());
    }

    public void clear() {
        this.modelController.clear();
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale), 0);
        this.counter = 0;
        view.getDiagnosticsPanel().clearLogTextArea();
    }

    public void initView() {
        this.updateWorldImage();
    }

    public void updateAgentStats() {
        this.view.getDiagnosticsPanel().setAgentStats(modelController.getDiagnostics().getAgentStats());
    }

    public void setEditingAgent(int index) {
        // Store the old agent settings
        AgentSettings agentSettings = view.getAgentEditorPanel().getAgentSettings();
        modelController.getAgentEditor().setEditingAgentSettings(agentSettings);

        // Update the active agents panel
        view.getActiveAgentsPanel().setAgentSelector(modelController.getAgentEditor().getEditingAgentIndex(), agentSettings.getColor(), agentSettings.getName());

        // Update the agent editor
        modelController.getAgentEditor().setEditingAgentIndex(index);

        // update the agent editor panel
        view.getAgentEditorPanel().setAgentSettings(modelController.getAgentEditor().getEditingAgentSettings());
        modelController.updateAgentNames();
        view.getDiagnosticsPanel().setAgentStats(modelController.getDiagnostics().getAgentStats());

    }

    public void initController() {
        this.view.getSimulationControlPanel().getRunStepButton().addActionListener(e -> this.runStep());
        //this.view.getSimulationControlPanel().getStopStartButton().addActionListener();
        this.view.getSimulationControlPanel().getPopulateButton().addActionListener(e -> this.populateWorld());
        this.view.getSimulationControlPanel().getClearButton().addActionListener(e -> this.clear());
        this.view.getSimulationControlPanel().getRunNStepsButton().addActionListener(e -> this.runNSteps());
        this.view.getEnvironmentSettingsPanel().getRefreshSettingsButton().addActionListener(e -> this.refreshEnvironment());
        this.view.getActiveAgentsPanel().getAgent0Button().addActionListener(e -> this.setEditingAgent(0));
        this.view.getActiveAgentsPanel().getAgent1Button().addActionListener(e -> this.setEditingAgent(1));
        this.view.getActiveAgentsPanel().getAgent2Button().addActionListener(e -> this.setEditingAgent(2));
        this.view.getActiveAgentsPanel().getAgent3Button().addActionListener(e -> this.setEditingAgent(3));
        this.view.getActiveAgentsPanel().getAgent4Button().addActionListener(e -> this.setEditingAgent(4));
        this.view.getActiveAgentsPanel().getAgent5Button().addActionListener(e -> this.setEditingAgent(5));
        this.view.getActiveAgentsPanel().getAgent6Button().addActionListener(e -> this.setEditingAgent(6));
        this.view.getActiveAgentsPanel().getAgent7Button().addActionListener(e -> this.setEditingAgent(7));
        this.view.getAgentEditorPanel().getUpdateSettingsButton().addActionListener(e -> this.setEditingAgent(modelController.getAgentEditor().getEditingAgentIndex()));
        this.view.getSimulationControlPanel().getReplenishEnvironmentEnergyButton().addActionListener(e -> this.replenishEnvironment());
        initActiveAgentsPanel();
        initAgentEditorPanel();
        initEnvironmentSettingsPanel();
        updateAgentStats();
    }

    public void initActiveAgentsPanel() {
        for (int i = 0; i < 8; i++) {
            view.getActiveAgentsPanel().setAgentSelector(i, modelController.getAgentEditor().getAgent(i).getAttributes().getColor(), modelController.getAgentEditor().getAgent(i).getAttributes().getName());
        }
    }

    public void initAgentEditorPanel() {
        view.getAgentEditorPanel().setAgentSettings(modelController.getAgentEditor().getEditingAgentSettings());
    }

    public void initEnvironmentSettingsPanel() {
        view.getEnvironmentSettingsPanel().setColors(modelController.getEnvironmentColors());
        view.getEnvironmentSettingsPanel().getMaxEnergySpinner().setValue(modelController.getMaxEnergy());
        view.getEnvironmentSettingsPanel().getMinEnergySpinner().setValue(modelController.getMinEnergy());
        view.getEnvironmentSettingsPanel().getEnergyRegenChanceSpinner().setValue(modelController.getEnergyRegenChance());
        view.getEnvironmentSettingsPanel().getEnergyRegenAmountSpinner().setValue(modelController.getEnergyRegenAmount());
        view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(modelController.getEnvironmentSize());

    }

    public MainView getView() {
        return view;
    }
}
