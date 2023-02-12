package Controller;
import Simulation.AgentUtility.AgentSettings;
import Simulation.AgentUtility.SavedAgents;
import Simulation.Environment.EnvironmentSettings;
import Simulation.Simulation;
import View.MainView;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.*;

public class MainController {

    // The class where the GUI and all its elements are stored
    private MainView view;
    // The simulation
    private Simulation simulation;

    private int scale = 0;

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount) throws IOException {
        this.simulation = new Simulation(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.view = new MainView();
        initModel();
        initController();
        initView();
    }

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount, int scale) throws IOException {
        this.simulation = new Simulation(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.scale = scale;
        this.view = new MainView();
        initView();
        initController();
    }

    public void initView() {
        updateWorldImage();
        updateActiveAgentsPanel();
        updateAgentEditorPanel();
        updateEnvironmentSettingsPanel();
        updateDiagnosticsPanel();
    }

    public void initController() {
        view.getSimulationControlPanel().getRunStepButton().addActionListener(e -> runStep());
        view.getSimulationControlPanel().getPopulateButton().addActionListener(e -> populateWorld());
        view.getSimulationControlPanel().getClearButton().addActionListener(e -> clear());
        view.getSimulationControlPanel().getRunNStepsButton().addActionListener(e -> runNSteps());
        view.getEnvironmentSettingsPanel().getRefreshSettingsButton().addActionListener(e -> updateEnvironmentSettings());
        view.getActiveAgentsPanel().getAgent0Button().addActionListener(e -> setEditingAgent(0));
        view.getActiveAgentsPanel().getAgent1Button().addActionListener(e -> setEditingAgent(1));
        view.getActiveAgentsPanel().getAgent2Button().addActionListener(e -> setEditingAgent(2));
        view.getActiveAgentsPanel().getAgent3Button().addActionListener(e -> setEditingAgent(3));
        view.getActiveAgentsPanel().getAgent4Button().addActionListener(e -> setEditingAgent(4));
        view.getActiveAgentsPanel().getAgent5Button().addActionListener(e -> setEditingAgent(5));
        view.getActiveAgentsPanel().getAgent6Button().addActionListener(e -> setEditingAgent(6));
        view.getActiveAgentsPanel().getAgent7Button().addActionListener(e -> setEditingAgent(7));
        view.getAgentEditorPanel().getUpdateSettingsButton().addActionListener(e -> setEditingAgent(simulation.getAgentEditor().getEditingAgentIndex()));
        view.getSimulationControlPanel().getReplenishEnvironmentEnergyButton().addActionListener(e -> replenishEnvironment());
        view.getSaveAgentsMenuButton().addActionListener(e -> saveAgents());
        view.getLoadAgentsMenuButton().addActionListener(e -> loadAgents());
        view.getSaveEnvironmentSettingsMenuButton().addActionListener(e -> saveEnvironment());
        view.getLoadEnvironmentSettingsMenuButton().addActionListener(e -> loadEnvironment());
    }

    final public void initModel() {
        initDiagnostics();
    }

    /*
    * The following methods control the model
    * */
    // Populates the environment with agents
    public void populateWorld() {
        simulation.populate( (double) view.getSimulationControlPanel().getPopulationDensitySpinner().getValue());
        updateWorldImage();
        updateDiagnosticsPanel();
        logMsg("[ENVIRONMENT]: Environment populated at a density of " + view.getSimulationControlPanel().getPopulationDensitySpinner().getValue() + "%.");
    }

    // Runs the simulation for one step and updates the environment image after
    public void runStep() {
        simulation.cycle();
        simulation.getDiagnostics().iterateStep();
        updateDiagnosticsPanel();
        updateWorldImage();
    }

    // Runs the simulation for a set number of steps, value is taken from the simultion control panel
    public void runNSteps() {
        int n = (int) view.getSimulationControlPanel().getRunNStepsSpinner().getValue();
        for (int i = 0; i < n; i++) {
            runStep();
        }
        logMsg("[SIMULATION]: Simulation ran for " + n + " steps.");
    }

    public void setEditingAgent(int index) {
        // Store the old agent settings
        AgentSettings agentSettings = view.getAgentEditorPanel().getAgentSettings();
        simulation.getAgentEditor().setEditingAgentSettings(agentSettings);

        // Update the active agents panel
        view.getActiveAgentsPanel().setAgentSelector(simulation.getAgentEditor().getEditingAgentIndex(), agentSettings.getColor(), agentSettings.getName());

        // Update the agent editor
        simulation.getAgentEditor().setEditingAgentIndex(index);

        // update the agent editor panel
        view.getAgentEditorPanel().setAgentSettings(simulation.getAgentEditor().getEditingAgentSettings());
        simulation.updateAgentNames();
        view.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
    }

    // Restores every tile in the environment to its maximum energy
    public void replenishEnvironment() {
        simulation.replenishEnvironmentEnergy();
        updateWorldImage();
        view.getDiagnosticsPanel().addLogMessage("[ENVIRONMENT]: Energy Replenished.");
        updateDiagnosticsPanel();
    }

    // Updates the environments settings to reflect those in the environment settings panel
    public void updateEnvironmentSettings() {
        simulation.setEnvironmentSettings(view.getEnvironmentSettingsPanel().getEnvironmentSettings());
        scale = 600 / (int) view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().getValue();
        updateWorldImage();
        logMsg("[ENVIRONMENT]: Settings updated to- \n" + simulation.getEnvironment().toString());
        updateDiagnosticsPanel();
        updateWorldImage();
    }

    // Removes all agents from the environment and clears the agents stats from the diagnostics panel
    public void clear() {
        simulation.clearAgents();
        view.updateWorldPanel(simulation.getEnvironmentImage(scale));
        simulation.getDiagnostics().clearAgentStats();
        simulation.getDiagnostics().clearSteps();
        view.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
        view.getDiagnosticsPanel().clearStepLabel();
        logMsg("[ENVIRONMENT]: Agents cleared.");
    }

    // Updates the diagnostics class with the current agent names
    public void initDiagnostics() {
        simulation.getDiagnostics().setAgentNames(simulation.getAgentEditor().getAgentNames());
        simulation.getDiagnostics().setMaxEnvironmentEnergy(simulation.getMaxTileEnergy() * simulation.getEnvironmentSize()* simulation.getEnvironmentSize());
        simulation.getDiagnostics().resetCurrentEnvironmentEnergy();
    }



    /*
    * The following methods update the view
    * */
    // Updates the environment image to reflect the environment in the model
    public void updateWorldImage() {
        view.updateWorldPanel(simulation.getEnvironmentImage(scale));
    }

    // Add a log message to the diagnostics panels text log
    public void logMsg(String logMsg) {
        view.getDiagnosticsPanel().addLogMessage(logMsg);
    }

    // Updates the values in the environment settings panel to match those of the current environment
    public void updateEnvironmentSettingsPanel() {
        view.getEnvironmentSettingsPanel().setColors(simulation.getEnvironmentColors());
        view.getEnvironmentSettingsPanel().getMaxEnergySpinner().setValue(simulation.getMaxTileEnergy());
        view.getEnvironmentSettingsPanel().getMinEnergySpinner().setValue(simulation.getMinTileEnergy());
        view.getEnvironmentSettingsPanel().getEnergyRegenChanceSpinner().setValue(simulation.getEnergyRegenChance());
        view.getEnvironmentSettingsPanel().getEnergyRegenAmountSpinner().setValue(simulation.getEnergyRegenAmount());
        view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(simulation.getEnvironmentSize());
    }

    // Updates the data in the diagnostics panel to match the data in the diagnostics panel
    public void updateDiagnosticsPanel() {
        view.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
        view.getDiagnosticsPanel().setStepLabel(simulation.getDiagnostics().getStep());
        view.getDiagnosticsPanel().setEnvironmentStats(simulation.getDiagnostics().getEnvironmentStats());
    }

    // Updates the values in the agent editor panel to match those of the currently editing agent
    public void updateAgentEditorPanel() {
        view.getAgentEditorPanel().setAgentSettings(simulation.getAgentEditor().getEditingAgentSettings());
    }

    // Updates the data in the active agents panel to match the active agents in the agent editor class
    public void updateActiveAgentsPanel() {
        for (int i = 0; i < 8; i++) {
            view.getActiveAgentsPanel().setAgentSelector(i, simulation.getAgentEditor().getAgent(i).getAttributes().getColor(), simulation.getAgentEditor().getAgent(i).getAttributes().getName());
        }
    }

    public void saveAgents() {
        if (view.getFileChooser().showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                    SavedAgents savedAgents = new SavedAgents(simulation.getAgentEditor().getActiveAgentsSettings());

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
        if (view.getFileChooser().showOpenDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                    SavedAgents savedAgents = (SavedAgents) objectInputStream.readObject();

                    simulation.getAgentEditor().setActiveAgentsSettings(savedAgents);
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

                    EnvironmentSettings environmentSettings = simulation.getEnvironmentSettings();

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
        if (view.getFileChooser().showOpenDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                    EnvironmentSettings environmentSettings = (EnvironmentSettings) objectInputStream.readObject();

                    simulation.setEnvironmentSettings(environmentSettings);
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

    public Simulation getModelController() {
        return simulation;
    }
}
