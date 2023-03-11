package Controller;
import Simulation.Agent.AgentUtility.AgentSettings;
import Simulation.Agent.AgentUtility.ActiveAgentsSettings;
import Simulation.Environment.EnvironmentSettings;
import Simulation.Simulation;
import Simulation.SimulationUtility.SimulationSettings;
import View.MainView;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

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

    private JDialog loadingDialog;

    private int scale;

    public MainController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double energyRegenChance, int energyRegenAmount) throws IOException {
        this.viewController = new ViewController();
        viewController.showLoadingDialog();
        this.scale = 600/size;
        this.view = new MainView();
        this.simulation = new Simulation(size, starting_food_level, minFoodLevel, maxFoodLevel, energyRegenChance, energyRegenAmount);
        this.simulationController = new SimulationController();
        initController();
        loadOnOpen();
        simulationController.initDiagnostics();
        viewController.initView();
        viewController.deleteLoadingDialog();
        this.cycleFlag = false;
        this.simulationRunning = false;
        this.runningNSteps = false;
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
        ((JButton) view.getActiveAgentsPanel().getAgent0Button()).addActionListener(e -> setEditingAgent(0));
        ((JButton) view.getActiveAgentsPanel().getAgent1Button()).addActionListener(e -> setEditingAgent(1));
        ((JButton) view.getActiveAgentsPanel().getAgent2Button()).addActionListener(e -> setEditingAgent(2));
        ((JButton) view.getActiveAgentsPanel().getAgent3Button()).addActionListener(e -> setEditingAgent(3));
        ((JButton) view.getActiveAgentsPanel().getAgent4Button()).addActionListener(e -> setEditingAgent(4));
        ((JButton) view.getActiveAgentsPanel().getAgent5Button()).addActionListener(e -> setEditingAgent(5));
        ((JButton) view.getActiveAgentsPanel().getAgent6Button()).addActionListener(e -> setEditingAgent(6));
        ((JButton) view.getActiveAgentsPanel().getAgent7Button()).addActionListener(e -> setEditingAgent(7));

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
        view.getClearTerrain().addActionListener(e -> simulationController.clearTerrain());
        view.getFillTerrain().addActionListener(e -> simulationController.fillTerrain());
        view.getClearTerrain().addActionListener(e -> simulationController.clearTerrain());
        view.getGenerateCave().addActionListener(e -> simulationController.standardCave());
        view.getGenerateVariableCave().addActionListener(e -> simulationController.variableCave());
        view.getGenerateGraphCave().addActionListener(e -> simulationController.graphCave());
        view.getPreset1Button().addActionListener(e -> loadPreset(0));
        view.getPreset2Button().addActionListener(e -> loadPreset(1));
        view.getPreset3Button().addActionListener(e -> loadPreset(2));
        view.getPreset4Button().addActionListener(e -> loadPreset(3));
        view.getPreset5Button().addActionListener(e -> loadPreset(4));
        view.getPreset6Button().addActionListener(e -> loadPreset(5));

        // Add window listener to mainView
        view.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveOnClose();
            }
        });

    }

    public void setEditingAgent(int index) {
        // Store the old agent settings
        AgentSettings agentSettings = view.getAgentEditorPanel().getAgentSettings();
        if (agentSettings.getName().isEmpty()) {
            agentSettings.setName(simulation.getAgentEditor().getAgentSettings(index).getName());
        }
        simulation.getAgentEditor().setEditingAgentSettings(agentSettings);

        // Update the agent selector panel
        view.getActiveAgentsPanel().setAgentSelector(simulation.getAgentEditor().getEditingAgentIndex(), agentSettings.getSeedColor(), agentSettings.getName());

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
            if (simulation.getDiagnostics().logMessagesInQueue()) {
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
                viewController.updateSimulationView();
                simulation.getDiagnostics().clearDiagnostics();
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
            EnvironmentSettings environmentSettings = view.getEnvironmentSettingsPanel().getEnvironmentSettings();
            if (environmentSettings.getSize() != simulation.getEnvironment().getSize() && cycleFlag) {
                environmentSettings.setSize(simulation.getEnvironment().getSize());
                viewController.logMsg("[ENVIRONMENT]: Environment size cannot be changed while the simulation is running.");
                view.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(environmentSettings.getSize());
            }
            simulation.setEnvironmentSettings(environmentSettings);
            scale = 600 / environmentSettings.getSize();
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
            scale = 600 / (int) simulationSettings.getEnvironmentSettings().getSize();
            simulation.setSimulationSettings(simulationSettings);
        }

        // Initialises the diagnostics panel with the agent names and environment energy.
        public void initDiagnostics() {
            simulation.getDiagnostics().setAgentNames(simulation.getAgentEditor().getAgentNames());
            //simulation.getDiagnostics().setMaxEnvironmentEnergy(simulation.getEnvironment().getMaxEnergyLevel() * simulation.getEnvironment().getSize()* simulation.getEnvironment().getSize());
            //simulation.getDiagnostics().resetCurrentEnvironmentEnergy();
        }

        public void clearTerrain() {
            simulation.getTerrainGenerator().clearTerrain();
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Terrain cleared.");
        }

        public void fillTerrain() {
            simulation.getTerrainGenerator().fillTerrain();
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Terrain filled.");
        }

        public void variableCave() {
            simulation.getTerrainGenerator().generateVariableCave();
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Variable Cave generated with - \n" + simulation.getTerrainGenerator().getTerrainSettings().toString());
        }

        public void graphCave() {
            simulation.getTerrainGenerator().generateGraphCave();
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Graph Cave generated with - \n" + simulation.getTerrainGenerator().getTerrainSettings().toString());
        }

        public void standardCave() {
            simulation.getTerrainGenerator().generateCave();
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Cave generated with - \n" + simulation.getTerrainGenerator().getTerrainSettings().toString());
        }

    }

    public class ViewController {
        // Updates all views
        public void updateView() {
            updateSimulationView();
            updateActiveAgentsPanel();
            updateEnvironmentSettingsPanel();
            updateDiagnosticsPanel();
            updateAgentEditorPanel();
        }

        // Updates the environment image to reflect the environment in the model
        public void updateSimulationView() {
            view.updateSimulationPanel(simulation.getEnvironment().toBufferedImage(scale));
        }

        // Add a log message to the diagnostics panels text log
        public void logMsg(String logMsg) {
            view.getDiagnosticsPanel().addLogMessage(logMsg);
        }

        // Updates the values in the environment settings panel to match those of the current environment
        public void updateEnvironmentSettingsPanel() {
            view.getEnvironmentSettingsPanel().setEnvironmentSettings(simulation.getEnvironment().getEnvironmentSettings());
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
            simulation.getTerrainGenerator().setTerrainSettings(view.openTerrainSettings(simulation.getTerrainGenerator().getTerrainSettings()));
        }

        private void showLoadingDialog() throws MalformedURLException {
            loadingDialog = new JDialog();
            JLabel loadingIcon = new JLabel();
            loadingIcon.setIcon(new ImageIcon(new File("images\\loading-image.png").toURL()));
            loadingDialog.add(loadingIcon);
            loadingDialog.setUndecorated(true);
            loadingDialog.pack();
            loadingDialog.setLocationRelativeTo(null);
            loadingDialog.setVisible(true);
        }

        private void deleteLoadingDialog() {
            loadingDialog.dispose();
        }
    }

    public void loadOnOpen() {
        try {
            File file = new File("data\\settings.dat");
            loadFile(file);

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
            System.out.println(e);
        }
    }

    public void saveOnClose() {
        try {
            File file = new File("data\\settings.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

            SimulationSettings simulationSettings = simulationController.getSimulationSettings();

            objectOutputStream.writeObject(simulationSettings);
            objectOutputStream.close();

        } catch (IOException e) {
            System.out.println("eh");
            e.printStackTrace();
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
                    viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
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
                    processFile(file);
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
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
                    viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
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
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
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
                    viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
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
                    scale = 600 / environmentSettings.getSize();
                    simulation.setEnvironmentSettings(environmentSettings);
                    viewController.updateSimulationView();
                    viewController.updateEnvironmentSettingsPanel();

                    objectInputStream.close();

                    viewController.logMsg("[SYSTEM]: Environment Loaded.");
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
                }
            }
            else {
                viewController.logMsg("[SYSTEM]: File type must be .dat.");
            }
        }
    }

    public void loadPreset(int index) {
        try {
            File file = new File("data\\presets\\" + index + ".dat");
            loadFile(file);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
            System.out.println(e);
        }
    }

    private void loadFile(File file) throws IOException, ClassNotFoundException {
        if (file.exists()) {
            processFile(file);
        }
        else {
            viewController.logMsg("[SYSTEM]: No settings found.");
        }
    }

    private void processFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

        SimulationSettings simulationSettings = (SimulationSettings) objectInputStream.readObject();
        scale = 600 / simulationSettings.getEnvironmentSettings().getSize();
        simulationController.setSimulationSettings(simulationSettings);
        simulationController.initDiagnostics();
        viewController.updateView();

        objectInputStream.close();

        viewController.logMsg("[SYSTEM]: Settings Loaded.");
    }
}
