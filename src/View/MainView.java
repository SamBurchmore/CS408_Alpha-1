package View;

import Model.Diagnostics;

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

    public MainView() {

        getContentPane().setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        //setSize(900, 650);
        //setPreferredSize(new Dimension(900, 1000));
        setTitle("MainView");

        // The GridBag constraints we'll be using to build the GUI
        GridBagConstraints c = new GridBagConstraints();

        agentEditorPanel = new AgentEditorPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 2;
        this.add(agentEditorPanel, c);

        activeAgentsPanel = new ActiveAgentsPanel();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(activeAgentsPanel, c);

        worldPanel = new WorldPanel();
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridheight = 2;
        this.add(worldPanel, c);

        environmentSettingsPanel = new EnvironmentSettingsPanel();
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 2;
        this.add(environmentSettingsPanel, c);

        simulationControlPanel = new SimulationControlPanel();
        c.gridx = 1;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(simulationControlPanel, c);

        diagnosticsPanel = new DiagnosticsPanel();
        c.gridx = 3;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 2;
        this.add(diagnosticsPanel, c);

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