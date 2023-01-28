package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainView extends JFrame {

    private EnvironmentSettingsPanel environmentSettingsPanel;
    private SimulationControlPanel simulationControlPanel;
    private AgentEditorPanel agentEditorPanel;
    private WorldPanel worldPanel;
    private ActiveAgentsPanel activeAgentsPanel;
    private DiagnosticsPanel diagnosticsPanel;

    private JPanel leftPanel;

    private JPanel centerPanel;

    public MainView() {

        getContentPane().setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("MainView");

        // The GridBag constraints we'll be using to build the GUI
        GridBagConstraints c = new GridBagConstraints();

        // First we build the left panel and then add it to the frame
        agentEditorPanel = new AgentEditorPanel();
        environmentSettingsPanel = new EnvironmentSettingsPanel();
        leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setPreferredSize(new Dimension(410, 765));
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
        pack();
    }

    public void updateWorldPanel(BufferedImage worldImage, int i) {
        this.worldPanel.updateWorldImage(worldImage);
        this.repaint();
        this.setTitle("Step: " + ((Integer) i));
    }

    public JSpinner getMaxEnergyLevel() {
        return environmentSettingsPanel.getMaxEnergySpinner();
    }

    public JSpinner getMinEnergyLevel() {
        return environmentSettingsPanel.getMinEnergySpinner();
    }

    public JSpinner getEnergyRegenChance() {
        return environmentSettingsPanel.getEnergyRegenChanceSpinner();
    }

    public JSpinner getEnergyRegenAmount() {
        return environmentSettingsPanel.getEnergyRegenAmountSpinner();
    }

    public JButton getRefreshSettingsButton() {
        return environmentSettingsPanel.getRefreshSettingsButton();
    }

    public JSpinner getEnvironmentSize() {
        return environmentSettingsPanel.getEnvironmentSizeSpinner();
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
}