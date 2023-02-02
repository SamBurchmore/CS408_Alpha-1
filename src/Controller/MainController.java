package Controller;
import Model.AgentEditor.AgentSettings;
import Model.AgentEditor.SavedAgents;
import Model.Environment.EnvironmentSettings;
import Model.ModelController;
import View.MainView;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.*;

public class MainController {

    private MainView view;
    // The world model
    private ModelController modelController;

    private int scale = 0;

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount) throws IOException {
        this.modelController = new ModelController(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.view = new MainView();
        this.initView();
        this.initController();
    }

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount, int scale) throws IOException {
        this.modelController = new ModelController(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.scale = scale;
        this.view = new MainView();
        this.initView();
        this.initController();
    }

    public void updateWorldImage() {
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale));
    }

    public void populateWorld() {
        this.modelController.populate( (double) view.getSimulationControlPanel().getPopulationDensitySpinner().getValue());
        this.updateWorldImage();
        this.updateDiagnostics();
    }

    public void runStep() {
        this.modelController.cycle();
        modelController.getDiagnostics().update();
        this.updateDiagnostics();
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
        this.modelController.setEnvironmentSettings(view.getEnvironmentSettingsPanel().getEnvironmentSettings());
        scale = 600 / (int) view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().getValue();
        updateWorldImage();
        view.getDiagnosticsPanel().addLogMessage(modelController.getEnvironment().toString());
    }

    public void clear() {
        this.modelController.clear();
        this.view.updateWorldPanel(this.modelController.getEnvironmentImage(this.scale));
        modelController.getDiagnostics().clearAgentStats();
        modelController.getDiagnostics().clearSteps();
        view.getDiagnosticsPanel().setAgentStats(modelController.getDiagnostics().getAgentStats());
        view.getDiagnosticsPanel().clearLog();
        view.getDiagnosticsPanel().clearStepLabel();

    }

    public void initView() {
        this.updateWorldImage();
    }

    public void updateDiagnostics() {
        this.view.getDiagnosticsPanel().setAgentStats(modelController.getDiagnostics().getAgentStats());
        this.view.getDiagnosticsPanel().setStepLabel(modelController.getDiagnostics().getStep());
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
        this.view.getSaveAgentsMenuButton().addActionListener(e -> this.saveAgents());
        this.view.getLoadAgentsMenuButton().addActionListener(e -> this.loadAgents());
        this.view.getSaveEnvironmentSettingsMenuButton().addActionListener(e -> this.saveEnvironment());
        this.view.getLoadEnvironmentSettingsMenuButton().addActionListener(e -> this.loadEnvironment());
        initActiveAgentsPanel();
        initAgentEditorPanel();
        initEnvironmentSettingsPanel();
        updateDiagnostics();
    }

    public void updateActiveAgentsPanel() {
        for (int i = 0; i < 8; i++) {
            view.getActiveAgentsPanel().setAgentSelector(i, modelController.getAgentEditor().getAgent(i).getAttributes().getColor(), modelController.getAgentEditor().getAgent(i).getAttributes().getName());
        }
    }

    public void updateAgentEditorPanel() {
        view.getAgentEditorPanel().setAgentSettings(modelController.getAgentEditor().getEditingAgentSettings());
    }

    public void logMsg(String logMsg) {
        view.getDiagnosticsPanel().addLogMessage(logMsg);
    }

    public void initActiveAgentsPanel() {
        updateActiveAgentsPanel();
    }

    public void initAgentEditorPanel() {
        updateAgentEditorPanel();
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

    public void saveAgents() {
        if (view.getFileChooser().showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                    SavedAgents savedAgents = new SavedAgents(modelController.getAgentEditor().getActiveAgentsSettings());

                    objectOutputStream.writeObject(savedAgents);
                    objectOutputStream.close();

                    logMsg("[SYSTEM]: Agents Saved.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }

    public void loadAgents() {
        if (view.getFileChooser().showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                    SavedAgents savedAgents = (SavedAgents) objectInputStream.readObject();

                    modelController.getAgentEditor().setActiveAgentsSettings(savedAgents);
                    updateActiveAgentsPanel();
                    updateAgentEditorPanel();

                    objectInputStream.close();

                    logMsg("[SYSTEM]: Agents Loaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }

    public void saveEnvironment() {
        if (view.getFileChooser().showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                    EnvironmentSettings environmentSettings = modelController.getEnvironmentSettings();

                    objectOutputStream.writeObject(environmentSettings);
                    objectOutputStream.close();

                    logMsg("[SYSTEM]: Environment Saved.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }

    public void loadEnvironment() {
        if (view.getFileChooser().showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                    EnvironmentSettings environmentSettings = (EnvironmentSettings) objectInputStream.readObject();

                    modelController.setEnvironmentSettings(environmentSettings);
                    updateWorldImage();

                    objectInputStream.close();

                    logMsg("[SYSTEM]: Environment Loaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }
}
