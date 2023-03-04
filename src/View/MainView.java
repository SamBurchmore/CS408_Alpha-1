package View;

import Simulation.SimulationUtility.TerrainSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {

    JFileChooser fileChooser;
    private JMenuBar menuBar;

    private JMenu saveMenu;
    private JMenu loadMenu;
    private JButton saveAgentsMenuButton;
    private JButton loadAgentsMenuButton;
    private JButton saveEnvironmentSettingsMenuButton;
    private JButton loadEnvironmentSettingsMenuButton;
    private JButton saveSettingsMenuButton;
    private JButton loadSettingsMenuButton;


    private JMenu presetsMenu;
    private JButton preset1Button;
    private JButton preset2Button;
    private JButton preset3Button;


    private JMenu toolSettingsMenu;
    private JButton toggleControlsButton;


    private JMenu terrainMenu;
    private JButton clearTerrain;
    private JButton generateTerrain;
    private JButton terrainSettings;

    private EnvironmentSettingsPanel environmentSettingsPanel;
    private SimulationControlPanel simulationControlPanel;
    private AgentEditorPanel agentEditorPanel;
    private SimulationPanel simulationPanel;
    private ActiveAgentsPanel activeAgentsPanel;
    private DiagnosticsPanel diagnosticsPanel;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;

    public MainView() throws IOException {

        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(ImageIO.read(this.getClass().getResource("../images/tool-icon-v1.png")));

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setFileFilter(new FileNameExtensionFilter("DAT file", "dat"));

        // Define the menu bar and its menus
        menuBar = new JMenuBar();
        saveMenu = new JMenu("Save");
        saveMenu.setMnemonic(KeyEvent.VK_S);
        loadMenu = new JMenu("Load");
        loadMenu.setMnemonic(KeyEvent.VK_L);
        presetsMenu = new JMenu("Presets");
        toolSettingsMenu = new JMenu("Settings");
        terrainMenu = new JMenu("Terrain");

        // Build the save menu
        saveSettingsMenuButton = new JButton("Save Settings");
        saveMenu.add(saveSettingsMenuButton);
        saveAgentsMenuButton = new JButton("Save Agents");
        saveEnvironmentSettingsMenuButton = new JButton("Save Environment");
        saveMenu.add(saveAgentsMenuButton);
        saveMenu.add(saveEnvironmentSettingsMenuButton);

        // Build the load menu
        loadSettingsMenuButton = new JButton("Load Settings");
        loadMenu.add(loadSettingsMenuButton);
        loadAgentsMenuButton = new JButton("Load Agents");
        loadEnvironmentSettingsMenuButton = new JButton("Load Environment");
        loadMenu.add(loadAgentsMenuButton);
        loadMenu.add(loadEnvironmentSettingsMenuButton);

        // Build the presets menu
        preset1Button = new JButton("Preset 1");
        preset2Button = new JButton("Preset 3");
        preset3Button = new JButton("Preset 2");
        presetsMenu.add(preset1Button);
        presetsMenu.add(preset2Button);
        presetsMenu.add(preset3Button);

        // Build the tool settings menu
        toggleControlsButton = new JButton("Toggle Controls");
        toolSettingsMenu.add(toggleControlsButton);

        // Build the terrain generator menu
        generateTerrain = new JButton("Generate Terrain");
        terrainMenu.add(generateTerrain);

        clearTerrain = new JButton("Clear Terrain");
        terrainMenu.add(clearTerrain);

        terrainSettings = new JButton("Terrain Settings");
        terrainMenu.add(terrainSettings);

        menuBar.add(new JLabel("|"));
        menuBar.add(saveMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(loadMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(presetsMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(toolSettingsMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(terrainMenu);
        menuBar.add(new JLabel("|"));
        menuBar.setFont(new Font("Dialog", Font.BOLD, 12));
        setJMenuBar(menuBar);

        // First we build the left panel and then add it to the frame
        agentEditorPanel = new AgentEditorPanel();
        environmentSettingsPanel = new EnvironmentSettingsPanel();
        leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setPreferredSize(new Dimension(460, 764));
        leftPanel.setBackground(Color.red);
        leftPanel.add(agentEditorPanel);
        leftPanel.add(environmentSettingsPanel);
        this.add(leftPanel);

        // Now we build the centre panel and add it to the frame
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.setPreferredSize(new Dimension(600, 764));
        activeAgentsPanel = new ActiveAgentsPanel();
        simulationPanel = new SimulationPanel();
        simulationControlPanel = new SimulationControlPanel();
        centerPanel.add(activeAgentsPanel);
        centerPanel.add(simulationPanel);
        centerPanel.add(simulationControlPanel);
        this.add(centerPanel);

        // Now we build the right panel and add it to the frame
        rightPanel = new JPanel(new GridLayout(1, 1));
        rightPanel.setPreferredSize(new Dimension(400, 764));
        diagnosticsPanel = new DiagnosticsPanel();
        rightPanel.add(diagnosticsPanel);
        this.add(rightPanel);

        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void updateSimulationPanel(BufferedImage worldImage) {
        this.simulationPanel.updateWorldImage(worldImage);
        this.repaint();
    }

    public AgentEditorPanel getAgentEditorPanel() {
        return agentEditorPanel;
    }

    public ActiveAgentsPanel getActiveAgentsPanel() {
        return activeAgentsPanel;
    }

    public DiagnosticsPanel getDiagnosticsPanel() {
        return diagnosticsPanel;
    }

    public SimulationControlPanel getSimulationControlPanel() { return simulationControlPanel; }

    public EnvironmentSettingsPanel getEnvironmentSettingsPanel() {
        return environmentSettingsPanel;
    }

    public JButton getSaveAgentsMenuButton() {
        return saveAgentsMenuButton;
    }

    public JButton getLoadAgentsMenuButton() {
        return loadAgentsMenuButton;
    }

    public JButton getSaveEnvironmentSettingsMenuButton() {
        return saveEnvironmentSettingsMenuButton;
    }

    public JButton getLoadEnvironmentSettingsMenuButton() {
        return loadEnvironmentSettingsMenuButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public SimulationPanel getWorldPanel() {
        return simulationPanel;
    }

    public JButton getSimpleModeButton() {
        return toggleControlsButton;
    }

    public JButton getToggleControlsButton() {
        return toggleControlsButton;
    }

    public void toggleControls() {
        if (rightPanel.isVisible()) {
            centerPanel.setPreferredSize(simulationPanel.getPreferredSize());
        }
        else {
            centerPanel.setPreferredSize(new Dimension(600, 764));
        }
        rightPanel.setVisible(!rightPanel.isVisible());
        leftPanel.setVisible(!leftPanel.isVisible());
        activeAgentsPanel.setVisible(!activeAgentsPanel.isVisible());
        simulationControlPanel.setVisible(!simulationControlPanel.isVisible());
        pack();
    }

    public JButton getSaveSettingsMenuButton() {
        return saveSettingsMenuButton;
    }

    public JButton getLoadSettingsMenuButton() {
        return loadSettingsMenuButton;
    }

    public JButton getTerrainSettings() {
        return terrainSettings;
    }

    public TerrainSettings openTerrainSettings(TerrainSettings terrainSettings) {;
        TerrainDialog terrainDialog = new TerrainDialog(this, terrainSettings);
        return terrainDialog.getTerrainSettings();
    }

    public JButton getClearTerrain() {
        return clearTerrain;
    }

    public JButton getGenerateTerrain() {
        return generateTerrain;
    }


}