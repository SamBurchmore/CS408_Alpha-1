package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {

    JFileChooser fileChooser;

    private JMenu saveMenu;
    private JMenu loadMenu;
    private JMenu presetsMenu;
    private JMenuBar menuBar;

    private JButton saveAgentsMenuButton;
    private JButton loadAgentsMenuButton;
    private JButton saveEnvironmentSettingsMenuButton;
    private JButton loadEnvironmentSettingsMenuButton;

    private EnvironmentSettingsPanel environmentSettingsPanel;
    private SimulationControlPanel simulationControlPanel;
    private AgentEditorPanel agentEditorPanel;
    private WorldPanel worldPanel;
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

        // Define the menu bar and its menus
        menuBar = new JMenuBar();
        saveMenu = new JMenu("Save");
        saveMenu.setMnemonic(KeyEvent.VK_S);
        loadMenu = new JMenu("Load");
        loadMenu.setMnemonic(KeyEvent.VK_L);
        presetsMenu = new JMenu("Presets");

        // Build the save menu
        saveAgentsMenuButton = new JButton("Save Agents");
        saveEnvironmentSettingsMenuButton = new JButton("Save Environment");
        saveMenu.add(saveAgentsMenuButton);
        saveMenu.add(saveEnvironmentSettingsMenuButton);

        // Build the load menu
        loadAgentsMenuButton = new JButton("Load Agents");
        loadEnvironmentSettingsMenuButton = new JButton("Load Environment");
        loadMenu.add(loadAgentsMenuButton);
        loadMenu.add(loadEnvironmentSettingsMenuButton);

        menuBar.add(new JLabel("|"));
        menuBar.add(saveMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(loadMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(presetsMenu);
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
        worldPanel = new WorldPanel();
        simulationControlPanel = new SimulationControlPanel();
        centerPanel.add(activeAgentsPanel);
        centerPanel.add(worldPanel);
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
        setVisible(true);
        pack();
    }

    public void updateWorldPanel(BufferedImage worldImage) {
        this.worldPanel.updateWorldImage(worldImage);
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
}