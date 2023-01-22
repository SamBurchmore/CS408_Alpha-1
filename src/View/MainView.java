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

        pack();
    }

    public void updateWorldPanel(BufferedImage worldImage, int i) {
        this.worldPanel.updateWorldImage(worldImage);
        this.repaint();
        this.setTitle("Step: " + ((Integer) i));
    }

    public JButton getRunStepButton() {
        return simulationControlPanel.getRunStepButton();
    }

    public JButton getRunNStepsButton() {
        return simulationControlPanel.getRunNStepsButton();
    }

    public JSpinner getRunNStepsSpinner() {
        return simulationControlPanel.getRunNStepsSpinner();
    }

    public JButton getPopulateButton() {
        return simulationControlPanel.getPopulateButton();
    }

    public JButton getClearButton() {
        return simulationControlPanel.getClearButton();
    }

    public JButton getAgent0Button() {
        return activeAgentsPanel.getAgent0Button();
    }

    public JButton getAgent1Button() {
        return activeAgentsPanel.getAgent1Button();
    }

    public JButton getAgent2Button() {
        return activeAgentsPanel.getAgent2Button();
    }

    public JButton getAgent3Button() {
        return activeAgentsPanel.getAgent3Button();
    }

    public JButton getAgent4Button() {
        return activeAgentsPanel.getAgent4Button();
    }

    public JButton getAgent5Button() {
        return activeAgentsPanel.getAgent5Button();
    }

    public JButton getAgent6Button() {
        return activeAgentsPanel.getAgent6Button();
    }

    public JButton getAgent7Button() {
        return activeAgentsPanel.getAgent7Button();
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

}