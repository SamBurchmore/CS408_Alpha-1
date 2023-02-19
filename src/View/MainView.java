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

    public MainView() throws IOException {

        getContentPane().setLayout(new GridBagLayout());
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
        //toggleSimpleModeButton = new JButton("Simple Mode");
       // toolSettingsMenu.add(toggleSimpleModeButton);

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

        // The GridBag constraints we'll be using to build the GUI
        GridBagConstraints c = new GridBagConstraints();

        // First we build the left panel and then add it to the frame
        agentEditorPanel = new AgentEditorPanel();
        environmentSettingsPanel = new EnvironmentSettingsPanel();
        leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setPreferredSize(new Dimension(460, 765));
        leftPanel.add(agentEditorPanel);
        leftPanel.add(environmentSettingsPanel);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 4;

        this.add(leftPanel);

        // Now we build the centre panel and add it to the frame
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        activeAgentsPanel = new ActiveAgentsPanel();
        simulationPanel = new SimulationPanel();
        simulationControlPanel = new SimulationControlPanel();
        centerPanel.add(activeAgentsPanel);
        centerPanel.add(simulationPanel);
        centerPanel.add(simulationControlPanel);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridheight = 4;

        this.add(centerPanel);

        diagnosticsPanel = new DiagnosticsPanel();
        c.gridx = 3;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 4;
        this.add(diagnosticsPanel, c);

        setResizable(false);
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
        diagnosticsPanel.setVisible(!diagnosticsPanel.isVisible());
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