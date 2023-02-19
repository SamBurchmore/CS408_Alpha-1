package Controller;
import Simulation.Agent.AgentUtility.AgentSettings;
import Simulation.Agent.AgentUtility.ActiveAgentsSettings;
import Simulation.Environment.EnvironmentSettings;
import Simulation.Simulation;
import Simulation.SimulationUtility.SimulationSettings;
import Simulation.SimulationUtility.TerrainSettings;
import View.MainView;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MainController {

    // The class where the GUI and all its elements are stored
    final private MainView view;
    // The simulation
    final private Simulation simulation;

    final private SimulationController simulationController;
    final private ViewController viewController;

    private boolean cycleFlag;
    private boolean simulationRunning;
    private boolean runningNSteps;

    private int scale = 0;

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount) throws IOException, InterruptedException {
        JDialog loadingDialog = new JDialog();
        JLabel loadingIcon = new JLabel();
        loadingIcon.setIcon(new ImageIcon(this.getClass().getResource("../images/loading-image-v2.png")));
        loadingDialog.add(loadingIcon);
        loadingDialog.setUndecorated(true);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(null);
        loadingDialog.setVisible(true);
        this.scale = 600 / size;
        this.view = new MainView();
        this.simulation = new Simulation(view.getWorldPanel(), size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.simulationController = new SimulationController();
        this.viewController = new ViewController();
        simulationController.initDiagnostics();
        initController();
        viewController.initView();
        loadingDialog.dispose();
        this.cycleFlag = false;
        this.simulationRunning = false;
        this.runningNSteps = false;
        //this.scale = 1;
    }

    public void initController() {
        // Add action listeners to Simulation Control Panel
        view.getSimulationControlPanel().getRunStepButton().addActionListener(e -> simulationController.runOneStep());
        view.getSimulationControlPanel().getPopulateButton().addActionListener(e -> simulationController.populateEnvironment());
        view.getSimulationControlPanel().getClearButton().addActionListener(e -> simulationController.clearAgents());
        view.getSimulationControlPanel().getRunNStepsButton().addActionListener(e -> simulationController.runNSteps());
        view.getSimulationControlPanel().getStopStartButton().addActionListener(e -> simulationController.toggleSimulation());
        view.getSimulationControlPanel().getReplenishEnvironmentEnergyButton().addActionListener(e -> simulationController.replenishEnvironment());

        // Add action listeners to Environment Control Panel
        view.getEnvironmentSettingsPanel().getRefreshSettingsButton().addActionListener(e -> simulationController.updateEnvironmentSettings());

        // Add action listeners to Active Agents Panel
        view.getActiveAgentsPanel().getAgent0Button().addActionListener(e -> setEditingAgent(0));
        view.getActiveAgentsPanel().getAgent1Button().addActionListener(e -> setEditingAgent(1));
        view.getActiveAgentsPanel().getAgent2Button().addActionListener(e -> setEditingAgent(2));
        view.getActiveAgentsPanel().getAgent3Button().addActionListener(e -> setEditingAgent(3));
        view.getActiveAgentsPanel().getAgent4Button().addActionListener(e -> setEditingAgent(4));
        view.getActiveAgentsPanel().getAgent5Button().addActionListener(e -> setEditingAgent(5));
        view.getActiveAgentsPanel().getAgent6Button().addActionListener(e -> setEditingAgent(6));
        view.getActiveAgentsPanel().getAgent7Button().addActionListener(e -> setEditingAgent(7));

        // Add action listener to Agent Editor Panel
        view.getAgentEditorPanel().getUpdateSettingsButton().addActionListener(e -> setEditingAgent(simulation.getAgentEditor().getEditingAgentIndex()));

        // Add action listeners to Diagnostics Panel
        view.getDiagnosticsPanel().getClearLogButton().addActionListener(e -> viewController.clearLog());

        // Add action listeners to menu buttons
        view.getSaveAgentsMenuButton().addActionListener(e -> saveAgents());
        view.getLoadAgentsMenuButton().addActionListener(e -> loadAgents());
        view.getSaveEnvironmentSettingsMenuButton().addActionListener(e -> saveEnvironment());
        view.getLoadEnvironmentSettingsMenuButton().addActionListener(e -> loadEnvironment());
        view.getSaveSettingsMenuButton().addActionListener(e -> saveSettings());
        view.getLoadSettingsMenuButton().addActionListener(e -> loadSettings());
        view.getToggleControlsButton().addActionListener(e -> viewController.toggleControls());
        view.getTerrainSettings().addActionListener(e -> viewController.openTerrainSettings());
        view.getClearTerrain().addActionListener(e -> simulationController.clearTerrain());
        view.getGenerateTerrain().addActionListener(e -> simulationController.generateTerrain());

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

    public class SimulationController {
        // Starts the simulation if it's not running, stops it if it is
        public void toggleSimulation() {
            if (runningNSteps) {
                runningNSteps = false;
                cycleFlag = false;
            } else {
                cycleFlag = !cycleFlag;
                if (cycleFlag) {
                    view.getSimulationControlPanel().getStopStartButton().setBackground(new Color(200, 50, 20));
                    view.getSimulationControlPanel().getStopStartButton().setText("Stop");
                    view.getSimulationControlPanel().getClearButton().setEnabled(false);
                    view.getSimulationControlPanel().getRunStepButton().setEnabled(false);
                    view.getSimulationControlPanel().getPopulateButton().setEnabled(false);
                    view.getSimulationControlPanel().getRunNStepsButton().setEnabled(false);

                    runSimulation();
                }
                else {
                    view.getSimulationControlPanel().getStopStartButton().setBackground(new Color(100, 220, 100));
                    view.getSimulationControlPanel().getStopStartButton().setText("Start");
                    view.getSimulationControlPanel().getClearButton().setEnabled(true);
                    view.getSimulationControlPanel().getRunStepButton().setEnabled(true);
                    view.getSimulationControlPanel().getPopulateButton().setEnabled(true);
                    view.getSimulationControlPanel().getRunNStepsButton().setEnabled(true);



                }
            }
        }

        // Runs the simulation until the run flag is set to false
        public void runSimulation() {
            SwingWorker<Void, BufferedImage> swingWorker = new SwingWorker<Void, BufferedImage>() {
                @Override
                protected Void doInBackground() throws Exception {
                    while (cycleFlag) {
                        simulationRunning = true;
                        runStep();
                        simulationRunning = false;
                    }
                    return null;
                }
            };
            swingWorker.execute();
        }

        // Runs the simulation for a set number of steps or until the toggleSimulation() method is called
        public void runSimulation(int stepsToRun) {
            SwingWorker<Void, BufferedImage> swingWorker = new SwingWorker<Void, BufferedImage>() {
                @Override
                protected Void doInBackground() throws Exception {
                    runningNSteps = true;
                    for (int i = 0; i < stepsToRun; i++) {
                        if (!runningNSteps) {
                            viewController.logMsg("[SIMULATION]: Simulation ran for " + i + " steps.");
                            break;
                        }
                        simulationRunning = true;
                        runStep();
                        simulationRunning = false;
                    }
                    runningNSteps = false;
                    return null;
                }
            };
            swingWorker.execute();
        }

        // Runs the simulation for one step and updates the environment image after
        public void runStep() {
            simulation.setDiagnosticsVerbosity(view.getDiagnosticsPanel().getDiagnosticsVerbosity());
            simulation.cycle();
            simulation.getDiagnostics().iterateStep();
            viewController.updateDiagnosticsPanel();
            viewController.updateSimulationView();
            if (simulation.getDiagnostics().logMsgsInQueue()) {
                viewController.logMsg(simulation.getDiagnostics().printLogQueue());
            }
        }

        // Calls the runStep method once if the simulation is not currently running
        public void runOneStep() {
            if (!simulationRunning) {
                runStep();
            } else {
                viewController.logMsg("[SIMULATION]: Simulation is already running.");
            }
        }

        // If the simulation is not currently running, runs the simulation for a set number of steps
        public void runNSteps() {
            if (!simulationRunning) {
                int n = (int) view.getSimulationControlPanel().getRunNStepsSpinner().getValue();
                runSimulation(n);
                viewController.logMsg("[SIMULATION]: Simulation ran for " + n + " steps.");
            } else {
                viewController.logMsg("[SIMULATION]: Simulation is already running.");

            }
        }

        // Populates the environment with agents if the simulation is not currently running
        public void populateEnvironment() {
            if (!simulationRunning) {
                simulation.populate((double) view.getSimulationControlPanel().getPopulationDensitySpinner().getValue());
                viewController.updateSimulationView();
                viewController.updateDiagnosticsPanel();
                viewController.logMsg("[ENVIRONMENT]: Environment populated at a density of " + view.getSimulationControlPanel().getPopulationDensitySpinner().getValue() + "%.");
                simulation.getDiagnostics().setExtinctFlags(0);
            } else {
                viewController.logMsg("[SIMULATION]: Please stop the simulation first.");
            }
        }

        // Removes all agents from the environment and clears the agents stats from the diagnostics panel
        public void clearAgents() {
            if (!simulationRunning) {
                simulation.clearAgents();
                view.updateSimulationPanel(simulation.getSimulationImage(scale));
                simulation.getDiagnostics().clearAgentStats();
                simulation.getDiagnostics().setExtinctFlags(-1);
                simulation.getDiagnostics().clearSteps();
                view.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
                view.getDiagnosticsPanel().clearStepLabel();
                viewController.logMsg("[ENVIRONMENT]: Agents cleared.");
            } else {
                viewController.logMsg("[SIMULATION]: Please stop the simulation first.");
            }
        }

        // Restores every tile in the environment to its maximum energy
        public void replenishEnvironment() {
            simulation.replenishEnvironmentEnergy();
            viewController.updateSimulationView();
            view.getDiagnosticsPanel().addLogMessage("[ENVIRONMENT]: Energy Replenished.");
            viewController.updateDiagnosticsPanel();
        }

        // Updates the environments settings to reflect those in the environment settings panel
        public void updateEnvironmentSettings() {
            simulation.setEnvironmentSettings(view.getEnvironmentSettingsPanel().getEnvironmentSettings());
            scale = 600 / (int) view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().getValue();
            viewController.updateSimulationView();
            viewController.logMsg("[ENVIRONMENT]: Settings updated to- \n" + simulation.getEnvironment().toString());
            viewController.updateDiagnosticsPanel();
            viewController.updateSimulationView();
        }

        // Retrieves a preset of the current simulation settings
        public SimulationSettings getSimulationSettings() {
            return simulation.getSimulationSettings("blank");
        }

        // Sets the simulation settings to the input preset
        public void setSimulationSettings(SimulationSettings simulationSettings) {
            simulation.setSimulationSettings(simulationSettings);
        }

        // Initialises the diagnostics panel with the agent names and environment energy.
        public void initDiagnostics() {
            simulation.getDiagnostics().setAgentNames(simulation.getAgentEditor().getAgentNames());
            simulation.getDiagnostics().setMaxEnvironmentEnergy(simulation.getMaxTileEnergy() * simulation.getEnvironmentSize()* simulation.getEnvironmentSize());
            simulation.getDiagnostics().resetCurrentEnvironmentEnergy();
        }

        public void clearTerrain() {
            simulation.getTerrainGenerator().clearTerrain();
            viewController.updateSimulationView();
        }

        public void generateTerrain() {
            simulation.getTerrainGenerator().generateTerrain();
            viewController.updateSimulationView();
        }

    }

    public class ViewController {
        // Updates the environment image to reflect the environment in the model
        public void updateSimulationView() {
            view.updateSimulationPanel(simulation.getSimulationImage(scale));
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
                view.getActiveAgentsPanel().setAgentSelector(i, simulation.getAgentEditor().getAgent(i).getAttributes().getSeedColor(), simulation.getAgentEditor().getAgent(i).getAttributes().getName());
            }
        }

        // Clear all text from the info log
        public void clearLog() {
            view.getDiagnosticsPanel().clearLog();
        }

        // Toggles the control and settings panels
        public void toggleControls() {
            view.toggleControls();
        }

        // Initialises the view and then sets it to visible
        public void initView() {
            viewController.updateSimulationView();
            viewController.updateActiveAgentsPanel();
            viewController.updateAgentEditorPanel();
            viewController.updateEnvironmentSettingsPanel();
            viewController.updateDiagnosticsPanel();
            view.setVisible(true);
        }

        public void openTerrainSettings() {
            //System.out.println(simulation.getTerrainGenerator().getTerrainSettings().toString());
            simulation.getTerrainGenerator().setTerrainSettings(view.openTerrainSettings(simulation.getTerrainGenerator().getTerrainSettings()));
            //System.out.println(simulation.getTerrainGenerator().getTerrainSettings().toString());

        }
    }

    public void saveSettings() {
        if (view.getFileChooser().showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                    SimulationSettings simulationSettings = simulationController.getSimulationSettings();

                    objectOutputStream.writeObject(simulationSettings);
                    objectOutputStream.close();

                    viewController.logMsg("[SYSTEM]: Settings Saved.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }

    public void loadSettings() {
        if (view.getFileChooser().showOpenDialog(view) == JFileChooser.APPROVE_OPTION) { // User has provided a path
            File file = view.getFileChooser().getSelectedFile();
            if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                    SimulationSettings simulationSettings = (SimulationSettings) objectInputStream.readObject();

                    simulationController.setSimulationSettings(simulationSettings);
                    viewController.updateActiveAgentsPanel();
                    viewController.updateAgentEditorPanel();
                    viewController.updateEnvironmentSettingsPanel();

                    objectInputStream.close();

                    viewController.logMsg("[SYSTEM]: Settings Loaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
            }
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

                    ActiveAgentsSettings activeAgentsSettings = simulation.getAgentEditor().getActiveAgentsSettings();

                    objectOutputStream.writeObject(activeAgentsSettings);
                    objectOutputStream.close();

                    viewController.logMsg("[SYSTEM]: Agents Saved.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
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

                    ActiveAgentsSettings activeAgentsSettings = (ActiveAgentsSettings) objectInputStream.readObject();

                    simulation.getAgentEditor().setActiveAgentsSettings(activeAgentsSettings);
                    viewController.updateActiveAgentsPanel();
                    viewController.updateAgentEditorPanel();

                    objectInputStream.close();

                    viewController.logMsg("[SYSTEM]: Agents Loaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
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

                    EnvironmentSettings environmentSettings = simulation.getEnvironment().getEnvironmentSettings();

                    objectOutputStream.writeObject(environmentSettings);
                    objectOutputStream.close();

                    viewController.logMsg("[SYSTEM]: Environment Saved.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
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
                    viewController.updateSimulationView();
                    viewController.updateEnvironmentSettingsPanel();

                    objectInputStream.close();

                    viewController.logMsg("[SYSTEM]: Environment Loaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }

}
